package com.wen.flow.ui.dash.shop.viemodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.wen.flow.MyApplication;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.paging.MongodbDataSource;
import com.wen.flow.paging.MongodbSourceFactory;

import static com.wen.flow.MyApplication.TAG;

public class MongodbViewModel extends ViewModel {

    public LiveData<PagedList<Mongodb.BooksBean>> itemPagedList;
    LiveData<MongodbDataSource> mongodbDataSourceMutableLiveData;

    public MongodbViewModel() {
        MongodbSourceFactory itemDataSourceFactory = new MongodbSourceFactory();
        mongodbDataSourceMutableLiveData = itemDataSourceFactory.getItemLiveDataSource();



        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(5).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();

        KLog.v(TAG +"MongodbViewModel -> mongodbDataSourceMutableLiveData:" + mongodbDataSourceMutableLiveData +"itemPagedList:" +itemPagedList);

    }

    public LiveData<PagedList<Mongodb.BooksBean>> getItemPagedList() {
        return itemPagedList;
    }
}