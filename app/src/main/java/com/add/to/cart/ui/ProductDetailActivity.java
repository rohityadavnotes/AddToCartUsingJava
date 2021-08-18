package com.add.to.cart.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.add.to.cart.R;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.ui.base.BaseActivity;
import com.add.to.cart.utilities.BitmapManager;

public class ProductDetailActivity extends BaseActivity {

    private static final String TAG = ProductDetailActivity.class.getSimpleName();

    private ImageView productImageView;
    private TextView nameTextView;
    private TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initializeView() {
        productImageView        = findViewById(R.id.imageView);
        nameTextView            = findViewById(R.id.nameTextView);
        priceTextView           = findViewById(R.id.priceTextView);
    }

    @Override
    protected void initializeObject() {
        displayParcelable();
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnClickListener() {
    }

    private void displayParcelable() {
        if (getIntent() != null && getIntent().hasExtra("parcelable_product_key")) {
            Product productParcelable = (Product) getIntent().getParcelableExtra("parcelable_product_key");
            if (productParcelable != null)
            {
                productImageView.setImageBitmap(BitmapManager.byteToBitmap(productParcelable.productImage));
                nameTextView.setText(productParcelable.name);
                priceTextView.setText(String.valueOf(productParcelable.price));
            }
        }
    }
}