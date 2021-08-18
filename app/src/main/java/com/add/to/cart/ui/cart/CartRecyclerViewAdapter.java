package com.add.to.cart.ui.cart;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import com.add.to.cart.R;
import com.add.to.cart.baseadapter.adapter.BaseSingleItemAdapter;
import com.add.to.cart.baseadapter.adapter.BaseViewHolder;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.utilities.BitmapManager;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartRecyclerViewAdapter extends BaseSingleItemAdapter<ProductWithCart, BaseViewHolder> {

    private static final String TAG = CartRecyclerViewAdapter.class.getSimpleName();
    private SparseBooleanArray selectedItemPosition;

    public CartRecyclerViewAdapter() {
        addChildClickViewIds(R.id.quantityDecrementTextViewButton);
        addChildClickViewIds(R.id.quantityIncrementTextViewButton);
        addChildClickViewIds(R.id.checkBox);
        selectedItemPosition = new SparseBooleanArray();
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.cart_product_item_row;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ProductWithCart productWithCart, int position) {
        viewHolder.setImageBitmap(R.id.productCircleImageView, BitmapManager.byteToBitmap(productWithCart.product.productImage));
        viewHolder.setText(R.id.productNameTextView, productWithCart.product.name);

        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        String indiaPriceFormat = fmt.format(productWithCart.product.price); //₹10,000.00
        viewHolder.setText(R.id.productPriceTextView, indiaPriceFormat);

        viewHolder.setText(R.id.quantityTextView, String.valueOf(productWithCart.cart.quantity));

        if(isSelected(position))
        {
            CheckBox checkBox = viewHolder.findView(R.id.checkBox);
            checkBox.setChecked(true);
        }
        else
        {
            CheckBox checkBox = viewHolder.findView(R.id.checkBox);
            checkBox.setChecked(false);
        }
    }

    /**
     * Select all checkbox
     **/
    public void selectAll() {
        Log.d(TAG, "selectAll() : " + "arrayList");
    }

    /**
     * Remove all checkbox
     **/
    public void deselectAll()
    {
        Log.d(TAG, "deselectAll() : " + "arrayList");

        List<Integer> selection = getSelectedItems();
        selectedItemPosition.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Check the Checkbox if not checked, if already check then unchecked
     **/
    public void checkCheckBox(int position, boolean value)
    {
        if (value)
        {
            selectedItemPosition.put(position, true);
        }
        else
        {
            selectedItemPosition.delete(position);
        }
        Log.e(TAG, "selectAll() : " +selectedItemPosition);
        //notifyDataSetChanged();
        notifyItemChanged(position);
    }

    /**
     * Return the selected Checkbox position
     **/
    public SparseBooleanArray getSelectedIds() {
        return selectedItemPosition;
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItemPosition.size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems()
    {
        System.out.println("============="+selectedItemPosition.size());
        List<Integer> items = new ArrayList<>(selectedItemPosition.size());

        for (int i = 0; i < selectedItemPosition.size(); ++i)
        {
            items.add(selectedItemPosition.keyAt(i));
        }
        return items;
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }
}
