package com.add.to.cart.ui.base;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.add.to.cart.data.local.relations.ProductWithCart;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    /*
     ***********************************************************************************************
     ********************************* BaseActivity abstract methods *******************************
     ***********************************************************************************************
     */
    @LayoutRes
    protected abstract int getLayoutID();
    protected abstract void initializeView();
    protected abstract void initializeObject();
    protected abstract void initializeToolBar();
    protected abstract void initializeCallbackListener();
    protected abstract void addTextChangedListener();
    protected abstract void setOnClickListener();
    /*
     ***********************************************************************************************
     ********************************* Activity lifecycle methods **********************************
     ***********************************************************************************************
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        setContentView(getLayoutID());

        initializeView();
        initializeObject();
        initializeToolBar();
        initializeCallbackListener();
        addTextChangedListener();
        setOnClickListener();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    @CallSuper
    protected void onRestart() { /* Only called after onStop() */
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed()");
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    public String price(int total){
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        String indiaPriceFormat = fmt.format(total); //₹10,000.00
        return indiaPriceFormat;
    }

    public int calculatePrice(int price, int quantity){
        return price*quantity;
    }

    public int grandTotal(List<ProductWithCart> productWithCarts){
        int totalPrice = 0;
        for(int i = 0 ; i < productWithCarts.size(); i++) {
            totalPrice += productWithCarts.get(i).cart.totalPrice;
        }
        return totalPrice;
    }
}
