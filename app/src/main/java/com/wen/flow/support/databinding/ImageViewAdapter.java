package com.wen.flow.support.databinding;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageViewAdapter {
    @BindingAdapter("app:setSrc")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("app:setSrc")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("android:scrGlide")
    public static void setSrcGlide(ImageView view, int resId) {
        Glide.with(view.getContext()).load(resId)
                .into(view);
    }

    @BindingAdapter("imageUrl")
    public static void setSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
//                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter("imageUrl")
    public static void setImageView(ImageView imageView, Integer resourceId) {
        Glide.with(imageView.getContext()).load(resourceId)
//                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter("ttt")
    public static void ttt(ImageView imageView, Integer resourceId) {
        Glide.with(imageView.getContext()).load(resourceId)
//                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }


}