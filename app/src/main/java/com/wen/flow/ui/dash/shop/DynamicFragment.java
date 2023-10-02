package com.wen.flow.ui.dash.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.wen.flow.R;
import com.wen.flow.common.FragmentConstant;
import com.wen.flow.databinding.FragmentDynamicBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.paging.adapter.MongodbAdapter;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.ui.dash.DashActivity;
import com.wen.flow.ui.dash.shop.model.PopularCategory;
import com.wen.flow.ui.dash.shop.model.Product;
import com.wen.flow.ui.dash.shop.model.Shop;
import com.wen.flow.ui.dash.shop.viemodel.MongodbViewModel;
import com.wen.flow.ui.test.header.HeaderFooterActivity;

import java.util.ArrayList;
import java.util.List;

import static com.wen.flow.MyApplication.TAG;
import static com.wen.flow.common.FragmentConstant.FROM_SHOP_TO_PRODUCT_BUNDLE_PARCELABLE;

public class DynamicFragment extends BaseFragment<FragmentDynamicBinding> {
    private static final String ARGUMENT_TAB_TITLE = "tab_title";
    private static final String ARGUMENT_ITEMS1 = "items1";
    private ArrayList<PopularCategory> popularCategoryArrayList;
    private MongodbViewModel mongodbViewModel;
    private RecyclerView recyclerViewCommodity;
    private MongodbAdapter mongodbAdapter;




    public static DynamicFragment newInstance(String tabTitle, RecyclerView.Adapter adapter) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_TAB_TITLE, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public static DynamicFragment newInstance(String tabTitle, ArrayList<PopularCategory>  popularCategories) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_TAB_TITLE, tabTitle);
        args.putParcelableArrayList(ARGUMENT_ITEMS1,popularCategories);
        fragment.setArguments(args);
        return fragment;
    }

//    private void setAdapter(RecyclerView.Adapter adapter) {
//        this.adapter = adapter;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);


        recyclerViewCommodity = view.findViewById(R.id.recyclerViewCommodity);

        Bundle args = getArguments();
        if (args != null) {
            String tabTitle = args.getString(ARGUMENT_TAB_TITLE);
            popularCategoryArrayList = args.getParcelableArrayList(ARGUMENT_ITEMS1);

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(tabTitle);

            PopularCategoryAdapter popularCategoryAdapter = new PopularCategoryAdapter(popularCategoryArrayList);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(popularCategoryAdapter);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setShopRecyclerView();
        initAdapterData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    private void initAdapterData() {
        List<BaseAdapterBean> dataList = new ArrayList<>();

// 添加10个热门分类
        for (int i = 0; i < 10; i++) {
            PopularCategory category = new PopularCategory(i, "熱門鞋款 " + i, R.drawable.ic_baseline_mic_none_24);
            category.setViewType(ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE);
            dataList.add(category);
        }

// 添加10个商品
        for (int i = 0; i < 10; i++) {
            Product product = new Product(i, "Nike ari Force " + i, 3200 + i, R.drawable.photp_nike_low);
            product.setViewType(ShopAdapter.PRODUCT_VIEW_TYPE);
            dataList.add(product);
        }

        KLog.json("dataList"+new Gson().toJson(dataList));

        RecyclerView rvShop = getView().findViewById(R.id.rvShop);

        ShopAdapter adapter = new ShopAdapter(dataList);
        adapter.setOnProductItemClickListener(new ShopAdapter.OnProductItemClickListener() {
            @Override
            public void onProductClick(Product product) {
                startProductFragment(product);
            }
        });

// 设置LayoutManager，每行显示5个热门分类或2个商品
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 10);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                if (viewType == ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE) {
                    return 2; // 5列
                } else {
                    return 5; // 1列
                }
            }
        });
        rvShop.addItemDecoration(new ShopItemDecoration());
        rvShop.setLayoutManager(layoutManager);

// 设置间距
//        int spacingInPixels = 30; // 30dp
//        rvShop.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));

// 创建适配器并设置给RecyclerView

        rvShop.setAdapter(adapter);
    }
    private void startProductFragment(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(FROM_SHOP_TO_PRODUCT_BUNDLE_PARCELABLE, product);
        Navigation.findNavController(getView()).navigate(R.id.action_shopFragment_to_productDialogFragment, bundle);
//        mActivity.startActivity(new Intent(mActivity, HeaderFooterActivity.class));
    }


    @Override
    protected void initListeners() {

    }


