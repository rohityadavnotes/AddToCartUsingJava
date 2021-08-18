package com.add.to.cart.ui.addproduct;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.data.repository.LocalRepository;

public class AddProductViewModel extends ViewModel {

    private LocalRepository repository;

    public AddProductViewModel(Context context) {
        repository = new LocalRepository(context);
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }
}
