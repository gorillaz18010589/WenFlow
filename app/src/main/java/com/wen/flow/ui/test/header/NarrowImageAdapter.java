package com.wen.flow.ui.test.header;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wen.flow.ui.test.data.AppUtils;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;


public class NarrowImageAdapter extends RecyclerArrayAdapter<Integer> {
    private float imgWidth;

    public NarrowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NarrowImageViewHolder(parent);
    }

    private static class NarrowImageViewHolder extends BaseViewHolder<Integer>{
        ImageView imgPicture;

        NarrowImageViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView) itemView;
//            imgPicture.setLayoutParams(new ViewGroup.LayoutParams((int)
//                    AppUtils.convertDpToPixel(100, getContext()),
//                    ViewGroup.LayoutParams.MATCH_PARENT));

            imgPicture.setLayoutParams(new ViewGroup.LayoutParams((int) AppUtils.convertDpToPixel(100, getContext())
                    , ((int) AppUtils.convertDpToPixel(100, getContext()))
            ));
            imgPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        @Override
        public void setData(Integer data) {
            imgPicture.setImageResource(data);
        }
    }
}


//public class NarrowImageAdapter extends RecyclerArrayAdapter<Integer> {
//
//
//    public NarrowImageAdapter(Context context) {
//        super(context);
//    }
//
//    @Override
//    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        return new NarrowImageViewHolder(parent);
//    }
//
//    private static class NarrowImageViewHolder extends BaseViewHolder<Integer>{
//        ImageView imgPicture;
//
//        NarrowImageViewHolder(ViewGroup parent) {
//            super(new ImageView(parent.getContext()));
//            imgPicture = (ImageView) itemView;
//            imgPicture.setLayoutParams(new ViewGroup.LayoutParams((int)
//                    AppUtils.convertDpToPixel(80.0f,getContext()),
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//
//        @Override
//        public void setData(Integer data) {
//            imgPicture.setImageResource(data);
//        }
//    }
//}
