package com.add.to.cart.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.add.to.cart.R;
import com.add.to.cart.baseadapter.listener.OnRecyclerViewItemChildClick;
import com.add.to.cart.baseadapter.listener.OnRecyclerViewItemClick;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.ui.base.BaseActivity;
import com.add.to.cart.utilities.LayoutManagerUtils;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity {

    private static final String TAG = CartActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private TextView totalHeadingTextView, totalPriceTextView;
    private MaterialButton checkoutMaterialButton;

    private ArrayList<ProductWithCart> arrayList;
    private CartRecyclerViewAdapter adapter;

    private CartViewModel cartViewModel;
    private CartViewModelFactory cartViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initializeView() {
        recyclerView = findViewById(R.id.recyclerView);
        totalHeadingTextView = findViewById(R.id.totalHeadingTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        checkoutMaterialButton = findViewById(R.id.checkoutMaterialButton);
    }

    @Override
    protected void initializeObject() {
        cartViewModelFactory = new CartViewModelFactory(this);
        cartViewModel = new ViewModelProvider(this, cartViewModelFactory).get(CartViewModel.class);

        arrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new CartRecyclerViewAdapter();
        adapter.addArrayList(arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
        final Observer<List<ProductWithCart>> productWithCartObserver = new Observer<List<ProductWithCart>>() {
            @Override
            public void onChanged(List<ProductWithCart> productWithCartList) {
                if (productWithCartList.size() != 0) {
                    arrayList.clear();

                    for (int i=0; i<productWithCartList.size(); i++)
                    {
                        Log.e(TAG, "===== ProductWithCart =====");
                        Log.e(TAG, "Product Name : "+productWithCartList.get(i).product.name);

                        if (productWithCartList.get(i).cart != null)
                        {
                           arrayList.add(productWithCartList.get(i));
                        }
                        else
                        {
                            Log.e(TAG, "===== Product Not Added To Cart =====");
                        }
                    }

                    totalPriceTextView.setText(price(grandTotal(arrayList)));
                    adapter.replaceArrayList(arrayList);
                }
            }
        };
        cartViewModel.getListOfProductWithCart().observe(this/*Activity or Fragment*/, productWithCartObserver);
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnClickListener() {
        checkoutMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deselectAll();
                getSelected();
            }
        });

        adapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick<ProductWithCart>() {
            @Override
            public void OnItemClick(View itemView, ProductWithCart productWithCart, int position) {
                //adapter.checkCheckBox(position, !(adapter.isSelected(position)));
            }
        });

        adapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ProductWithCart>() {
            @Override
            public void OnItemChildClick(View viewChild, ProductWithCart productWithCart, int position) {
                switch (viewChild.getId()) {
                    case R.id.quantityDecrementTextViewButton:

                        if (productWithCart.cart.quantity > 1)
                        {
                            cartViewModel.updateQuantity(
                                    productWithCart.cart.quantity - 1,
                                    calculatePrice(productWithCart.product.price, productWithCart.cart.quantity - 1),
                                    getCurrentDate(),
                                    productWithCart.cart.cartId);
                        }
                        else
                        {
                            cartViewModel.deleteProductFromCart(productWithCart.cart.cartId);
                        }
                        break;
                    case R.id.quantityIncrementTextViewButton:
                        cartViewModel.updateQuantity(
                                productWithCart.cart.quantity + 1,
                                calculatePrice(productWithCart.product.price, productWithCart.cart.quantity + 1),
                                getCurrentDate(),
                                productWithCart.cart.cartId);
                        break;
                    case R.id.checkBox:
                        adapter.checkCheckBox(position, !(adapter.isSelected(position)));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu. it adds items to the action bar if it's present. */
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_select_all:
                selectAll();
                return true;
            case R.id.action_delete_selected:
                deleteSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    public void selectAll() {
        for (int i = 0; i < arrayList.size(); i++)
            adapter.checkCheckBox(i, true);
    }

    public void deselectAll() {
        adapter.deselectAll();
    }

    public void deleteSelected() {
        SparseBooleanArray selectedRows = adapter.getSelectedIds();
        if (selectedRows.size() > 0)
        {
            for (int i = (selectedRows.size() - 1); i >= 0; i--) {
                if (selectedRows.valueAt(i)) {
                    cartViewModel.deleteProductFromCart(arrayList.get(i).cart.cartId);
                    adapter.removeSingleItemUsingPosition(selectedRows.keyAt(i));
                }
            }
            adapter.deselectAll();
        }
    }

    public void getSelected() {
        SparseBooleanArray selectedRows = adapter.getSelectedIds();

        if (selectedRows.size() > 0)
        {
            StringBuilder stringBuilder = new StringBuilder();

            for(int i=0; i<selectedRows.size(); i++)
            {
                int key = selectedRows.keyAt(i);
                String selectedRowLabel ="Selected Position "+key;
                stringBuilder.append(selectedRowLabel + "\n");
                Log.d("Element at "+key, " is "+selectedRows.get(key));
            }
            Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}