package com.wen.flow.network.request;

public class RegisterRequest extends ApiRequest{
    private String userName;
    private String passWord;
    private int emailCode;

    public RegisterRequest(String userName, String passWord, int emailCode) {
        this.userName = userName;
        this.passWord = passWord;
        this.emailCode = emailCode;
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

    public int getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(int emailCode) {
        this.emailCode = emailCode;
    }
}
