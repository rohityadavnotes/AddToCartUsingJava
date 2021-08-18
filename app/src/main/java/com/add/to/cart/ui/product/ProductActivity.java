package com.add.to.cart.ui.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.add.to.cart.R;
import com.add.to.cart.baseadapter.listener.OnRecyclerViewItemChildClick;
import com.add.to.cart.baseadapter.listener.OnRecyclerViewItemClick;
import com.add.to.cart.customview.MyBadgeDrawable;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.ui.ProductDetailActivity;
import com.add.to.cart.ui.addproduct.AddProductActivity;
import com.add.to.cart.ui.base.BaseActivity;
import com.add.to.cart.ui.cart.CartActivity;
import com.add.to.cart.utilities.LayoutManagerUtils;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private Menu menu;
    private int badgeCount = 0;

    private ProductViewModel productViewModel;
    private ProductViewModelFactory productViewModelFactory;

    private RecyclerView recyclerView;
    private ArrayList<ProductWithCart> arrayList;
    private ProductRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_product;
    }

    @Override
    protected void initializeView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initializeObject() {
        productViewModelFactory = new ProductViewModelFactory(this);
        productViewModel = new ViewModelProvider(this, productViewModelFactory).get(ProductViewModel.class);

        arrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new ProductRecyclerViewAdapter();
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
                    arrayList.addAll(productWithCartList);
                    adapter.replaceArrayList(arrayList);
                }
            }
        };
        productViewModel.getListOfProductWithCart().observe(this/*Activity or Fragment*/, productWithCartObserver);

        final Observer<Integer> integerObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    badgeCount = integer;
                    updateProductBadge(badgeCount);
                }
            }
        };
        productViewModel.getNumberOfProductAddedIntoCart().observe(this/*Activity or Fragment*/, integerObserver);
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnClickListener() {
        adapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick<ProductWithCart>() {
            @Override
            public void OnItemClick(View itemView, ProductWithCart productWithCart, int position) {
                openActivityWithParcelable(productWithCart.product);
            }
        });

        adapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ProductWithCart>() {
            @Override
            public void OnItemChildClick(View viewChild, ProductWithCart productWithCart, int position) {
                switch (viewChild.getId()) {
                    case R.id.addToCartButton:
                        if (productWithCart != null)
                        {
                            Log.e(TAG, "===== ProductWithCart =====");
                            Log.e(TAG, "Product Name : "+productWithCart.product.name);

                            if (productWithCart.cart != null)
                            {
                                Log.e(TAG, "===== Already Added To Cart, Now Increase Quantity =====");
                                productViewModel.updateQuantity(
                                        productWithCart.cart.quantity + 1,
                                        calculatePrice(productWithCart.product.price, productWithCart.cart.quantity + 1),
                                        getCurrentDate(),
                                        productWithCart.cart.cartId);
                            }
                            else
                            {
                                Log.e(TAG, "===== Product Not Added To Cart, Now Add =====");
                                Cart cart = new Cart(productWithCart.product.productId,
                                        1,
                                        calculatePrice(productWithCart.product.price, 1),
                                        getCurrentDate(),
                                        getCurrentDate());
                                productViewModel.insertCart(cart);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setBadgeCount(Context context, LayerDrawable icon, int count) {
        MyBadgeDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);

        if (reuse instanceof MyBadgeDrawable) {
            badge = (MyBadgeDrawable) reuse;
        } else {
            badge = new MyBadgeDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    private void updateProductBadge(int count) {
        badgeCount = count;
        invalidateOptionsMenu();
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
        menuInflater.inflate(R.menu.menu_main, menu);

        /* Add to Cart Count */
        MenuItem menuItem = menu.findItem(R.id.action_view_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        setBadgeCount(this, icon, badgeCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_product:
                Intent addProductActivity = new Intent(ProductActivity.this, AddProductActivity.class);
                startActivity(addProductActivity);
                return true;
            case R.id.action_view_cart:
                Intent cartActivity = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(cartActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    private void openActivityWithParcelable(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("parcelable_product_key", product);
        startActivity(intent);
    }
}