package com.wen.flow.support.custom.banner.indicator;

import android.view.View;

import androidx.annotation.NonNull;

import com.wen.flow.support.custom.banner.config.IndicatorConfig;
import com.wen.flow.support.custom.banner.listener.OnPageChangeListener;


public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
