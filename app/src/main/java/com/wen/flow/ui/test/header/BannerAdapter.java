package com.wen.flow.ui.test.header;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wen.flow.R;
import com.wen.flow.ui.test.data.AdData;
import com.wen.flow.ui.test.data.DataProvider;
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;



import java.util.List;

public class BannerAdapter extends AbsStaticPagerAdapter {

    private Context ctx;
    private List<AdData> list;

    public BannerAdapter(Context ctx) {
        this.ctx = ctx;
        list = DataProvider.getAdList();
    }

    public BannerAdapter(Context ctx, List<AdData> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //加载图片
//        Glide.with(ctx)
//                .load(list.get(position).getImage())
//                .placeholder(R.drawable.icon_shop_checked)
//                .into(imageView);
        imageView.setImageResource(list.get(position).getDrawable());
        //点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

//public class BannerAdapter extends AbsStaticPagerAdapter {
//
//    private Context ctx;
//    private List<AdData> list;
//
//    public BannerAdapter(Context ctx) {
//        this.ctx = ctx;
//        list = DataProvider.getAdList();
//    }
//
//    @Override
//    public View getView(ViewGroup container, final int position) {
//        ImageView imageView = new ImageView(ctx);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        //加载图片
//        Glide.with(ctx)
//                .load(list.get(position).getImage())
//                .placeholder(R.drawable.icon_shop_checked)
//                .into(imageView);
//        //点击事件
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        return imageView;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//}