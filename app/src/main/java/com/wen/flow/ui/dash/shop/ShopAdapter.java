package com.wen.flow.ui.dash.shop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.R;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.ui.dash.shop.model.PopularCategory;
import com.wen.flow.ui.dash.shop.model.Product;
import com.wen.flow.ui.dash.shop.model.Shop;

import java.util.List;
import java.util.zip.Inflater;

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //1.
    private List<BaseAdapterBean> baseAdapterBeanList;
    public final static int TITLE = 1001;//标题的viewType
    public final static int POPULAR_CATEGORY_VIEW_TYPE = 1002;//横向列表的viewType
    public final static int PRODUCT_VIEW_TYPE = 1003;//正常列表的viewType
    private Context mContent;
    private LayoutInflater mLayoutInflater;
    private OnProductItemClickListener onProductItemClickListener;


    public ShopAdapter(List<BaseAdapterBean> baseAdapterBeans) {
        this.baseAdapterBeanList = baseAdapterBeans;
    }

    //3.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContent == null) mContent = parent.getContext();
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(mContent);
        View view;

        switch (viewType) {
            case POPULAR_CATEGORY_VIEW_TYPE:
                view = mLayoutInflater.inflate(R.layout.item_shop_catrgory, parent, false);
                return new PopularCategoryViewHolder(view);
            case PRODUCT_VIEW_TYPE:
                view = mLayoutInflater.inflate(R.layout.item_commodity, parent, false);
                return new ProductViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseAdapterBean item = baseAdapterBeanList.get(position);
        KLog.v("BaseAdapterBean position:" + position +", BaseAdapterBean:" + item.getViewType());
        if (holder instanceof PopularCategoryViewHolder) {
            PopularCategoryViewHolder categoryHolder = (PopularCategoryViewHolder) holder;
            PopularCategory category = (PopularCategory) item;
            categoryHolder.img.setImageResource(category.getPopularCategoryImg());
            categoryHolder.tvName.setText(category.getPopularCategoryName());

        }

        if(holder instanceof ProductViewHolder){
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            Product product = (Product) item;
            productHolder.img.setImageResource(product.getPhoto());
            productHolder.tvName.setText(product.getName());
            productHolder.tvPrice.setText(String.valueOf(product.getPrice()));
            if (onProductItemClickListener != null) holder.itemView.setOnClickListener(v -> {
                onProductItemClickListener.onProductClick(product);
            });
        }

    }

    @Override
    public int getItemCount() {
        return baseAdapterBeanList.size();
    }


    //2.
    @Override
    public int getItemViewType(int position) {
        return baseAdapterBeanList.get(position).getViewType();
    }


    public interface  OnProductItemClickListener {
        void onProductClick(Product product);
    }

    public void setOnProductItemClickListener(OnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class PopularCategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName;

        public PopularCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName;
        private TextView tvPrice;
        private ConstraintLayout item;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            item = itemView.findViewById(R.id.item);
        }
    }

}


//public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    //1.
//    private List<Shop> shopList;
//    public final static int TITLE = 1001;//标题的viewType
//    public final static int POPULAR_CATEGORY_VIEW_TYPE = 1002;//横向列表的viewType
//    public final static int PRODUCT_VIEW_TYPE = 1003;//正常列表的viewType
//    private Context mContent;
//    private LayoutInflater mLayoutInflater;
//
//
//    public ShopAdapter(List<Shop> shopList) {
//        this.shopList = shopList;
//    }
//
//    //3.
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (mContent == null) mContent = parent.getContext();
//        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(mContent);
//        View view;
//
//        switch (viewType) {
//            case POPULAR_CATEGORY_VIEW_TYPE:
//                view = mLayoutInflater.inflate(R.layout.item_shop_catrgory, parent, false);
//                return new CategoryViewHolder(view);
//            case PRODUCT_VIEW_TYPE:
//                view = mLayoutInflater.inflate(R.layout.item_commodity, parent, false);
//                return new ProductViewHolder(view);
//        }
//
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof PopularCategoryViewHolder) {
//             List<PopularCategory> popularCategoryList = shopList.get(position).getPopularCategoryList();
//            ((PopularCategoryViewHolder) holder).img.setImageResource(popularCategoryList.get(position).getPopularCategoryImg());
//            ((PopularCategoryViewHolder) holder).tvName.setText(popularCategoryList.get(position).getPopularCategoryName());
//        }
//
//        if(holder instanceof PopularCategoryViewHolder){
//            List<Product> productList = shopList.get(position).getProductList();
//            ((ProductViewHolder) holder).img.setImageResource(productList.get(position).getPhoto());
//            ((ProductViewHolder) holder).tvName.setText(productList.get(position).getName());
//            ((ProductViewHolder) holder).tvPrice.setText(productList.get(position).getPrice());
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return shopList.size();
//    }
//
//
//    //2.
//    @Override
//    public int getItemViewType(int position) {
//        if (shopList.size() > 0) {
//            return shopList.get(position).getViewType();
//        }
//        return super.getItemViewType(position);
//    }
//
//    public class CategoryViewHolder extends RecyclerView.ViewHolder {
//        public CategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    public class PopularCategoryViewHolder extends RecyclerView.ViewHolder {
//        private ImageView img;
//        private TextView tvName;
//
//        public PopularCategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img = itemView.findViewById(R.id.img);
//            tvName = itemView.findViewById(R.id.tvName);
//        }
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder {
//        private ImageView img;
//        private TextView tvName;
//        private TextView tvPrice;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img = itemView.findViewById(R.id.img);
//            tvName = itemView.findViewById(R.id.tvName);
//            tvPrice = itemView.findViewById(R.id.tvPrice);
//        }
//    }
//}