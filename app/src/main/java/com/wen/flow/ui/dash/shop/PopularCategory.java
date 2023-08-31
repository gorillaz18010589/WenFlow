package com.wen.flow.ui.dash.shop;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularCategory implements Parcelable {
    private int popularCategoryType;
    private String popularCategoryName;
    private int popularCategoryImg;

    public PopularCategory() {
    }

    public PopularCategory(int popularCategoryType, String popularCategoryName, int popularCategoryImg) {
        this.popularCategoryType = popularCategoryType;
        this.popularCategoryName = popularCategoryName;
        this.popularCategoryImg = popularCategoryImg;
    }

    protected PopularCategory(Parcel in) {
        popularCategoryType = in.readInt();
        popularCategoryName = in.readString();
        popularCategoryImg = in.readInt();
    }

    public static final Creator<PopularCategory> CREATOR = new Creator<PopularCategory>() {
        @Override
        public PopularCategory createFromParcel(Parcel in) {
            return new PopularCategory(in);
        }

        @Override
        public PopularCategory[] newArray(int size) {
            return new PopularCategory[size];
        }
    };

    public int getPopularCategoryType() {
        return popularCategoryType;
    }

    public void setPopularCategoryType(int popularCategoryType) {
        this.popularCategoryType = popularCategoryType;
    }

    public String getPopularCategoryName() {
        return popularCategoryName;
    }

    public void setPopularCategoryName(String popularCategoryName) {
        this.popularCategoryName = popularCategoryName;
    }

    public int getPopularCategoryImg() {
        return popularCategoryImg;
    }

    public void setPopularCategoryImg(int popularCategoryImg) {
        this.popularCategoryImg = popularCategoryImg;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(popularCategoryType);
        dest.writeString(popularCategoryName);
        dest.writeInt(popularCategoryImg);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
