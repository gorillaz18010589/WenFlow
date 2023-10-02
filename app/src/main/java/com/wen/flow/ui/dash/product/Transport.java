package com.wen.flow.ui.dash.product;

public class Transport {
    private String title;
    private int transportPrice;

    public Transport(String title, int transportPrice) {
        this.title = title;
        this.transportPrice = transportPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(int transportPrice) {
        this.transportPrice = transportPrice;
    }
}
