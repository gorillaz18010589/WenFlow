package com.wen.flow.network.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.wen.flow.MyApplication;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.repository.BaseRepository;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.ApiResult;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.support.toast.TipsToast;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wen.flow.MyApplication.TAG;

public class BaseViewModel extends ViewModel {
    private BaseRepository baseRepository;
    private CompositeDisposable disposables = new CompositeDisposable();


    public BaseViewModel() {
        this.baseRepository = new BaseRepository();
    }

    public BaseRepository getBaseRepository() {
        return baseRepository;
    }

    //就方法
//    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Callable<Single<BaseResponse<T>>> requestCall) {
//        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();
//
//        try {
//            Single<BaseResponse<T>> responseSingle = requestCall.call();
//            disposables.add();
//            responseSingle
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(response -> {
//                        if (response != null) {
//                            if (response.isResult()) {
//                                resultLiveData.setValue(response);
//                            } else {
//                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
//                            }
//                        } else {
//                            resultLiveData.setValue(null);
//                        }
//                    }, throwable -> {
//                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
//                        // Handle error here
//                    });
//        } catch (Exception e) {
//            // Handle exception here
//            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
//        }
//
//        return resultLiveData;
//    }

    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Observable<BaseResponse<T>> requestCall) {
        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();

        try {
            Observable<BaseResponse<T>> responseSingle = requestCall;

            Disposable disposable = responseSingle  // 將訂閱賦值給 Disposable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null) {
                            if (response.isResult()) {
                                resultLiveData.setValue(response);
                            } else {
                                TipsToast.getInstance(MyApplication.getInstance()).showTips("requestResponse !isResult->");
                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
                            }
                        } else {
                            resultLiveData.setValue(null);
                        }
                    }, throwable -> {
                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
                        // Handle error here
                    });

            disposables.add(disposable);  // 將 Disposable 添加到 disposables 中
        } catch (Exception e) {
            // Handle exception here
            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
        }

        return resultLiveData;
    }

//    public <T, R> MutableLiveData<BaseResponse<R>> requestResponse(Observable<BaseResponse<T>> requestCall, Class<R> clazz) {
//        MutableLiveData<BaseResponse<R>> resultLiveData = new MutableLiveData<>();
//
//        try {
//            Observable<BaseResponse<T>> responseSingle = requestCall;
//
//            Disposable disposable = responseSingle
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(response -> {
//                        if (response != null) {
//                            if (response.isResult()) {
//                                resultLiveData.setValue(new BaseResponse<>(response.isResult(), response, new Gson().fromJson(new Gson().toJson(response,clazz)))); // Convert response to the desired class
////                                resultLiveData.setValue(response.convertTo(clazz)); // Convert response to the desired class
//                            } else {
//                                TipsToast.getInstance(MyApplication.getInstance()).showTips("requestResponse !isResult->");
//                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
//                            }
//                        } else {
//                            resultLiveData.setValue(null);
//                        }
//                    }, throwable -> {
//                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
//                        // Handle error here
//                    });
//
//            disposables.add(disposable);
//        } catch (Exception e) {
//            // Handle exception here
//            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
//        }
//
//        return resultLiveData;
//    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}