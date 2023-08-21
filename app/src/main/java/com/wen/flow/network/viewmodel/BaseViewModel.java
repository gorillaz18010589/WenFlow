package com.wen.flow.network.viewmodel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.wen.flow.MyApplication;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.manager.app.ActivityManager;
import com.wen.flow.network.error.ApiException;
import com.wen.flow.network.error.ExceptionHandler;
import com.wen.flow.network.repository.BaseRepository;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.ApiResult;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.support.rx.RxBus;
import com.wen.flow.support.toast.TipsToast;
import com.wen.flow.support.util.LoadingUtils;
import com.wen.flow.support.util.NetworkUtils;

import java.util.List;
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
    private LoadingUtils loadingUtils;

    public LoadingUtils getLoadingUtils() {
        if (loadingUtils == null) {
            List<Activity> activities = ActivityManager.getTasksActivity();
            Activity activity = activities.get(activities.size() - 1);
            loadingUtils = new LoadingUtils(activity);
        }
        return loadingUtils;
    }

    public void showLoading(String txt, boolean cancel) {
        if (loadingUtils != null) loadingUtils.show(txt, cancel);
    }

    public void disMissLoading() {
        if (loadingUtils != null) loadingUtils.dismissLoading();
    }

    public BaseViewModel() {
        this.baseRepository = new BaseRepository();
    }

    public BaseRepository getBaseRepository() {
        return baseRepository;
    }

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
                                resultLiveData.setValue(response);
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

    /*
    * @param requestCall
    * @param zClazz
    * @param isShowLoadingView
    *
    * */
    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Observable<BaseResponse<T>> requestCall, Class<T> zClazz, boolean isShowLoadingView) {
        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();

        if (isShowLoadingView) getLoadingUtils().show("請稍候...", false);

        if (!NetworkUtils.isConnected(MyApplication.getInstance())) {
            if (isShowLoadingView) getLoadingUtils().dismissLoading();
            TipsToast.getInstance(MyApplication.getInstance()).showTips("無網路");
            resultLiveData.setValue(new BaseResponse<T>(false, new BaseResponse.ErrorData("無網路", 0), null));
            return resultLiveData;
        }

        try {
            Observable<BaseResponse<T>> responseSingle = requestCall;

            Disposable disposable = responseSingle  // 將訂閱賦值給 Disposable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (isShowLoadingView) getLoadingUtils().dismissLoading();

                        if (response != null) {
                            if (response.isResult()) {
                                if (response.getBean() != null) {
                                    T result = new Gson().fromJson(new Gson().toJson(response.getBean()), zClazz);
                                    resultLiveData.setValue(new BaseResponse<T>(response.isResult(), null, result));
                                } else {
                                    resultLiveData.setValue(new BaseResponse<T>(response.isResult(), response.getErrorData(), null));
                                }
                            } else {
                                resultLiveData.setValue(new BaseResponse<T>(response.isResult(), response.getErrorData(), null));
                                TipsToast.getInstance(MyApplication.getInstance()).showTips(response.getErrorData().getErrMsg());
                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
                            }
                        } else {
                            TipsToast.getInstance(MyApplication.getInstance()).showTips("response == null");
                            resultLiveData.setValue(null);
                        }
                    }, throwable -> {
                        if (isShowLoadingView) getLoadingUtils().dismissLoading();
                        ApiException apiException = ExceptionHandler.handleException(throwable);
                        TipsToast.getInstance(MyApplication.getInstance()).showTips(apiException.getErrMsg());
                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
                        // Handle error here
                    });
            disposables.add(disposable);  // 將 Disposable 添加到 disposables 中
        } catch (Exception e) {
            // Handle exception here
            if (isShowLoadingView) getLoadingUtils().dismissLoading();
            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
        }

        return resultLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

