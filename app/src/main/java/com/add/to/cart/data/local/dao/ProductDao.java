package com.add.to.cart.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import com.add.to.cart.data.local.entity.Product;
import com.add.to.cart.data.local.relations.ProductWithCart;
import java.util.List;

@Dao
public interface ProductDao {

    /*============================================ Insert ========================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(Product product);

    /*============================================= Read =========================================*/
    @Query("SELECT * FROM product")
    public LiveData<List<Product>> getListOfProduct();

    /*==================================== One to One Relationship ===============================*/
    @Transaction
    @Query("SELECT * FROM product")
    public LiveData<List<ProductWithCart>> getListOfProductWithCart();
}