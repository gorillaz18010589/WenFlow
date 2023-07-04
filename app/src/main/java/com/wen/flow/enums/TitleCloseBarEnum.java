package com.wen.flow.enums;

import androidx.annotation.StringRes;

import com.wen.flow.MyApplication;
import com.wen.flow.R;

public enum TitleCloseBarEnum {
    Login(MyApplication.getInstance().getString(R.string.s_login_title)),
    Register(MyApplication.getInstance().getString(R.string.s_login_title)),
    ;

    TitleCloseBarEnum(String title) {
        this.title = title;
    }

    public String title;
}
