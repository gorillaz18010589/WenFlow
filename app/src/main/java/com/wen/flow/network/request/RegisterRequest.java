package com.wen.flow.network.request;

public class RegisterRequest extends ApiRequest{
    private String userName;
    private String passWord;
    private String passWordRepeat;

    public RegisterRequest(String userName, String passWord, String passWordRepeat) {
        this.userName = userName;
        this.passWord = passWord;
        this.passWordRepeat = passWordRepeat;
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

    public String getPassWordRepeat() {
        return passWordRepeat;
    }

    public void setPassWordRepeat(String passWordRepeat) {
        this.passWordRepeat = passWordRepeat;
    }
}
