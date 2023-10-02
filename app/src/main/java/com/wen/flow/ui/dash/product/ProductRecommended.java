package com.wen.flow.ui.dash.product;

public class ProductRecommended {
    private int id;
    private String productName;
    private int productImg;
    private int productPrice;

    public ProductRecommended(int id, String productName, int productImg, int productPrice) {
        this.id = id;
        this.productName = productName;
        this.productImg = productImg;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
