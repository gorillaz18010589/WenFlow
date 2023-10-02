package com.wen.flow.ui.dash.shop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wen.flow.network.response.LoginModel;
import com.wen.flow.ui.dash.shop.BaseAdapterBean;

public class Product extends BaseAdapterBean implements Parcelable {
    private int id;
    private String name;
    private int price;
    private int photo;

    public Product(int id, String name, int price, int photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(photo);
    }

    private Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        photo = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }
}
