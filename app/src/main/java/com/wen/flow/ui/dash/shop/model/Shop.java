package com.wen.flow.ui.dash.shop.model;

import com.wen.flow.ui.dash.shop.BaseAdapterBean;

import java.util.List;

public class Shop extends BaseAdapterBean {
    private List<Product> productList;
    private List<PopularCategory> popularCategoryList;
    private List<Category> categories;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<PopularCategory> getPopularCategoryList() {
        return popularCategoryList;
    }

    public void setPopularCategoryList(List<PopularCategory> popularCategoryList) {
        this.popularCategoryList = popularCategoryList;
    }
}
