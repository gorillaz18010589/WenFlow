package com.wen.flow.ui.dash.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.R;

import java.util.List;

public class PopularCategoryAdapter extends RecyclerView.Adapter<PopularCategoryAdapter.PopularCategoryHolder> {
    private List<PopularCategory> mData;

    public PopularCategoryAdapter(List<PopularCategory> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public PopularCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_catrgory, parent, false);
        PopularCategoryHolder popularCategoryHolder = new PopularCategoryHolder(view);
        return popularCategoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularCategoryHolder holder, int position) {
        holder.img.setImageResource(mData.get(position).getPopularCategoryImg());
        holder.tvName.setText(mData.get(position).getPopularCategoryName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PopularCategoryHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName;

        public PopularCategoryHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}


