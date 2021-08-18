package com.add.to.cart.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "cart",
        foreignKeys = @ForeignKey(
                entity = Product.class,
                parentColumns = "product_id",
                childColumns = "product_id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )
)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    @NonNull
    public int cartId;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "total_price")
    public int totalPrice;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "updated_at")
    public Date updatedAt;

    public Cart(int productId, int quantity, int totalPrice, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