//public class BaseViewModel extends ViewModel {
//    private BaseRepository baseRepository;
//    private CompositeDisposable disposables = new CompositeDisposable();
//    private LoadingUtils loadingUtils;
//
//    public LoadingUtils getLoadingUtils() {
//        if (loadingUtils == null) {
//            List<Activity> activities = ActivityManager.getTasksActivity();
//            Activity activity = activities.get(activities.size() - 1);
//            loadingUtils = new LoadingUtils(activity);
//        }
//        return loadingUtils;
//    }
//
//    public void showLoading(String txt, boolean cancel) {
//        if (loadingUtils != null) loadingUtils.show(txt, cancel);
//    }
//
//    public void disMissLoading() {
//        if (loadingUtils != null) loadingUtils.dismissLoading();
//    }
//
//    public BaseViewModel() {
//        this.baseRepository = new BaseRepository();
//    }
//
//    public BaseRepository getBaseRepository() {
//        return baseRepository;
//    }
//
//    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Observable<BaseResponse<T>> requestCall) {
//        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();
//
//        try {
//            Observable<BaseResponse<T>> responseSingle = requestCall;
//
//            Disposable disposable = responseSingle  // 將訂閱賦值給 Disposable
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(response -> {
//                        if (response != null) {
//                            if (response.isResult()) {
//                                resultLiveData.setValue(response);
//                            } else {
//                                resultLiveData.setValue(response);
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
//            disposables.add(disposable);  // 將 Disposable 添加到 disposables 中
//        } catch (Exception e) {
//            // Handle exception here
//            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
//        }
//
//        return resultLiveData;
//    }
//
//    /*
//    * @param requestCall
//    * @param zClazz
//    * @param isShowLoadingView
//    *
//    * */
//    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Observable<BaseResponse<T>> requestCall, Class<T> zClazz, boolean isShowLoadingView) {
//        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();
//
//        if (isShowLoadingView) getLoadingUtils().show("請稍候...", false);
//
//        if (!NetworkUtils.isConnected(MyApplication.getInstance())) {
//            if (isShowLoadingView) getLoadingUtils().dismissLoading();
//            TipsToast.getInstance(MyApplication.getInstance()).showTips("無網路");
//            resultLiveData.setValue(new BaseResponse<T>(false, new BaseResponse.ErrorData("無網路", 0), null));
//            return resultLiveData;
//        }
//
//        try {
//            Observable<BaseResponse<T>> responseSingle = requestCall;
//
//            Disposable disposable = responseSingle  // 將訂閱賦值給 Disposable
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(response -> {
//                        if (isShowLoadingView) getLoadingUtils().dismissLoading();
//
//                        if (response != null) {
//                            if (response.isResult()) {
//                                if (response.getBean() != null) {
//                                    T result = new Gson().fromJson(new Gson().toJson(response.getBean()), zClazz);
//                                    resultLiveData.setValue(new BaseResponse<T>(response.isResult(), null, result));
//                                } else {
//                                    resultLiveData.setValue(new BaseResponse<T>(response.isResult(), response.getErrorData(), null));
//                                }
//                            } else {
//                                resultLiveData.setValue(new BaseResponse<T>(response.isResult(), response.getErrorData(), null));
//                                TipsToast.getInstance(MyApplication.getInstance()).showTips(response.getErrorData().getErrMsg());
//                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
//                            }
//                        } else {
//                            TipsToast.getInstance(MyApplication.getInstance()).showTips("response == null");
//                            resultLiveData.setValue(null);
//                        }
//                    }, throwable -> {
//                        if (isShowLoadingView) getLoadingUtils().dismissLoading();
//                        ApiException apiException = ExceptionHandler.handleException(throwable);
//                        TipsToast.getInstance(MyApplication.getInstance()).showTips(apiException.getErrMsg());
//                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
//                        // Handle error here
//                    });
//            disposables.add(disposable);  // 將 Disposable 添加到 disposables 中
//        } catch (Exception e) {
//            // Handle exception here
//            if (isShowLoadingView) getLoadingUtils().dismissLoading();
//            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
//        }
//
//        return resultLiveData;
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        disposables.clear();
//    }
//}