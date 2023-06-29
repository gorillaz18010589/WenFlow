package com.wen.flow.support.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.DrawableRes;

import com.wen.flow.R;
import com.wen.flow.databinding.DialogLoadingBinding;


/**
 * 通用加载中弹窗
 */
public class CenterLoadingView extends Dialog {
    private DialogLoadingBinding mBinding;
    private Animation animation;
    private Context context;

    public CenterLoadingView(Context context) {
        super(context, R.style.loading_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context));
        setContentView(mBinding.getRoot());
        initAnim();
    }

    private void initAnim() {
        animation = new RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        animation.setDuration(2000);
        animation.setRepeatCount(40);
        animation.setFillAfter(true);
    }

    @Override
    public void show() {
        super.show();
        mBinding.ivImage.startAnimation(animation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mBinding.ivImage.clearAnimation();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (title != null && !title.toString().isEmpty()) {
            mBinding.tvMsg.setText(title);
        }
    }

    public void setLoadingImage(@DrawableRes int resId){
        mBinding.ivImage.setBackground(getContext().getDrawable(resId));
    }
}