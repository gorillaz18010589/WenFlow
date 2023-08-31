package com.wen.flow.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.wen.flow.network.response.Mongodb;

import static com.wen.flow.MyApplication.TAG;


public class MongodbSourceFactory extends DataSource.Factory<Integer, Mongodb.BooksBean> {
    private MutableLiveData<MongodbDataSource> itemLiveDataSource = new MutableLiveData<>();

    public MutableLiveData<MongodbDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }


    @NonNull
    @Override
    public DataSource<Integer, Mongodb.BooksBean> create() {

        MongodbDataSource mongodbDataSource = new MongodbDataSource();

        itemLiveDataSource.postValue(mongodbDataSource);

        Log.v(TAG,"MongodbSourceFactory -> mongodbDataSource:" + mongodbDataSource  +", itemLiveDataSource:" + itemLiveDataSource);
        return mongodbDataSource;
    }

}



