package com.wen.flow.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.wen.flow.MyApplication;
import com.wen.flow.support.util.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.wen.flow.MyApplication.TAG;

public class BaseApi {
    private static final String LOCAL_SERVER_URL = "http://172.20.10.5:2365/api/";
//    private static final String LOCAL_SERVER_URL = "http://127.0.0.1:2365/api/";
//    private static final String UPDATE_URL = BuildConfig.UPDATE_URL;

    public static <T> T createApi(Class<T> service) {

        //默認情況下，Gson是嚴格的，只接受RFC 4627指定的JSON。此選項使解析器在接受的內容中更加自由。
        //    Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOCAL_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值為String的支持(要排第一)
                .addConverterFactory(GsonConverterFactory.create())//增加返回值為为GSON的支持(以實體類返回)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//增加返回值為Observable<T>的支持
                .client(MyApplication.getInstance().genericClient())
                .build();
        return retrofit.create(service);
    }

    public static <T> T createApi2(Class<T> service, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MyApplication.getInstance().genericClient())
                .build();
        return retrofit.create(service);
    }

    public static <T> void request(Observable<T> observable, final IResponseListener<T> listener) {

        //  final CompositeDisposable compositeDisposable = new CompositeDisposable();

        if (!NetworkUtils.isConnected(MyApplication.getInstance())) {
            //  ToastUtils.getInstance().showToast("無網路");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                               @Override
                               public void onError(@NonNull Throwable e) {
                                   e.printStackTrace();
                                   //   Log.d("TIME_ZONE", Objects.requireNonNull(e.getMessage()));
                                   if (listener != null) {
                                       listener.onError(e);
                                   }
                               }

                               @Override
                               public void onComplete() {
                                   // Log.d("@@@e@@", "onComplete: ");
                                   //       compositeDisposable.dispose();
                               }

                               @Override
                               public void onSubscribe(@NonNull Disposable disposable) {
                                   //   Log.d("@@@e@@", "onSubscribe: ");
                                   //      compositeDisposable.add(disposable);
                               }

                               @Override
                               public void onNext(@NonNull T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }
                );
    }

    public interface IResponseListener<T> {
        void onSuccess(T data);

        void onFail();

        void onError(Throwable error);
    }


    //  private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    //  public static void apiClear() {
//        Log.i("WEB_API", "apiClear: " + compositeDisposable.size());
//        compositeDisposable.clear();
//        Log.i("WEB_API", "apiClear: " + compositeDisposable.size());
    //  }
}
