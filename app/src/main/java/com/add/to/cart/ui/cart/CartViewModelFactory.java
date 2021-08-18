package com.add.to.cart.ui.cart;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CartViewModelFactory implements ViewModelProvider.Factory{

    private Context context;

    public CartViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CartViewModel(context);
    }
}