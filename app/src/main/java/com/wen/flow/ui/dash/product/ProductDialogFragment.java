package com.wen.flow.ui.dash.product;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.wen.flow.R;
import com.wen.flow.common.ViewConstant;
import com.wen.flow.databinding.DialogFramgentProductBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.navigation.navigator.ReplaceFragment;
import com.wen.flow.support.base.BaseBindingDialogFragment;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.util.DisplayUtil;
import com.wen.flow.ui.dash.DashActivity;
import com.wen.flow.ui.dash.product.InterItemView.ItemProductBannerView;
import com.wen.flow.ui.dash.product.adapter.ProductAdapter;
import com.wen.flow.ui.dash.product.adapter.ProductBannerAdapter;
import com.wen.flow.ui.dash.product.adapter.ProductPhotoTypeAdapter;
import com.wen.flow.ui.dash.product.itemDecoration.ProductDetailItemDecoration;
import com.wen.flow.ui.dash.product.viewholder.ProductDetailViewHolder;
import com.wen.flow.ui.dash.product.viewholder.ProductInfoViewHolder;
import com.wen.flow.ui.dash.product.viewholder.ProductRecommendedViewHolder;
import com.wen.flow.ui.dash.product.viewholder.TransportViewHolder;
import com.wen.flow.ui.dash.shop.GridSpacingItemDecoration;
import com.wen.flow.ui.dash.shop.model.Product;
import com.wen.flow.ui.test.data.AdData;
import com.wen.flow.ui.test.data.AppUtils;
import com.wen.flow.ui.test.data.DataProvider;
import com.wen.flow.ui.test.data.PersonData;
import com.wen.flow.ui.test.header.BannerAdapter;
import com.wen.flow.ui.test.header.HeaderFooterActivity;
import com.wen.flow.ui.test.header.NarrowImageAdapter;
import com.wen.flow.ui.test.refresh.PersonAdapter;
import com.yc.cn.ycbannerlib.LibUtils;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.hintview.ColorPointHintView;
import com.yc.cn.ycbannerlib.banner.hintview.TextHintView;

import org.yczbj.ycrefreshviewlib.inter.InterItemView;
import org.yczbj.ycrefreshviewlib.inter.OnItemChildClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnNoMoreListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;

import java.util.ArrayList;
import java.util.List;

import static com.wen.flow.common.FragmentConstant.FROM_SHOP_TO_PRODUCT_BUNDLE_PARCELABLE;

@ReplaceFragment
public class ProductDialogFragment extends BaseFragment<DialogFramgentProductBinding> {
    private Product product;
    private PersonAdapter adapter;
    private ProductAdapter productAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            product = bundle.getParcelable(FROM_SHOP_TO_PRODUCT_BUNDLE_PARCELABLE);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_framgent_product;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
//        initDefaultData();
//        initProductData();
        initProductDataByHeader();
    }

    private void initProductDataByHeader(){
        productAdapter = new ProductAdapter(requireActivity());
//        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(15,10,true));
//        binding.recyclerView.addItemDecoration(new RecycleViewItemLine(requireActivity(),
//                LinearLayoutManager.HORIZONTAL,DisplayUtil.dp2px(requireActivity(),10),
//                getResources().getColor(R.color.blue)));
//        SpaceViewItemLine spaceViewItemLine =  new SpaceViewItemLine(DisplayUtil.px2dp(requireActivity(),100));
//        binding.recyclerView.addItemDecoration(spaceViewItemLine);
        binding.recyclerView.addItemDecoration(new ProductDetailItemDecoration());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 10);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = productAdapter.getItemViewType(position);
                long itemId = productAdapter.getItemId(position);
                KLog.v("position:" + position + "itemId:" + itemId + ", viewType:" + viewType);
