package com.wen.flow.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.Gson;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.network.webapi.BaseApi;
import com.wen.flow.network.webapi.IServiceApi;

import static com.wen.flow.MyApplication.TAG;

/// 繼承 PageKeyedDataSource，表示這是一個分頁數據源，它返回的數據類型是 <Integer, Mongodb.BooksBean>
public class MongodbDataSource extends PageKeyedDataSource<Integer, Mongodb.BooksBean> {
    // 用於查詢第一頁的變數
    private static final int FIRST_PAGE = 1;
    // 日誌標籤，用於調試



    @Override
    // 當需要加載初始數據時，Paging 將調用此方法
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Mongodb.BooksBean> callback) {
        KLog.v(TAG + "LoadInitialParams() params.placeholdersEnabled:" + params.placeholdersEnabled);

        // 使用 BaseApi 發起請求來獲取數據，createApi(IServiceApi.class) 創建了一個 API 服務的實例
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getAllPeople(FIRST_PAGE), new BaseApi.IResponseListener<Mongodb>() {
            @Override
            public void onSuccess(Mongodb data) {
                Mongodb mongodb = new Gson().fromJson(new Gson().toJson(data), Mongodb.class);
                // 將數據傳遞給回調函數，同時指定下一頁的頁碼
                callback.onResult(mongodb.getBooks(), null, FIRST_PAGE + 1);
                KLog.v(TAG + "loadInitial() onSuccess：" + FIRST_PAGE + 1);
                KLog.json(TAG + "loadInitial() onSuccess： jsson" + new Gson().toJson(mongodb.getBooks()));
            }

            @Override
            public void onFail() {
                KLog.v(TAG +"getBooksApi() onFail");
            }

            @Override
            public void onError(Throwable error) {
                KLog.v(TAG +"getBooksApi() onError");
            }
        });
    }

    @Override
    // 在之前加載數據的方法中不需要實現任何內容
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Mongodb.BooksBean> callback) {
    }

    @Override
    // 當需要加載更多數據時，Paging 將調用此方法
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Mongodb.BooksBean> callback) {
        KLog.v(TAG + "loadAfter() params.key:" + params.key);

        BaseApi.request(BaseApi.createApi(IServiceApi.class).getAllPeople(params.key), new BaseApi.IResponseListener<Mongodb>() {
            @Override
            public void onSuccess(Mongodb data) {
                KLog.v(TAG + "loadAfter() onSuccess：" + (params.key + 1));
                // 將響應數據解析為 Mongodb 對象，然後將數據傳遞給回調函數，同時指定下一頁的頁碼
                Mongodb mongodb = new Gson().fromJson(new Gson().toJson(data), Mongodb.class);
                callback.onResult(mongodb.getBooks(), params.key + 1);
            }

            @Override
            public void onFail() {
                KLog.v(TAG +"getBooksApi() onFail");
            }

            @Override
            public void onError(Throwable error) {
                KLog.v(TAG +"getBooksApi() onError");
            }
        });
    }
}
