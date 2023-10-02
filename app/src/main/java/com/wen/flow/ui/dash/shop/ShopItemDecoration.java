package com.wen.flow.ui.dash.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.support.util.DisplayUtil;

public class ShopItemDecoration extends RecyclerView.ItemDecoration {
    private int popularCategoryBottom = 0;
    private int productBottom = 0;
    private int productRight = 0;
    private int productItemLeftBottom = 0;

    private int categoryDrawLineLeft;
    private int categoryDrawLineRight;
    private int categoryDrawLineBottom;
    private int categoryDrawLineTop;

    //第二呼叫getItemOffsets全部呼叫玩,才被呼叫
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        onDrawLine(c, parent);

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent) {
        super.onDrawOver(c, parent);
//        drawOverBitmapOnImg(c, parent);
    }

    //第三呼叫跟onDraw,輪流被呼叫
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        KLog.v();
    }

    //只有初次初始化RecyclerView會呼叫
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        setMargin(outRect, view, parent, state);
    }

    private void setMargin(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        if (viewHolder instanceof ShopAdapter.PopularCategoryViewHolder) {
            outRect.bottom = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
            popularCategoryBottom = outRect.bottom;
            KLog.v("PopularCategoryViewHolder:" + parent.getChildAdapterPosition(view));
        }
        if (viewHolder instanceof ShopAdapter.ProductViewHolder) {
            outRect.bottom = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
            outRect.right = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
            productBottom = outRect.bottom;
            productRight = outRect.right;
            GridLayoutManager gridLayoutManager = (GridLayoutManager) (parent.getLayoutManager());
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanCount = gridLayoutManager.getSpanCount();//這個資料的總item數量
            int spanSize = spanSizeLookup.getSpanSize(parent.getChildAdapterPosition(view));
            int itemPosition = parent.getChildAdapterPosition(view);

            if (itemPosition % 2 == 0) {
                //PopularCategory2的倍數代表靠左邊,item靠左畫線
                outRect.left = DisplayUtil.dp2px(MyApplication.getInstance(), 10);
                productItemLeftBottom = outRect.left;
            } else {

            }
            KLog.v("ProductViewHolder:" + parent.getChildAdapterPosition(view) + ", spanCount:" + spanCount + ", spanSize:" + spanSize);
        }

        int bottom = outRect.bottom;
        int top = outRect.top;
        int left = outRect.left;
        int right = outRect.right;
        int childAdapterPosition = parent.getChildAdapterPosition(view);//回傳當前看得到的item位置
        int viedWidth = view.getWidth();
        int viewHeight = view.getHeight();
        int viewLeft = view.getLeft();
        int viewPaddingLeft = view.getPaddingLeft();

        KLog.v(String.format(
                " top:%s, " +
                        "bottom:%x, " +
                        "left:%x, " +
                        "right:%x ," +
                        "childAdapterPosition:%x," +
                        "viedWidth:%x," +
                        "viewHeight:%x," +
                        "viewLeft:%x," +
                        "viewPaddingLeft:%x," +
                        "state:%s"
                ,
                top, bottom, left, right, childAdapterPosition, viedWidth, viewHeight, viewLeft, viewPaddingLeft, state));
    }

    //新增bitmapIconOnImg
    private void drawOverBitmapOnImg(@NonNull Canvas c, @NonNull RecyclerView parent) {
        int count = parent.getChildCount();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.icon_tag_sale);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.RED);
        int marginLeft = DisplayUtil.dp2px(MyApplication.getInstance(), 5);
        int marginTop = DisplayUtil.dp2px(MyApplication.getInstance(), 20);
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
            if (viewHolder instanceof ShopAdapter.ProductViewHolder) {
                c.drawBitmap(bitmap, view.getLeft(), view.getTop(), paint);
                c.drawText(String.valueOf(i), view.getLeft() + marginLeft, view.getTop() + marginTop, textPaint);
            }
        }
    }

    //畫出四種不同item的線
    private void onDrawLine(@NonNull Canvas c, @NonNull RecyclerView parent) {
        //初始化線
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

        //ProductItem的右邊線
        Paint paintProduct = new Paint();
        paintProduct.setColor(Color.GREEN);

        //ProductItem的底部線
        Paint paint4 = new Paint();
        paint4.setColor(Color.BLACK);

        //ProductItem的左邊線ｓ
        Paint paint3 = new Paint();
        paint3.setColor(Color.GRAY);

        int count = parent.getChildCount();//取得item的總數
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);//取得itemView

            //取得itemView/recyclerView的dp資料
            int position = parent.getChildAdapterPosition(view);//item位置
            int viewLeft = DisplayUtil.px2dp(MyApplication.getInstance(), view.getLeft());
            int viewTop = DisplayUtil.px2dp(MyApplication.getInstance(), view.getTop());
            int viewRight = DisplayUtil.px2dp(MyApplication.getInstance(), view.getRight());
            int viewBottom = DisplayUtil.px2dp(MyApplication.getInstance(), view.getBottom());
            int paddingLeft = DisplayUtil.px2dp(MyApplication.getInstance(), view.getPaddingLeft());
            int paddingTop = DisplayUtil.px2dp(MyApplication.getInstance(), view.getPaddingTop());
            int paddingRight = DisplayUtil.px2dp(MyApplication.getInstance(), view.getPaddingRight());
            int paddingBottom = DisplayUtil.px2dp(MyApplication.getInstance(), view.getPaddingBottom());
            int width = DisplayUtil.px2dp(MyApplication.getInstance(), view.getWidth());
            int height = DisplayUtil.px2dp(MyApplication.getInstance(), view.getHeight());
            int parentPaddingLeft = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getPaddingLeft());
            int parentPaddingRight = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getPaddingRight());
            int parentPaddingTop = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getPaddingTop());
            int parentPaddingBottom = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getPaddingBottom());
            int parentWidth = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getWidth());
            int parentHeight = DisplayUtil.px2dp(MyApplication.getInstance(), parent.getHeight());
            categoryDrawLineLeft = DisplayUtil.dp2px(MyApplication.getInstance(), paddingLeft);//水平線起始從item的最左邊
            categoryDrawLineTop = DisplayUtil.dp2px(MyApplication.getInstance(), viewBottom);//垂直線從item的底部
            categoryDrawLineRight = DisplayUtil.dp2px(MyApplication.getInstance(), (parentWidth - parentPaddingRight));//畫到整個水平線的最右邊
            categoryDrawLineBottom = DisplayUtil.dp2px(MyApplication.getInstance(), viewBottom + DisplayUtil.px2dp(MyApplication.getInstance(), popularCategoryBottom));//畫到item新加的margin為止
            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
            if (viewHolder instanceof ShopAdapter.PopularCategoryViewHolder) {
                //PopularCategoryViewHolder在底部畫一條黃線
                c.drawRect(categoryDrawLineLeft, categoryDrawLineTop, categoryDrawLineRight, categoryDrawLineBottom, paint);
            }
            if (viewHolder instanceof ShopAdapter.ProductViewHolder) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) (parent.getLayoutManager());
                GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                int spanCount = gridLayoutManager.getSpanCount();//這個資料的總item數量
                int spanSize = spanSizeLookup.getSpanSize(parent.getChildAdapterPosition(view));
                int itemPosition = parent.getChildAdapterPosition(view);
                int drawLineLeft = DisplayUtil.dp2px(MyApplication.getInstance(), viewRight);
                int drawLineRight = DisplayUtil.dp2px(MyApplication.getInstance(), (viewRight + DisplayUtil.px2dp(MyApplication.getInstance(), productRight)));
                int drawLineTop = DisplayUtil.dp2px(MyApplication.getInstance(), (viewTop));
                int drawLineBottom = DisplayUtil.dp2px(MyApplication.getInstance(), parentHeight);
                c.drawRect(drawLineLeft, drawLineTop, drawLineRight, drawLineBottom, paintProduct);
                int drawLineLeft4 = DisplayUtil.dp2px(MyApplication.getInstance(), viewLeft);
                int drawLineRight4 = DisplayUtil.dp2px(MyApplication.getInstance(), (viewRight));
                int drawLineTop4 = DisplayUtil.dp2px(MyApplication.getInstance(), (viewBottom));
                int drawLineBottom4 = DisplayUtil.dp2px(MyApplication.getInstance(), (viewBottom + DisplayUtil.px2dp(MyApplication.getInstance(), popularCategoryBottom)));
                c.drawRect(drawLineLeft4, drawLineTop4, drawLineRight4, drawLineBottom4, paint4);
                if (itemPosition % 2 == 0) {
                    int drawLineLeft3 = DisplayUtil.dp2px(MyApplication.getInstance(), paddingLeft);
                    int drawLineRight3 = DisplayUtil.dp2px(MyApplication.getInstance(), (paddingLeft + DisplayUtil.px2dp(MyApplication.getInstance(), productItemLeftBottom)));
                    int drawLineTop3 = DisplayUtil.dp2px(MyApplication.getInstance(), viewTop);
                    int drawLineBottom3 = DisplayUtil.dp2px(MyApplication.getInstance(), parentHeight);
                    c.drawRect(drawLineLeft3, drawLineTop3, drawLineRight3, drawLineBottom3, paint3);
                } else {

                }
                KLog.v(String.format(
                        "\n position:%d, " +
                                "viewLeft:%d, " +
                                "viewTop:%d, " +
                                "viewRight:%d ," +
                                "viewBottom:%d ," +
                                "popularCategoryBottom:%d ," +
                                "productBottom:%d," +
                                "productRight:%d ," +
                                "productItemLeftBottom:%d," +
                                "paddingLeft:%d ," +
                                "paddingTop:%d ," +
                                "paddingRight:%d ," +
                                "paddingBottom:%d ," +
                                "width:%d ," +
                                "height:%d ," +
                                "parentPaddingLeft:%d ," +
                                "parentPaddingRight:%d ," +
                                "parentPaddingTop:%d ," +
                                "parentPaddingBottom:%d ," +
                                "parentWidth:%d ," +
                                "parentHeight:%d ,"
                        ,
                        position, viewLeft, viewTop, viewRight, viewBottom,
                        DisplayUtil.px2dp(MyApplication.getInstance(), popularCategoryBottom),
                        DisplayUtil.px2dp(MyApplication.getInstance(), productBottom),
                        DisplayUtil.px2dp(MyApplication.getInstance(), productRight),
                        DisplayUtil.px2dp(MyApplication.getInstance(), productItemLeftBottom),
                        paddingLeft, paddingTop, paddingRight, paddingBottom,
                        width, height,
                        parentPaddingLeft, parentPaddingRight, parentPaddingTop, parentPaddingBottom, parentWidth, parentHeight
                ));
            }
        }
    }
}

