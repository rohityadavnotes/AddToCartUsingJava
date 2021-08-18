package com.add.to.cart.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.add.to.cart.data.local.entity.Cart;
import java.util.Date;
import java.util.List;

@Dao
public interface CartDao {

    /*============================================ Insert ========================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCart(Cart cart);

    /*============================================ Update ========================================*/
    @Query("UPDATE cart SET quantity=:quantity, total_price= :totalPrice, updated_at= :updatedAt WHERE cart_id = :cartId")
    public int updateQuantity(int quantity, int totalPrice, Date updatedAt, int cartId);

    /*============================================= Read =========================================*/
    @Query("SELECT * FROM cart")
    public LiveData<List<Cart>> getListOfCartProduct();

    @Query("SELECT COUNT(cart_id) FROM cart")
    public LiveData<Integer> getNumberOfProductAddedIntoCart();

    /*============================================ Delete ========================================*/
    @Query("DELETE FROM cart WHERE cart_id = :cartId")
    public int deleteProductFromCart(int cartId);
}