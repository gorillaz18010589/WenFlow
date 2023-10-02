package com.wen.flow.ui.dash.product.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.wen.flow.ui.test.data.AppUtils;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

public class ProductPhotoTypeAdapter extends RecyclerArrayAdapter<Integer> {
    private Context mContent;
    public ProductPhotoTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductPhotoTypeViewHolder(parent);
    }


    public class ProductPhotoTypeViewHolder extends BaseViewHolder<Integer> {
        private ImageView imgPhoto;
        public ProductPhotoTypeViewHolder(ViewGroup viewGroup) {
            super(new ImageView(viewGroup.getContext()));
            imgPhoto = (ImageView) itemView;
            imgPhoto.setLayoutParams(new ViewGroup.LayoutParams((int) AppUtils.convertDpToPixel(100, getContext())
                    , ((int) AppUtils.convertDpToPixel(100, getContext()))
            ));
            imgPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        @Override
        public void setData(Integer data) {
            super.setData(data);
            imgPhoto.setImageResource(data);
        }
    }


}
