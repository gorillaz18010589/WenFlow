package com.wen.flow.ui.dash.product.viewholder;

import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.R;
import com.wen.flow.ui.dash.shop.model.Product;

import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

public class ProductDetailViewHolder extends BaseViewHolder<Product> {


    public ProductDetailViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_detail);
    }

    @Override
    public void setData(Product data) {
        super.setData(data);
    }
}
