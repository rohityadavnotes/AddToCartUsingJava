package com.add.to.cart.data.repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.add.to.cart.data.local.dao.CartDao;
import com.add.to.cart.data.local.dao.ProductDao;
import com.add.to.cart.data.local.database.MyRoomDatabase;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.data.local.relations.ProductWithCart;
import java.util.Date;
import java.util.List;

public class LocalRepository {

    private ProductDao productDao;
    private CartDao cartDao;

    public LocalRepository(Context context) {
        MyRoomDatabase myRoomDatabase = MyRoomDatabase.getInstance(context);
        productDao = myRoomDatabase.getProductDao();
        cartDao = myRoomDatabase.getCartDao();
    }

    public void insertProduct(Product product) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long insert = productDao.insertProduct(product);
                Log.e("INSERT", "Insert Success : " + insert);
            }
        }).start();
    }

    public LiveData<List<Product>> getListOfProduct() {
        return productDao.getListOfProduct();
    }

    public LiveData<List<ProductWithCart>> getListOfProductWithCart() {
        return productDao.getListOfProductWithCart();
    }

    public void insertCart(Cart cart) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long insert = cartDao.insertCart(cart);
                Log.e("CART", "Insert Success : " + insert);
            }
        }).start();
    }

    public void updateQuantity(int quantity, int totalPrice, Date updatedAt, int cartId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long insert = cartDao.updateQuantity(quantity, totalPrice, updatedAt, cartId);
                Log.e("CART", "Update Quantity Success : " + insert);
            }
        }).start();
    }

    public LiveData<List<Cart>> getListOfCartProduct() {
        return cartDao.getListOfCartProduct();
    }

    public LiveData<Integer> getNumberOfProductAddedIntoCart() {
        return cartDao.getNumberOfProductAddedIntoCart();
    }

    public void deleteProductFromCart(int cartId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long insert = cartDao.deleteProductFromCart(cartId);
                Log.e("CART", "Delete Success : " + insert);
            }
        }).start();
    }
}
