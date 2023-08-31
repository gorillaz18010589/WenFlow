package com.wen.flow.ui.dash.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.R;
import com.wen.flow.support.custom.banner.Banner;
import com.wen.flow.support.custom.banner.adapter.BannerAdapter;

import java.util.List;

public class ShopCategoryBannerAdapter extends BannerAdapter<BannerCategory, ShopCategoryBannerAdapter.ShopCategoryHolder> {

    public ShopCategoryBannerAdapter(List<BannerCategory> datas) {
        super(datas);
    }

    @Override
    public ShopCategoryHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_catrgory,parent,false);
//        view.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        ShopCategoryHolder categoryHolder = new ShopCategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindView(ShopCategoryHolder holder, BannerCategory data, int position, int size) {
        BannerCategory bannerCategory = getData(position);
        int type = bannerCategory.getType();
        List<BannerCategory.Category> categoryList = bannerCategory.getCategoryList();


        for(BannerCategory.Category category : categoryList){
            if(category.getCategoryId() == type){
                holder.img.setImageResource(category.getCategoryImg());
                holder.tvName.setText(category.getCategoryName());
            }
        }

    }

    public class ShopCategoryHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName;
        public ShopCategoryHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
