package com.wen.flow.network.response;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class LoginModel extends ApiResult implements Parcelable {

    public LoginModel() {
    }

    /**
     * userID : 1
     * userName : filtoho@gmail.com
     * createTime : 2023-08-24 21:08:33
     * loginType : 1
     * token : MUpVTkdLT09LMjAyMy0wOC0yNCAyMjozNzozMg==
     */



    private int userID;
    private String userName;
    private String createTime;
    private int loginType;
    private String token;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    //順序要一樣
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userID);
        parcel.writeString(userName);
        parcel.writeString(createTime);
        parcel.writeInt(loginType);
        parcel.writeString(token);
    }

    //順序要一樣
    private LoginModel(Parcel in) {
        userID = in.readInt();
        userName = in.readString();
        createTime = in.readString();
        loginType = in.readInt();
        token = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

}
