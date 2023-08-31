package com.wen.flow.ui.dash.shop;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.google.gson.Gson;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentShopBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.network.webapi.BaseApi;
import com.wen.flow.network.webapi.IServiceApi;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.custom.banner.indicator.CircleIndicator;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopFragment extends BaseFragment<FragmentShopBinding> {
    private ShopCategoryBannerAdapter shopCategoryBannerAdapter;
    private List<BannerCategory> bannerCategoryList = new ArrayList<>();
    private List<BannerCategory.Category> categoryList = new ArrayList<>();
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        initCategoryFragment();
//        getBooksApi();
    }

    private void initCategoryFragment() {

        List<PopularCategory> list = new ArrayList<>();
        list.add(new PopularCategory(0, "籃球", R.drawable.ic_baseline_sports_baseball_24));
        list.add(new PopularCategory(1, "羽球", R.drawable.ic_baseline_sports_baseball_24));
        list.add(new PopularCategory(2, "排球", R.drawable.ic_baseline_sports_baseball_24));
        list.add(new PopularCategory(3, "躲避球", R.drawable.ic_baseline_sports_baseball_24));
        PopularCategoryAdapter pagerAdapter = new PopularCategoryAdapter(list);

        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("運動");
        tabTitles.add("鞋子");
        tabTitles.add("衣服");
        tabTitles.add("配件");

        HashMap<Integer, ArrayList<PopularCategory>> hashMap = new HashMap<>();

        ArrayList<PopularCategory> list1 = new ArrayList<>();
        list1.add(new PopularCategory(0, "籃球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(1, "羽球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(2, "排球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(3, "躲避球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(4, "躲避球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(5, "躲避球", R.drawable.ic_baseline_sports_baseball_24));
        list1.add(new PopularCategory(6, "躲避球", R.drawable.ic_baseline_sports_baseball_24));

        ArrayList<PopularCategory> list2 = new ArrayList<>();
        list2.add(new PopularCategory(0, "鞋子", R.drawable.ic_baseline_sports_baseball_24));
        list2.add(new PopularCategory(1, "褲子", R.drawable.ic_baseline_sports_baseball_24));
        list2.add(new PopularCategory(2, "球褲", R.drawable.ic_baseline_sports_baseball_24));
        list2.add(new PopularCategory(3, "羽球", R.drawable.ic_baseline_sports_baseball_24));

        ArrayList<PopularCategory> list3 = new ArrayList<>();
        list3.add(new PopularCategory(0, "鞋子", R.drawable.ic_baseline_sports_baseball_24));
        list3.add(new PopularCategory(1, "褲子", R.drawable.ic_baseline_sports_baseball_24));
        list3.add(new PopularCategory(2, "球褲", R.drawable.ic_baseline_sports_baseball_24));
        list3.add(new PopularCategory(3, "羽球", R.drawable.ic_baseline_sports_baseball_24));

        ArrayList<PopularCategory> list4 = new ArrayList<>();
        list4.add(new PopularCategory(0, "鞋子", R.drawable.ic_baseline_sports_baseball_24));
        list4.add(new PopularCategory(1, "褲子", R.drawable.ic_baseline_sports_baseball_24));
        list4.add(new PopularCategory(2, "球褲", R.drawable.ic_baseline_sports_baseball_24));
        list4.add(new PopularCategory(3, "羽球", R.drawable.ic_baseline_sports_baseball_24));

        hashMap.put(0, list1);
        hashMap.put(1, list2);
        hashMap.put(2, list3);
        hashMap.put(3, list4);
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabTitles.size(); i++) {
            fragmentList.add(DynamicFragment.newInstance(tabTitles.get(i), hashMap.get(i)));
        }


        binding.viewPager.setAdapter(new FragmentPageResumeAdapter(getChildFragmentManager(), fragmentList));
        binding.viewPager.setOffscreenPageLimit(fragmentList.size());

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
//                titleView.setNormalColor(getResources().getColor(R.color.blue));
                titleView.setNormalColor(context.getColor(R.color.grays));
                titleView.setSelectedColor(getResources().getColor(R.color.color_on_primary));
                titleView.setTextSize(16);
                titleView.setText(tabTitles.get(index));

                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.viewPager.setCurrentItem(index);
                    }
                });

//
//                titleView.setOnSelectListener(new OnSelectListener() {
//                    @Override
//                    public void onSelect(int index, int totalCount) {
//                        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//                    }
//
//                    @Override
//                    public void onDeselect(int index, int totalCount) {
//                        titleView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
//                    }
//                });

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(2);
                indicator.setLineWidth(20);
                indicator.setRoundRadius(UIUtil.dip2px(context, 1.0f));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setYOffset(context.getResources().getDimension(R.dimen.dp_7));
                indicator.setColors(getResources().getColor(R.color.color_80707070));
                return indicator;
            }
        });

        binding.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.magicIndicator, binding.viewPager);
    }

    private void getBooksApi(){
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getAllPeople(3), new BaseApi.IResponseListener<Mongodb>() {
            @Override
            public void onSuccess(Mongodb data) {
                KLog.json(new Gson().toJson(data));
            }

            @Override
            public void onFail() {
                KLog.v("getBooksApi() onFail");
            }

            @Override
            public void onError(Throwable error) {
                KLog.v("getBooksApi() onError");
            }
        });
    }


    @Override
    protected void initListeners() {
    }
}