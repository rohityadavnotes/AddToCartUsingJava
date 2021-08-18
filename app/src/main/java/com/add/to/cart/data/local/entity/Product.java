package com.add.to.cart.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    @NonNull
    public int productId;

    @ColumnInfo(name = "product_image", typeAffinity = ColumnInfo.BLOB)
    public byte[] productImage;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public int price;

    public Product(byte[] productImage, String name, int price) {
        this.productImage = productImage;
        this.name = name;
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productId);
        dest.writeByteArray(this.productImage);
        dest.writeString(this.name);
        dest.writeInt(this.price);
    }

    protected Product(Parcel in) {
        this.productId = in.readInt();
        this.productImage = in.createByteArray();
        this.name = in.readString();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
