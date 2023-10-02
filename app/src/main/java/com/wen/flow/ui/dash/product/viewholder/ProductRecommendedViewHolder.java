package com.wen.flow.ui.dash.product.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wen.flow.R;
import com.wen.flow.ui.dash.product.ProductRecommended;

import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

public class ProductRecommendedViewHolder extends BaseViewHolder<ProductRecommended> {
    private ImageView img;
    private TextView tvName;
    private TextView tvPrice;
    public ProductRecommendedViewHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.item_product_recommende);
        img = getView(R.id.img);
        tvName = getView(R.id.tvName);
        tvPrice = getView(R.id.tvPrice);
    }

    @Override
    public void setData(ProductRecommended data) {
        super.setData(data);
        img.setImageResource(data.getProductImg());
        tvName.setText(data.getProductName());
        tvPrice.setText(String.valueOf(data.getProductPrice()));
    }
}