//                productAdapter.getAllData()
//                InterItemView interItemView = productAdapter.getHeader(position);
                if (viewType == ProductAdapter.TYPE_PRODUCT_RECOMMEND) {
                    return 5;
                } else {
                    return 10;
                }
            }
        });
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(productAdapter);

        productAdapter.removeAllFooter();
        productAdapter.removeAllHeader();
        productAdapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                List<AdData> adData = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    adData.add(new AdData("測試" + i, R.drawable.photp_nike_low));
                }
                BannerView bannerView = new BannerView(requireActivity());
                TextRectangleHintView textRectangleHintView = new TextRectangleHintView(requireActivity());
                textRectangleHintView.setCurrent(1);
                textRectangleHintView.initView(1, 9);
                bannerView.setHintView(textRectangleHintView);
                bannerView.setPlayDelay(2 * 1000);
                bannerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
                bannerView.setAdapter(new ProductBannerAdapter(requireActivity(), adData));
                return bannerView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
//        productAdapter.addHeader(new ItemProductBannerView());
        productAdapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()) {
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(120,
                                requireActivity())));
                final ProductPhotoTypeAdapter adapter;
                recyclerView.setAdapter(adapter = new ProductPhotoTypeAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
                        AppUtils.convertDpToPixel(8, parent.getContext())));

                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(DataProvider.getShoeTypeColor(0));
                            }
                        }, 1000);
                    }
                });
                adapter.addAll(DataProvider.getShoeTypeColor(0));
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
        productAdapter.addAll(getProductData(product));
        productAdapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recommend,null);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
                YCRefreshView rvProductRecommend = headerView.findViewById(R.id.rvProductRecommend);

                final ProductPhotoTypeAdapter adapter;
                rvProductRecommend.setAdapter(adapter = new ProductPhotoTypeAdapter(getContext()));
                rvProductRecommend.setLayoutManager(new GridLayoutManager(getContext(),
                      2));

                int left = 100;
                int right = 100;
                int bottom = 100;
                Paint bottomPaint = new Paint();
                Paint rightPaint = new Paint();
                Paint leftPaint = new Paint();
                int color = ContextCompat.getColor(context, R.color.color_80878282);

                rvProductRecommend.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        super.onDraw(c, parent, state);
                        bottomPaint.setColor(Color.GRAY);
                        rightPaint.setColor(Color.BLUE);
                        leftPaint.setColor(Color.YELLOW);

                        bottomPaint.setColor(color);
                        rightPaint.setColor(color);
                        leftPaint.setColor(color);
                        int count = parent.getChildCount();
                        for (int i = 0; i < count; i++) {
                            View view = parent.getChildAt(i);
                            c.drawRect(parent.getPaddingLeft(), view.getBottom() + bottom, parent.getWidth(), view.getBottom(), bottomPaint);
                            c.drawRect(view.getRight(), view.getTop(), view.getRight() + right, view.getBottom(), rightPaint);
                            int itemPosition = parent.getChildAdapterPosition(view);
                            if (itemPosition % 2 == 0) {
                                c.drawRect(parent.getPaddingLeft(), view.getTop(), view.getLeft(), view.getBottom(), leftPaint);
                            }
                        }
                    }

                    @Override
                    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.right = right;
                        outRect.bottom = bottom;
                        int itemPosition = parent.getChildPosition(view);
                        if (itemPosition % 2 == 0) {
                            outRect.left = left;
                        }
                    }
                });
//                SpaceViewItemLine spaceViewItemLine = new SpaceViewItemLine(10);
//                spaceViewItemLine.setPaddingEdgeSide(true);
//                spaceViewItemLine.setPaddingHeaderFooter(true);
//                rvProductRecommend.addItemDecoration(spaceViewItemLine);

//                rvProductRecommend.addItemDecoration(new SpaceViewItemLine((int)
//                        AppUtils.convertDpToPixel(8, getContext())));

//                rvProductRecommend.addItemDecoration(new DividerViewItemLine(Color.GRAY,
//                        DisplayUtil.dp2px(requireActivity(),10),
//                        DisplayUtil.dp2px(requireActivity(),10),
//                        DisplayUtil.dp2px(requireActivity(),10)));

//                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                            }
//                        }, 1000);
//                    }
//                });
                adapter.addAll(DataProvider.getShoeTypeColor(0));

