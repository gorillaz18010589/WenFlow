package com.wen.flow.ui.dash.product.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.chip.Chip;
import com.wen.flow.R;
import com.wen.flow.ui.test.data.AdData;
import com.yc.cn.ycbannerlib.banner.adapter.AbsDynamicPagerAdapter;
import com.yc.cn.ycbannerlib.banner.adapter.AbsLoopPagerAdapter;
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;

import java.util.List;

public class ProductBannerAdapter extends AbsStaticPagerAdapter {
    private Context mContent;
    private List<AdData> mData;


    public ProductBannerAdapter(Context mContent, List<AdData> mData) {
        this.mContent = mContent;
        this.mData = mData;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ConstraintLayout constraintLayout = new ConstraintLayout(mContent);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        ImageView imageView = new ImageView(mContent);
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        Chip chip = new Chip(mContent);
        chip.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        constraintLayout.setId(R.id.banner_container);
        chip.setId(R.id.banner_chip);
        imageView.setId(R.id.banner_img);

        constraintLayout.addView(imageView);
        constraintLayout.addView(chip);

        chip.setText("1/9");
        chip.setTextSize(16);
        chip.setChipCornerRadius(50f);
        chip.setClickable(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mData.get(position).getDrawable());

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

// 設置 Chip 的約束
        constraintSet.connect(chip.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 10);
        constraintSet.connect(chip.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 10);

// Apply the constraints
        constraintSet.applyTo(constraintLayout);

        return constraintLayout;
    }

    @Override
    public int getCount() {
        return mData.size();
    }


}