//    private void initData() {
//        List<BaseAdapterBean> dataList = new ArrayList<>();
//
//// 添加10个热门分类
//        for (int i = 0; i < 10; i++) {
//            PopularCategory category = new PopularCategory(i, "熱門鞋款 " + i, R.drawable.ic_baseline_mic_none_24);
//            category.setViewType(ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE);
//            dataList.add(category);
//        }
//
//// 添加10个商品
//        for (int i = 0; i < 10; i++) {
//            Product product = new Product(i, "Nike ari Force " + i, 3200 + i, R.drawable.photp_nike_low);
//            product.setViewType(ShopAdapter.PRODUCT_VIEW_TYPE);
//            dataList.add(product);
//        }
//
//        KLog.json("dataList"+new Gson().toJson(dataList));
//
//        RecyclerView rvShop = getView().findViewById(R.id.rvShop);
//
//        ShopAdapter adapter = new ShopAdapter(dataList);
//
//// 设置LayoutManager，每行显示5个热门分类或2个商品
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 10);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int viewType = adapter.getItemViewType(position);
//                if (viewType == ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE) {
//                    return 2; // 5列
//                } else {
//                    return 5; // 1列
//                }
//            }
//        });
//        rvShop.addItemDecoration(new ShopItemDecoration());
//        rvShop.setLayoutManager(layoutManager);
//
//// 设置间距
////        int spacingInPixels = 30; // 30dp
////        rvShop.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
//
//// 创建适配器并设置给RecyclerView
//
//        rvShop.setAdapter(adapter);
//    }


    public void setShopRecyclerView(){
        mongodbAdapter = new MongodbAdapter(getContext());

        mongodbViewModel = new ViewModelProvider(getActivity()).get(MongodbViewModel.class);

        mongodbViewModel.getItemPagedList().observe(getActivity(), new Observer<PagedList<Mongodb.BooksBean>>() {
            @Override
            public void onChanged(PagedList<Mongodb.BooksBean> booksBeans) {
                KLog.json("itemPagedList->:" + new Gson().toJson(booksBeans));
                mongodbAdapter.submitList(booksBeans);
                KLog.json(TAG +"setShopRecyclerView()"+new Gson().toJson(booksBeans));
                Toast.makeText(getActivity(),"setShopRecyclerView itemPagedList -> onChanged()" ,Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewCommodity.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewCommodity.setAdapter(mongodbAdapter);
    }

}


//public class DynamicFragment extends Fragment {
//    private static final String ARGUMENT_TAB_TITLE = "tab_title";
//    private static final String ARGUMENT_ITEMS1 = "items1";
//    private ArrayList<PopularCategory> popularCategoryArrayList;
//    private MongodbViewModel mongodbViewModel;
//    private RecyclerView recyclerViewCommodity;
//    private MongodbAdapter mongodbAdapter;
//
//
//
//    public static DynamicFragment newInstance(String tabTitle, RecyclerView.Adapter adapter) {
//        DynamicFragment fragment = new DynamicFragment();
//        Bundle args = new Bundle();
//        args.putString(ARGUMENT_TAB_TITLE, tabTitle);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public static DynamicFragment newInstance(String tabTitle, ArrayList<PopularCategory>  popularCategories) {
//        DynamicFragment fragment = new DynamicFragment();
//        Bundle args = new Bundle();
//        args.putString(ARGUMENT_TAB_TITLE, tabTitle);
//        args.putParcelableArrayList(ARGUMENT_ITEMS1,popularCategories);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
////    private void setAdapter(RecyclerView.Adapter adapter) {
////        this.adapter = adapter;
////    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
//
//
//        recyclerViewCommodity = view.findViewById(R.id.recyclerViewCommodity);
//
//        Bundle args = getArguments();
//        if (args != null) {
//            String tabTitle = args.getString(ARGUMENT_TAB_TITLE);
//            popularCategoryArrayList = args.getParcelableArrayList(ARGUMENT_ITEMS1);
//
//            TextView textView = view.findViewById(R.id.textView);
//            textView.setText(tabTitle);
//
//            PopularCategoryAdapter popularCategoryAdapter = new PopularCategoryAdapter(popularCategoryArrayList);
//            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
//            recyclerView.setLayoutManager(gridLayoutManager);
//            recyclerView.setAdapter(popularCategoryAdapter);
//        }
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        setShopRecyclerView();
//        initData();
//    }
//
//    private void initData() {
//        List<BaseAdapterBean> dataList = new ArrayList<>();
//
//// 添加10个热门分类
//        for (int i = 0; i < 10; i++) {
//            PopularCategory category = new PopularCategory(i, "熱門鞋款 " + i, R.drawable.ic_baseline_mic_none_24);
//            category.setViewType(ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE);
//            dataList.add(category);
//        }
//
//// 添加10个商品
//        for (int i = 0; i < 10; i++) {
//            Product product = new Product(i, "Nike ari Force " + i, 3200 + i, R.drawable.photp_nike_low);
//            product.setViewType(ShopAdapter.PRODUCT_VIEW_TYPE);
//            dataList.add(product);
//        }
//
//        KLog.json("dataList"+new Gson().toJson(dataList));
//
//        RecyclerView rvShop = getView().findViewById(R.id.rvShop);
//
//        ShopAdapter adapter = new ShopAdapter(dataList);
//        adapter.setOnProductItemClickListener(new ShopAdapter.OnProductItemClickListener() {
//            @Override
//            public void onProductClick(Product product) {
//
//            }
//        });
//
//// 设置LayoutManager，每行显示5个热门分类或2个商品
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 10);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int viewType = adapter.getItemViewType(position);
//                if (viewType == ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE) {
//                    return 2; // 5列
//                } else {
//                    return 5; // 1列
//                }
//            }
//        });
//        rvShop.addItemDecoration(new ShopItemDecoration());
//        rvShop.setLayoutManager(layoutManager);
//
//// 设置间距
////        int spacingInPixels = 30; // 30dp
////        rvShop.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
//
//// 创建适配器并设置给RecyclerView
//
//        rvShop.setAdapter(adapter);
//    }
//
//
////    private void initData() {
////        List<BaseAdapterBean> dataList = new ArrayList<>();
////
////// 添加10个热门分类
////        for (int i = 0; i < 10; i++) {
////            PopularCategory category = new PopularCategory(i, "熱門鞋款 " + i, R.drawable.ic_baseline_mic_none_24);
////            category.setViewType(ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE);
////            dataList.add(category);
////        }
////
////// 添加10个商品
////        for (int i = 0; i < 10; i++) {
////            Product product = new Product(i, "Nike ari Force " + i, 3200 + i, R.drawable.photp_nike_low);
////            product.setViewType(ShopAdapter.PRODUCT_VIEW_TYPE);
////            dataList.add(product);
////        }
////
////        KLog.json("dataList"+new Gson().toJson(dataList));
////
////        RecyclerView rvShop = getView().findViewById(R.id.rvShop);
////
////        ShopAdapter adapter = new ShopAdapter(dataList);
////
////// 设置LayoutManager，每行显示5个热门分类或2个商品
////        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 10);
////        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////            @Override
////            public int getSpanSize(int position) {
////                int viewType = adapter.getItemViewType(position);
////                if (viewType == ShopAdapter.POPULAR_CATEGORY_VIEW_TYPE) {
////                    return 2; // 5列
////                } else {
////                    return 5; // 1列
////                }
////            }
////        });
////        rvShop.addItemDecoration(new ShopItemDecoration());
////        rvShop.setLayoutManager(layoutManager);
////
////// 设置间距
//////        int spacingInPixels = 30; // 30dp
//////        rvShop.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
////
////// 创建适配器并设置给RecyclerView
////
////        rvShop.setAdapter(adapter);
////    }
//
//
//    public void setShopRecyclerView(){
//        mongodbAdapter = new MongodbAdapter(getContext());
//
//        mongodbViewModel = new ViewModelProvider(getActivity()).get(MongodbViewModel.class);
//
//        mongodbViewModel.getItemPagedList().observe(getActivity(), new Observer<PagedList<Mongodb.BooksBean>>() {
//            @Override
//            public void onChanged(PagedList<Mongodb.BooksBean> booksBeans) {
//                KLog.json("itemPagedList->:" + new Gson().toJson(booksBeans));
//                mongodbAdapter.submitList(booksBeans);
//                KLog.json(TAG +"setShopRecyclerView()"+new Gson().toJson(booksBeans));
//                Toast.makeText(getActivity(),"setShopRecyclerView itemPagedList -> onChanged()" ,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        recyclerViewCommodity.setLayoutManager(new GridLayoutManager(getContext(),2));
//        recyclerViewCommodity.setAdapter(mongodbAdapter);
//    }
//
//}