//                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
//                YCRefreshView rvProductRecommend = headerView.findViewById(R.id.rvProductRecommend);
//
//                final ProductPhotoTypeAdapter adapter;
//                rvProductRecommend.setAdapter(adapter = new ProductPhotoTypeAdapter(getContext()));
//                rvProductRecommend.setLayoutManager(new LinearLayoutManager(getContext(),
//                        LinearLayoutManager.HORIZONTAL, false));
//
//                rvProductRecommend.addItemDecoration(new SpaceViewItemLine((int)
//                        AppUtils.convertDpToPixel(8, getContext())));
//
//                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                            }
//                        }, 1000);
//                    }
//                });
//                adapter.addAll(DataProvider.getShoeTypeColor(0));
            }
        });

        KLog.v("size:" + productAdapter.getAllData().size() + ", header:" + productAdapter.getHeaderCount());
    }

    private ArrayList<Object> getProductData(Product product){
        ArrayList<Object> datas = new ArrayList<>();
        datas.add(new ProductInfo("Nike Air Force",3000));
        datas.add(new Transport("付款方式",90));
        datas.add(product);
        for(int i=0; i<10; i++){ ;
            datas.add(new ProductRecommended(i,"New Banlance", R.drawable.photp_nike_low, 2800));
        }
        KLog.json(new Gson().toJson(datas));
        return datas;
    }

    private void initProductData() {
        productAdapter = new ProductAdapter(requireActivity());
//        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(15,10,true));
//        binding.recyclerView.addItemDecoration(new RecycleViewItemLine(requireActivity(),
//                LinearLayoutManager.HORIZONTAL,DisplayUtil.dp2px(requireActivity(),10),
//                getResources().getColor(R.color.blue)));
//        SpaceViewItemLine spaceViewItemLine =  new SpaceViewItemLine(DisplayUtil.px2dp(requireActivity(),100));
//        binding.recyclerView.addItemDecoration(spaceViewItemLine);
        binding.recyclerView.addItemDecoration(new ProductDetailItemDecoration());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 10);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = productAdapter.getItemViewType(position);
                long itemId = productAdapter.getItemId(position);
                KLog.v("position:" + position + "itemId:" + itemId + ", viewType:" + viewType);
//                productAdapter.getAllData()
//                InterItemView interItemView = productAdapter.getHeader(position);
                if (viewType == ProductAdapter.TYPE_PRODUCT_RECOMMEND) {
                    return 5;
                } else {
                    return 10;
                }
            }
        });
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(productAdapter);

        productAdapter.removeAllFooter();
        productAdapter.removeAllHeader();
        productAdapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                List<AdData> adData = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    adData.add(new AdData("測試" + i, R.drawable.photp_nike_low));
                }
                BannerView bannerView = new BannerView(requireActivity());
                TextRectangleHintView textRectangleHintView = new TextRectangleHintView(requireActivity());
                textRectangleHintView.setCurrent(1);
                textRectangleHintView.initView(1, 9);
                bannerView.setHintView(textRectangleHintView);
                bannerView.setPlayDelay(2 * 1000);
                bannerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
                bannerView.setAdapter(new ProductBannerAdapter(requireActivity(), adData));
                return bannerView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
//        productAdapter.addHeader(new ItemProductBannerView());
        productAdapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()) {
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(120,
                                requireActivity())));
                final ProductPhotoTypeAdapter adapter;
                recyclerView.setAdapter(adapter = new ProductPhotoTypeAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
                        AppUtils.convertDpToPixel(8, parent.getContext())));

                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(DataProvider.getShoeTypeColor(0));
                            }
                        }, 1000);
                    }
                });
                adapter.addAll(DataProvider.getShoeTypeColor(0));
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
        productAdapter.addAll(getProductData(product));

        KLog.v("size:" + productAdapter.getAllData().size() + ", header:" + productAdapter.getHeaderCount());
    }

