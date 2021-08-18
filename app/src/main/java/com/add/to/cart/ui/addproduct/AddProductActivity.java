package com.add.to.cart.ui.addproduct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.add.to.cart.R;
import com.add.to.cart.customview.CircleImageView;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.ui.base.BaseActivity;
import com.add.to.cart.utilities.BitmapManager;
import com.add.to.cart.utilities.StringUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddProductActivity extends BaseActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();

    private int PICK_IMAGE_REQUEST_CODE_ADD = 100;

    private TextView addProductHeadingTextView;

    private CircleImageView productCircleImageView;
    private FloatingActionButton selectProductImageFloatingActionButton;

    private TextInputLayout productNameTextInputLayout, productPriceTextInputLayout;
    private TextInputEditText productNameTextInputEditText, productPriceTextInputEditText;

    private MaterialButton addMaterialButton;

    private byte[] productImageByteArray;
    private String productNameString;
    private String productPriceString;

    private AddProductViewModel addProductViewModel;
    private AddProductViewModelFactory addProductViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void initializeView() {
        addProductHeadingTextView = findViewById(R.id.addProductHeadingTextView);
        addProductHeadingTextView.setText("Add Product");

        productCircleImageView = findViewById(R.id.productCircleImageView);
        selectProductImageFloatingActionButton = findViewById(R.id.selectProductImageFloatingActionButton);

        productNameTextInputLayout = findViewById(R.id.productNameTextInputLayout);
        productNameTextInputEditText = findViewById(R.id.productNameTextInputEditText);
        productPriceTextInputLayout = findViewById(R.id.productPriceTextInputLayout);
        productPriceTextInputEditText = findViewById(R.id.productPriceTextInputEditText);

        addMaterialButton = findViewById(R.id.addMaterialButton);
    }

    @Override
    protected void initializeObject() {
        addProductViewModelFactory = new AddProductViewModelFactory(this);
        addProductViewModel = new ViewModelProvider(this, addProductViewModelFactory).get(AddProductViewModel.class);
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
    }

    @Override
    protected void addTextChangedListener() {
        productNameTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if(text.length() < 1)
                {
                    productNameTextInputLayout.setErrorEnabled(true);
                    productNameTextInputLayout.setError("Please enter product name !");
                }
                else if(text.length() > 0)
                {
                    productNameTextInputLayout.setError(null);
                    productNameTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        productPriceTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if(text.length() < 1)
                {
                    productPriceTextInputLayout.setErrorEnabled(true);
                    productPriceTextInputLayout.setError("Please enter product price !");
                }
                else if(text.length() > 0)
                {
                    productPriceTextInputLayout.setError(null);
                    productPriceTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void setOnClickListener() {

        selectProductImageFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(PICK_IMAGE_REQUEST_CODE_ADD);
            }
        });

        addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productNameString         = productNameTextInputEditText.getText().toString();
                productPriceString        = productPriceTextInputEditText.getText().toString();

                if (validation(productNameString, productPriceString) == null)
                {
                    Product product = new Product(productImageByteArray, productNameString, Integer.parseInt(productPriceString));
                    addProductViewModel.insertProduct(product);

                    Toast.makeText(getApplicationContext(), "Product Add Success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), validation(productNameString, productPriceString), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == PICK_IMAGE_REQUEST_CODE_ADD)
            {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    Bitmap studentImageBitmap= BitmapManager.decodeUri(AddProductActivity.this,selectedImageUri, 400);
                    productImageByteArray = BitmapManager.bitmapToByte(studentImageBitmap);
                    productCircleImageView.setImageBitmap(BitmapManager.byteToBitmap(productImageByteArray));
                }
            }
        }
    }

    private void showFileChooser(int SELECT_REQUEST_CODE) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_REQUEST_CODE);
    }

    private String validation(String firstName, String lastName) {
        if(productImageByteArray == null)
        {
            return "Please select image !";
        }
        else if(StringUtils.isBlank(firstName))
        {
            return "Please enter product name !";
        }
        else if(StringUtils.isBlank(lastName))
        {
            return "Please enter product price !";
        }
        return null;
    }
}