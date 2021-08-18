package com.add.to.cart.ui.product;

import androidx.annotation.NonNull;
import com.add.to.cart.R;
import com.add.to.cart.baseadapter.adapter.BaseSingleItemAdapter;
import com.add.to.cart.baseadapter.adapter.BaseViewHolder;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.utilities.BitmapManager;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductRecyclerViewAdapter extends BaseSingleItemAdapter<ProductWithCart, BaseViewHolder> {

    public ProductRecyclerViewAdapter() {
        addChildClickViewIds(R.id.addToCartButton);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.product_item_row;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ProductWithCart productWithCart, int position) {
        viewHolder.setImageBitmap(R.id.productCircleImageView, BitmapManager.byteToBitmap(productWithCart.product.productImage));
        viewHolder.setText(R.id.productNameTextView, "Name : "+productWithCart.product.name);
        viewHolder.setText(R.id.productPriceTextView, "Price : "+price(productWithCart.product.price));
    }

    public String price(int total){
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        String indiaPriceFormat = fmt.format(total); //₹10,000.00
        return indiaPriceFormat;
    }
}
