package com.add.to.cart.ui.addproduct;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddProductViewModelFactory implements ViewModelProvider.Factory{

    private Context context;

    public AddProductViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddProductViewModel(context);
    }
}