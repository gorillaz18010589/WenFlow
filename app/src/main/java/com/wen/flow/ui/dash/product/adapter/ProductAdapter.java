package com.wen.flow.ui.dash.product.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.wen.flow.ui.dash.product.ProductInfo;
import com.wen.flow.ui.dash.product.Transport;
import com.wen.flow.ui.dash.product.viewholder.ProductDetailViewHolder;
import com.wen.flow.ui.dash.product.viewholder.ProductInfoViewHolder;
import com.wen.flow.ui.dash.product.ProductRecommended;
import com.wen.flow.ui.dash.product.viewholder.ProductRecommendedViewHolder;
import com.wen.flow.ui.dash.product.viewholder.TransportViewHolder;
import com.wen.flow.ui.dash.shop.model.Product;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

import java.util.List;

public class ProductAdapter extends RecyclerArrayAdapter<Object> {
    /*
    *產品資訊
    * 產品名稱,金額
    * */
    public static final int TYPE_PRODUCT_INFO = 0;

    /*
    * 運輸付費資訊
    * */
    public static final int TYPE_PRODUCT_TRANSPORT = 1;

    /*
    * 商品詳細資訊
    * */
    public static final int TYPE_PRODUCT_DETAIL = 2;

    /*
    *商品推薦
    * */
    public static final int TYPE_PRODUCT_RECOMMEND = 3;


    public ProductAdapter(Context context) {
        super(context);
    }

    public ProductAdapter(Context context, Object[] objects) {
        super(context, objects);
    }

    public ProductAdapter(Context context, List<Object> objects) {
        super(context, objects);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        com.yc.eastadapterlib.BaseViewHolder baseViewHolder;
        switch (viewType) {
            case TYPE_PRODUCT_INFO:
                return new ProductInfoViewHolder(parent);
            case TYPE_PRODUCT_TRANSPORT:
                return new TransportViewHolder(parent);
            case TYPE_PRODUCT_DETAIL:
                return new ProductDetailViewHolder(parent);
            case TYPE_PRODUCT_RECOMMEND:
                return new ProductRecommendedViewHolder(parent);
        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        int type = 0;
        if (getItem(position) instanceof Product) {
            return TYPE_PRODUCT_DETAIL;
        } else if (getItem(position) instanceof ProductInfo) {
            return TYPE_PRODUCT_INFO;
        } else if (getItem(position) instanceof Transport) {
            return TYPE_PRODUCT_TRANSPORT;
        } else if (getItem(position) instanceof ProductRecommended) {
            return TYPE_PRODUCT_RECOMMEND;
        }
        return type;
    }
}
