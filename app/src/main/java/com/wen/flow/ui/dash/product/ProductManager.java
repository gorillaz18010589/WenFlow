package com.wen.flow.ui.dash.product;

import com.wen.flow.R;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    public static ArrayList<Object> getProductRecommended() {
        ArrayList<Object> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new ProductRecommended(i, "Nike Air Force", 3000, R.drawable.photp_nike_low));
        }
        return data;
    }
}
