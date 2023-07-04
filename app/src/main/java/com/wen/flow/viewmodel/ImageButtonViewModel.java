package com.wen.flow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.wen.flow.enums.IconEnum;
import com.wen.flow.model.ImageButtonBean;

public class ImageButtonViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<IconEnum> iconEnumData;
    private MutableLiveData<ImageButtonBean> imageButtonBeanMutableLiveData;

    public ImageButtonViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<ImageButtonBean> getImageButtonBeanMutableLiveData() {
        if(imageButtonBeanMutableLiveData == null) {
            imageButtonBeanMutableLiveData = new MutableLiveData<>();
        }
        return imageButtonBeanMutableLiveData;
    }

    public void setImageButtonBeanMutableLiveData(ImageButtonBean imageButtonBean) {
        if(imageButtonBeanMutableLiveData != null)

        this.imageButtonBeanMutableLiveData.setValue(imageButtonBean);
    }

    public MutableLiveData<IconEnum> getIconEnumData() {
        if(iconEnumData == null){
            iconEnumData = new MutableLiveData<>();
        }
        return iconEnumData;
    }

    public void setIconEnumData(IconEnum iconEnum) {
        if(iconEnumData != null){
            iconEnumData.setValue(iconEnum);
        }
    }
}
