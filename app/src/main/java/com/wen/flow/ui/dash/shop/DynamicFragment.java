package com.wen.flow.ui.dash.shop;

import android.os.Bundle;
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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.wen.flow.R;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.paging.adapter.MongodbAdapter;
import com.wen.flow.ui.dash.shop.viemodel.MongodbViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.wen.flow.MyApplication.TAG;

public class DynamicFragment extends Fragment {
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
    }

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
