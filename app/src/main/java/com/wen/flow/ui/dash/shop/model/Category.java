package com.wen.flow.ui.dash.shop.model;

import com.wen.flow.ui.dash.shop.BaseAdapterBean;

public class Category extends BaseAdapterBean {
    private int id;
    private String name;

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
}
