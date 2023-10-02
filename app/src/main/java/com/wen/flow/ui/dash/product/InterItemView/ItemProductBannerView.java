package com.wen.flow.ui.dash.product.InterItemView;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.R;
import com.wen.flow.ui.dash.product.TextRectangleHintView;
import com.wen.flow.ui.dash.product.adapter.ProductBannerAdapter;
import com.wen.flow.ui.test.data.AdData;
import com.wen.flow.ui.test.data.AppUtils;
import com.yc.cn.ycbannerlib.banner.BannerView;

import org.yczbj.ycrefreshviewlib.inter.InterItemView;

import java.util.ArrayList;
import java.util.List;

public class ItemProductBannerView implements InterItemView {
    @Override
    public View onCreateView(ViewGroup parent) {
        List<AdData> adData = new ArrayList<>();
        for(int i=0; i< 9; i++){
            adData.add(new AdData("測試" + i , R.drawable.photp_nike_low));
        }
        BannerView bannerView = new BannerView(parent.getContext());
        TextRectangleHintView textRectangleHintView = new TextRectangleHintView(parent.getContext());
        textRectangleHintView.setCurrent(1);
        textRectangleHintView.initView(1,9);
        bannerView.setHintView(textRectangleHintView);
        bannerView.setPlayDelay(2 *1000);
        bannerView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) AppUtils.convertDpToPixel(200, parent.getContext())));
        bannerView.setAdapter(new ProductBannerAdapter(parent.getContext(),adData));
        return bannerView;    }

    @Override
    public void onBindView(View headerView) {

    }
}
