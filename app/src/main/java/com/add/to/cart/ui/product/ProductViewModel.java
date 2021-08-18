package com.add.to.cart.ui.product;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.data.repository.LocalRepository;
import java.util.Date;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private LocalRepository repository;
    private LiveData<List<Product>> productList;

    public ProductViewModel(Context context) {
        repository = new LocalRepository(context);
        productList = repository.getListOfProduct();
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }

    public LiveData<List<Product>> getListOfProduct() {
        return productList;
    }

    public LiveData<List<ProductWithCart>> getListOfProductWithCart() {
        return repository.getListOfProductWithCart();
    }

    public void insertCart(Cart cart) {
        repository.insertCart(cart);
    }

    public void updateQuantity(int quantity, int totalPrice, Date updatedAt, int cartId) {
        repository.updateQuantity(quantity, totalPrice, updatedAt, cartId);
    }

    public LiveData<Integer> getNumberOfProductAddedIntoCart() {
        return repository.getNumberOfProductAddedIntoCart();
    }
}