//    private void initProductData() {
//
//        adapter = new PersonAdapter(requireActivity());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),10);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 10;
//            }
//        });
//        binding.recyclerView.setLayoutManager(gridLayoutManager);
//        binding.recyclerView.setAdapter(adapter);
//
//        adapter.removeAllFooter();
//        adapter.removeAllHeader();
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                List<AdData> adData = new ArrayList<>();
//                for(int i=0; i< 9; i++){
//                    adData.add(new AdData("測試" + i ,R.drawable.photp_nike_low));
//                }
//                BannerView bannerView = new BannerView(requireActivity());
//                TextRectangleHintView textRectangleHintView = new TextRectangleHintView(requireActivity());
//                textRectangleHintView.setCurrent(1);
//                textRectangleHintView.initView(1,9);
//                bannerView.setHintView(textRectangleHintView);
//                bannerView.setPlayDelay(2 *1000);
//                bannerView.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
//                bannerView.setAdapter(new ProductBannerAdapter(requireActivity(),adData));
//                return bannerView;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
//                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
//                    @SuppressLint("ClickableViewAccessibility")
//                    @Override
//                    public boolean onTouchEvent(MotionEvent event) {
//                        super.onTouchEvent(event);
//                        return true;
//                    }
//                };
//                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(120,
//                                requireActivity())));
//                final ProductPhotoTypeAdapter adapter;
//                recyclerView.setAdapter(adapter = new ProductPhotoTypeAdapter(parent.getContext()));
//                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
//                        LinearLayoutManager.HORIZONTAL,false));
//
//                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
//                        AppUtils.convertDpToPixel(8,parent.getContext())));
//
//                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                            }
//                        },1000);
//                    }
//                });
//                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                return recyclerView;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                //这里的处理别忘了
//                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
//            }
//        });
//
//        adapter.addAll(DataProvider.getPersonList(0));
////        adapter.addAll(DataProvider.getPersonList(0));
//    }


    private void initDefaultData() {
        adapter = new PersonAdapter(requireActivity());
        binding.recyclerView.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_secondary),
                LibUtils.dip2px(requireActivity(),0.5f),
                LibUtils.dip2px(requireActivity(),72),0);
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(true);
        binding.recyclerView.addItemDecoration(itemDecoration);

        final RecycleViewItemLine line = new RecycleViewItemLine(requireActivity(),
                LinearLayout.HORIZONTAL, DisplayUtil.dp2px(requireActivity(),10), getResources().getColor(R.color.color4DE9E9EA));
        binding.recyclerView.addItemDecoration(line);

        binding.recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(DataProvider.getPersonList(0));
                    }
                },1500);
            }
        });
        //recyclerView.setRefreshing(true);
        binding.recyclerView.setRefreshingColorResources(R.color.color_e7d9f1);
        initTestHeader();
        adapter.addAll(DataProvider.getPersonList(0));
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                switch (view.getId()){
                    case R.id.iv_news_image:
//                        Toast.makeText(HeaderFooterActivity.this,
//                                "点击图片了",Toast.LENGTH_SHORT).show();
                        showToast("點擊圖片了");
                        break;
                    case R.id.tv_title:
//                        Toast.makeText(HeaderFooterActivity.this,
//                                "点击标题",Toast.LENGTH_SHORT).show();
                        showToast("點擊標題了");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initListeners() {

    }

    private void initHeader() {
        adapter.removeAllFooter();
        adapter.removeAllHeader();

        InterItemView interItemView = new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                List<AdData> adData = new ArrayList<>();
                for(int i=0; i< 9; i++){
                    adData.add(new AdData("測試" + i ,R.drawable.login_ads));
                }
                BannerView header = new BannerView(getContext());
                header.setHintView(new ColorPointHintView(getContext(),
                        Color.YELLOW, Color.GRAY));
                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(
                        8, requireActivity()));
                header.setPlayDelay(2000);
                header.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
                header.setAdapter(new BannerAdapter(getContext(), adData));
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        };
        adapter.addHeader(interItemView);
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(requireActivity())
                        .inflate(R.layout.header_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvTitle = headerView.findViewById(R.id.tvTitle);
            }
        });
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
                final NarrowImageAdapter adapter;
                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
                        LinearLayoutManager.HORIZONTAL,false));

                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
                        AppUtils.convertDpToPixel(8,parent.getContext())));

                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(DataProvider.getNarrowImage(0));
                            }
                        },1000);
                    }
                });
                adapter.addAll(DataProvider.getNarrowImage(0));
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(requireActivity())
                        .inflate(R.layout.footer_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(requireActivity());
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(56,requireActivity())));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setText("这个是底部布局");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    private void initTestHeader() {
        adapter.removeAllFooter();
        adapter.removeAllHeader();

        InterItemView interItemView = new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                List<AdData> adData = new ArrayList<>();
                for(int i=0; i< 9; i++){
                    adData.add(new AdData("測試" + i ,R.drawable.photp_nike_low));
                }
                BannerView header = new BannerView(getContext());
//                header.setHintView(new ColorPointHintView(getContext(),
//                        Color.YELLOW, Color.GRAY));
//
//                TextHintView textHintView = new TextHintView(requireActivity());
//                textHintView.initView(9,2);
//                textHintView.setCurrent(1);
//                header.setHintView(textHintView);

                TextRectangleHintView textHintView = new TextRectangleHintView(requireActivity());
                textHintView.initView(9,2);
                textHintView.setCurrent(1);

                header.setHintView(textHintView);
                header.setHintPadding(30, 0, 20, (int) AppUtils.convertDpToPixel(
                        8, requireActivity()));
                header.setPlayDelay(2000);
                header.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
                header.setAdapter(new BannerAdapter(getContext(), adData));
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        };
        adapter.addHeader(interItemView);
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(120,
                                requireActivity())));
                final NarrowImageAdapter adapter;
                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
                        LinearLayoutManager.HORIZONTAL,false));

                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
                        AppUtils.convertDpToPixel(8,parent.getContext())));

                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(DataProvider.getShoeTypeColor(0));
                            }
                        },1000);
                    }
                });
                adapter.addAll(DataProvider.getShoeTypeColor(0));
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(requireActivity())
                        .inflate(R.layout.item_product_information, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {
//                TextView tvTitle = headerView.findViewById(R.id.tvTitle);
            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(requireActivity())
                        .inflate(R.layout.footer_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(requireActivity());
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(56,requireActivity())));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setText("这个是底部布局");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


//        private void initTestHeader() {
//        adapter.removeAllFooter();
//        adapter.removeAllHeader();
//
//        InterItemView interItemView = new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                List<AdData> adData = new ArrayList<>();
//                for(int i=0; i< 9; i++){
//                    adData.add(new AdData("測試" + i ,R.drawable.photp_nike_low));
//                }
//                BannerView header = new BannerView(getContext());
//                header.setHintView(new ColorPointHintView(getContext(),
//                        Color.YELLOW, Color.GRAY));
//                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(
//                        8, requireActivity()));
//                header.setPlayDelay(2000);
//                header.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
//                header.setAdapter(new BannerAdapter(getContext(), adData));
//                return header;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        };
//        adapter.addHeader(interItemView);
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
//                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
//                    @SuppressLint("ClickableViewAccessibility")
//                    @Override
//                    public boolean onTouchEvent(MotionEvent event) {
//                        super.onTouchEvent(event);
//                        return true;
//                    }
//                };
//                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(120,
//                                requireActivity())));
//                final NarrowImageAdapter adapter;
//                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
//                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
//                        LinearLayoutManager.HORIZONTAL,false));
//
//                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
//                        AppUtils.convertDpToPixel(8,parent.getContext())));
//
//                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                            }
//                        },1000);
//                    }
//                });
//                adapter.addAll(DataProvider.getShoeTypeColor(0));
//                return recyclerView;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                //这里的处理别忘了
//                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
//            }
//        });
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View inflate = LayoutInflater.from(requireActivity())
//                        .inflate(R.layout.item_product_information, null);
//                return inflate;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
////                TextView tvTitle = headerView.findViewById(R.id.tvTitle);
//            }
//        });
//        adapter.addFooter(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View inflate = LayoutInflater.from(requireActivity())
//                        .inflate(R.layout.footer_view, null);
//                return inflate;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//        adapter.addFooter(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                TextView tv = new TextView(requireActivity());
//                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(56,requireActivity())));
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//                tv.setText("这个是底部布局");
//                return tv;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//    }

}

