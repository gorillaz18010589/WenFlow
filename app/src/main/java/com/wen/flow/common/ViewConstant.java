package com.wen.flow.common;

import androidx.annotation.IntDef;

public class ViewConstant {

    //dialog Animation
    public static final int TRANSLATION_X = 0;
    public static final int TRANSLATION_Y = 1;
    public static final int ALPHA = 2;

    @IntDef({TRANSLATION_Y, TRANSLATION_X, ALPHA})
    public @interface animationType {
    }

}
