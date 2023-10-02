package com.wen.flow.ui.dash.product.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wen.flow.R;
import com.wen.flow.ui.dash.product.ProductInfo;

import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

public class ProductInfoViewHolder extends BaseViewHolder<ProductInfo> {
    private TextView tvProductName;
    private TextView tvProductPriceRange;

    public ProductInfoViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_information);
        tvProductName = getView(R.id.tvProductName);
        tvProductPriceRange = getView(R.id.tvProductPriceRange);
    }


    @Override
    public void setData(ProductInfo data) {
        super.setData(data);
        tvProductName.setText(data.getProductName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NT$").append(data.getProductPrice());
        tvProductPriceRange.setText(stringBuilder.toString());
    }
}