//public class ProductDialogFragment extends BaseFragment<DialogFramgentProductBinding> {
//    private Product product;
//    private PersonAdapter adapter;
//
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            product = bundle.getParcelable(FROM_SHOP_TO_PRODUCT_BUNDLE_PARCELABLE);
//        }
//    }
//
//    @Override
//    protected int getLayoutResourceId() {
//        return R.layout.dialog_framgent_product;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//
//    }
//
//    @Override
//    protected void initData() {
//        adapter = new PersonAdapter(requireActivity());
//        binding.recyclerView.setAdapter(adapter);
//
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
//        binding.recyclerView.setLayoutManager(linearLayoutManager);
//        DividerViewItemLine itemDecoration = new DividerViewItemLine(
//                this.getResources().getColor(R.color.color_secondary),
//                LibUtils.dip2px(requireActivity(),0.5f),
//                LibUtils.dip2px(requireActivity(),72),0);
//        itemDecoration.setDrawLastItem(true);
//        itemDecoration.setDrawHeaderFooter(true);
//        binding.recyclerView.addItemDecoration(itemDecoration);
//
//        final RecycleViewItemLine line = new RecycleViewItemLine(requireActivity(),
//                LinearLayout.HORIZONTAL, 1, getResources().getColor(R.color.red));
//        binding.recyclerView.addItemDecoration(line);
//
//        binding.recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.clear();
//                        adapter.addAll(DataProvider.getPersonList(0));
//                    }
//                },1500);
//            }
//        });
//        //recyclerView.setRefreshing(true);
//        binding.recyclerView.setRefreshingColorResources(R.color.color_e7d9f1);
//        initHeader();
//        adapter.addAll(DataProvider.getPersonList(0));
//        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(View view, int position) {
//                switch (view.getId()){
//                    case R.id.iv_news_image:
////                        Toast.makeText(HeaderFooterActivity.this,
////                                "点击图片了",Toast.LENGTH_SHORT).show();
//                        showToast("點擊圖片了");
//                        break;
//                    case R.id.tv_title:
////                        Toast.makeText(HeaderFooterActivity.this,
////                                "点击标题",Toast.LENGTH_SHORT).show();
//                        showToast("點擊標題了");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void initListeners() {
//
//    }
//
//    private void initHeader() {
//        adapter.removeAllFooter();
//        adapter.removeAllHeader();
//
//        InterItemView interItemView = new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                List<AdData> adData = new ArrayList<>();
//                for(int i=0; i< 9; i++){
//                    adData.add(new AdData("測試" + i ,R.drawable.login_ads));
//                }
//                BannerView header = new BannerView(getContext());
//                header.setHintView(new ColorPointHintView(getContext(),
//                        Color.YELLOW, Color.GRAY));
//                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(
//                        8, requireActivity()));
//                header.setPlayDelay(2000);
//                header.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
//                header.setAdapter(new BannerAdapter(getContext(), adData));
//                return header;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        };
//        adapter.addHeader(interItemView);
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View inflate = LayoutInflater.from(requireActivity())
//                        .inflate(R.layout.header_view, null);
//                return inflate;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                TextView tvTitle = headerView.findViewById(R.id.tvTitle);
//            }
//        });
//        adapter.addHeader(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
//                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
//                    @SuppressLint("ClickableViewAccessibility")
//                    @Override
//                    public boolean onTouchEvent(MotionEvent event) {
//                        super.onTouchEvent(event);
//                        return true;
//                    }
//                };
//                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(200, requireActivity())));
//                final NarrowImageAdapter adapter;
//                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
//                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
//                        LinearLayoutManager.HORIZONTAL,false));
//
//                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
//                        AppUtils.convertDpToPixel(8,parent.getContext())));
//
//                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.addAll(DataProvider.getNarrowImage(0));
//                            }
//                        },1000);
//                    }
//                });
//                adapter.addAll(DataProvider.getNarrowImage(0));
//                return recyclerView;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                //这里的处理别忘了
//                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
//            }
//        });
//        adapter.addFooter(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View inflate = LayoutInflater.from(requireActivity())
//                        .inflate(R.layout.footer_view, null);
//                return inflate;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//        adapter.addFooter(new InterItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                TextView tv = new TextView(requireActivity());
//                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        (int) AppUtils.convertDpToPixel(56,requireActivity())));
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//                tv.setText("这个是底部布局");
//                return tv;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//
//            }
//        });
//    }
//
//}