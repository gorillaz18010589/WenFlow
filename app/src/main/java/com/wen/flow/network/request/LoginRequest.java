package com.wen.flow.network.request;

public class LoginRequest extends ApiRequest{
    private String userName;
    private String passWord;
    private int loginType;

    public LoginRequest() {
    }

    public LoginRequest(String userName, String passWord, int loginType) {
        this.userName = userName;
        this.passWord = passWord;
        this.loginType = loginType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }
}
