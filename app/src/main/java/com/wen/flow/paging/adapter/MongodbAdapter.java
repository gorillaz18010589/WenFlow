package com.wen.flow.paging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wen.flow.R;
import com.wen.flow.network.response.Mongodb;

import java.util.List;

public class MongodbAdapter extends PagedListAdapter<Mongodb.BooksBean, MongodbAdapter.MongodbHolder> {
//    private List<Mongodb.BooksBean> mDatas;
    private PagedList<Mongodb.BooksBean> mDatas;
    private Context mContent;

    public MongodbAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContent = context;
    }

    @NonNull
    @Override
    public MongodbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commodity, parent, false);
        MongodbHolder mongodbHolder = new MongodbHolder(view);
        return mongodbHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MongodbHolder holder, int position) {
        Mongodb.BooksBean booksBean  = getItem(position);
        Glide.with(mContent)
                .load(booksBean.getImage())
                .into(holder.img);
        holder.tvName.setText(booksBean.getTitle());
        holder.tvPrice.setText(booksBean.getPrice());
    }



    public class MongodbHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName;
        private TextView tvPrice;

        public MongodbHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

    private static DiffUtil.ItemCallback<Mongodb.BooksBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Mongodb.BooksBean>() {
                @Override
                public boolean areItemsTheSame(Mongodb.BooksBean oldItem, Mongodb.BooksBean newItem) {
                    if (oldItem == null || newItem == null) return false;
                    return oldItem.getIsbn13().equals(newItem.getIsbn13()); // 使用唯一标识符比较项目
                }

                @Override
                public boolean areContentsTheSame(Mongodb.BooksBean oldItem, Mongodb.BooksBean newItem) {
                    if (oldItem == null || newItem == null) return false;
                    // 这里可以根据项目的内容比较来判断是否内容相同
                    return oldItem.equals(newItem); // 如果 Mongodb.BooksBean 类有适当的 equals 方法，可以直接调用
                }
            };

}
