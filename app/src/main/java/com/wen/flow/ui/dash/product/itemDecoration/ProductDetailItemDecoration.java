package com.wen.flow.ui.dash.product.itemDecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.MyApplication;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.support.util.DisplayUtil;
import com.wen.flow.ui.dash.product.adapter.ProductAdapter;
import com.wen.flow.ui.dash.product.viewholder.ProductDetailViewHolder;
import com.wen.flow.ui.dash.product.viewholder.ProductInfoViewHolder;
import com.wen.flow.ui.dash.product.viewholder.ProductRecommendedViewHolder;
import com.wen.flow.ui.dash.product.viewholder.TransportViewHolder;
import com.wen.flow.ui.dash.shop.ShopAdapter;

import org.w3c.dom.Text;

public class ProductDetailItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        if(viewHolder instanceof ProductInfoViewHolder){
            outRect.bottom = 20;
            outRect.left = 10;
            outRect.right = 10;
        }else if(viewHolder instanceof  TransportViewHolder){
            outRect.bottom = 20;
            outRect.left = 10;
            outRect.right = 10;
        }else if(viewHolder instanceof  ProductDetailViewHolder){
            outRect.bottom = 20;
            outRect.left = 10;
            outRect.right = 10;
        }else if(viewHolder instanceof  ProductRecommendedViewHolder){
//            outRect.bottom = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
//            outRect.right = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
//            productBottom = outRect.bottom;
//            productRight = outRect.right;
            outRect.bottom = 10;
            outRect.right = 10;
            outRect.top = 100;
            GridLayoutManager gridLayoutManager = (GridLayoutManager) (parent.getLayoutManager());
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int itemPosition = parent.getChildAdapterPosition(view);

            if (itemPosition % 2 == 0) {
                //PopularCategory2的倍數代表靠左邊,item靠左畫線
                outRect.left = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
                outRect.left = 10;
            } else {

            }
        }


    }


    private Paint paint = new Paint();
    private TextPaint textPaint = new TextPaint();

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        onDrawLine(c,parent);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        paint.setColor(Color.BLACK);
//        textPaint.setColor(Color.BLACK);
//        textPaint.setTextSize(36);
//        int count = ((ArrayAdapter) parent.getAdapter()).getViewTypeCount();
//        for(int i=0; i< count; i++){
//            View view =  parent.getChildAt(i);
//            if(parent.getChildViewHolder(view) instanceof  ProductRecommendedViewHolder){
//               c.drawText("測試",100,100,textPaint);
//            }
//        }
//        onDrawLine(c,parent);
    }


    //畫出四種不同item的線
    private void onDrawLine(@NonNull Canvas c, @NonNull RecyclerView parent) {
        //初始化線
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);


        int count = parent.getChildCount();//取得item的總數
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);//取得itemView


            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
            if (viewHolder instanceof ProductRecommendedViewHolder) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) (parent.getLayoutManager());
                GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                int itemPosition = parent.getChildAdapterPosition(view);
                int lastItemPosition = (parent.getChildAdapterPosition(view)-1);
//                c.drawRect(drawLineLeft4, drawLineTop4, drawLineRight4, drawLineBottom4, paint4);
                int drawYStart = (view.getTop() + view.getBottom() / 2);
                c.drawRect(parent.getPaddingLeft(),10+ view.getTop(),parent.getWidth(),view.getTop(),paint);
//                c.drawRect(parent.getPaddingLeft(),(10+ drawYStart) ,parent.getWidth(),drawYStart,paint);
//                c.drawRect(parent.getPaddingLeft(),drawYStart+ view.getTop(),parent.getWidth(),view.getTop() -20,paint);
//                c.drawRect(parent.getPaddingLeft(),10+ view.getTop(),parent.getWidth(),drawYEnd,paint);
                if (itemPosition % 2 == 0) {
//                    int drawLineLeft3 = DisplayUtil.dp2px(MyApplication.getInstance(), paddingLeft);
//                    int drawLineRight3 = DisplayUtil.dp2px(MyApplication.getInstance(), (paddingLeft + DisplayUtil.px2dp(MyApplication.getInstance(), productItemLeftBottom)));
//                    int drawLineTop3 = DisplayUtil.dp2px(MyApplication.getInstance(), viewTop);
//                    int drawLineBottom3 = DisplayUtil.dp2px(MyApplication.getInstance(), parentHeight);
//                    c.drawRect(drawLineLeft3, drawLineTop3, drawLineRight3, drawLineBottom3, paint3);
                } else {

                }
//                KLog.v(String.format(
//                        "\n position:%d, " +
//                                "viewLeft:%d, " +
//                                "viewTop:%d, " +
//                                "viewRight:%d ," +
//                                "viewBottom:%d ," +
//                                "popularCategoryBottom:%d ," +
//                                "productBottom:%d," +
//                                "productRight:%d ," +
//                                "productItemLeftBottom:%d," +
//                                "paddingLeft:%d ," +
//                                "paddingTop:%d ," +
//                                "paddingRight:%d ," +
//                                "paddingBottom:%d ," +
//                                "width:%d ," +
//                                "height:%d ," +
//                                "parentPaddingLeft:%d ," +
//                                "parentPaddingRight:%d ," +
//                                "parentPaddingTop:%d ," +
//                                "parentPaddingBottom:%d ," +
//                                "parentWidth:%d ," +
//                                "parentHeight:%d ,"
//                        ,
//                        position, viewLeft, viewTop, viewRight, viewBottom,
//                        DisplayUtil.px2dp(MyApplication.getInstance(), popularCategoryBottom),
//                        DisplayUtil.px2dp(MyApplication.getInstance(), productBottom),
//                        DisplayUtil.px2dp(MyApplication.getInstance(), productRight),
//                        DisplayUtil.px2dp(MyApplication.getInstance(), productItemLeftBottom),
//                        paddingLeft, paddingTop, paddingRight, paddingBottom,
//                        width, height,
//                        parentPaddingLeft, parentPaddingRight, parentPaddingTop, parentPaddingBottom, parentWidth, parentHeight

            }
        }
    }
}
