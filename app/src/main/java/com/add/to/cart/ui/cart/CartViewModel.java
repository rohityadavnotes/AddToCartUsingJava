package com.add.to.cart.ui.cart;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.relations.ProductWithCart;
import com.add.to.cart.data.repository.LocalRepository;
import java.util.Date;
import java.util.List;

public class CartViewModel extends ViewModel {

    private LocalRepository repository;
    private LiveData<List<Cart>> cartList;

    public CartViewModel(Context context) {
        repository = new LocalRepository(context);
        cartList = repository.getListOfCartProduct();
    }

    public void insertCart(Cart cart) {
        repository.insertCart(cart);
    }

    public void updateQuantity(int quantity, int totalPrice, Date updatedAt, int cartId) {
        repository.updateQuantity(quantity, totalPrice, updatedAt, cartId);
    }

    public LiveData<List<Cart>> getListOfCartProduct() {
        return cartList;
    }

    public LiveData<List<ProductWithCart>> getListOfProductWithCart() {
        return repository.getListOfProductWithCart();
    }

    public void deleteProductFromCart(int cartId) {
        repository.deleteProductFromCart(cartId);
    }
}
