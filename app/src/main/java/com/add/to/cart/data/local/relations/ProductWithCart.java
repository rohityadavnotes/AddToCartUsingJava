package com.add.to.cart.data.local.relations;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.add.to.cart.data.local.entity.Cart;
import com.add.to.cart.data.local.entity.Product;

public class ProductWithCart {

    @Embedded
    public Product product; /* parent entity */

    @Relation(
            parentColumn = "product_id", /* primary key of parent entity */
            entityColumn = "product_id"  /* foreign key (refers to primary key of parent entity) */
    )
    public Cart cart;  /* child entity */
}