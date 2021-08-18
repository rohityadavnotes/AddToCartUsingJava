package com.add.to.cart.data.local.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.add.to.cart.data.local.converter.DateConverter;
import com.add.to.cart.data.local.dao.CartDao;
import com.add.to.cart.data.local.dao.ProductDao;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.entity.Product;

@Database(entities = {Product.class, Cart.class}, version = 1, exportSchema = true)
@TypeConverters({DateConverter.class})
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();

    private static volatile MyRoomDatabase INSTANCE;

    public static MyRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null)
        {
            synchronized (LOCK)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), MyRoomDatabase.class, "Product.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ProductDao getProductDao();
    public abstract CartDao getCartDao();
}
