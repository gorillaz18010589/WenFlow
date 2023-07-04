package com.wen.flow.support.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wen.flow.R;

public class CustomConstraintLayout extends ConstraintLayout {
    private int startColor;
    private int middleColor;
    private int endColor;
    private float cornerRadiusTopLeft;
    private float cornerRadiusTopRight;
    private float cornerRadiusBottomLeft;
    private float cornerRadiusBottomRight;
    private float shadowRadius;
    private int shadowColor;
    private float shadowDx;
    private float shadowDy;

    public CustomConstraintLayout(Context context) {
        super(context);
        init(null);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomConstraintLayout);
            startColor = a.getColor(R.styleable.CustomConstraintLayout_startColor, Color.TRANSPARENT);
            middleColor = a.getColor(R.styleable.CustomConstraintLayout_middleColor, Color.TRANSPARENT);
            endColor = a.getColor(R.styleable.CustomConstraintLayout_endColor, Color.TRANSPARENT);
            cornerRadiusTopLeft = a.getDimension(R.styleable.CustomConstraintLayout_cornerRadiusTopLeft, 0);
            cornerRadiusTopRight = a.getDimension(R.styleable.CustomConstraintLayout_cornerRadiusTopRight, 0);
            cornerRadiusBottomLeft = a.getDimension(R.styleable.CustomConstraintLayout_cornerRadiusBottomLeft, 0);
            cornerRadiusBottomRight = a.getDimension(R.styleable.CustomConstraintLayout_cornerRadiusBottomRight, 0);
            shadowRadius = a.getDimension(R.styleable.CustomConstraintLayout_shadowRadius, 0);
            shadowColor = a.getColor(R.styleable.CustomConstraintLayout_shadowColor, Color.TRANSPARENT);
            shadowDx = a.getDimension(R.styleable.CustomConstraintLayout_shadowDx, 0);
            shadowDy = a.getDimension(R.styleable.CustomConstraintLayout_shadowDy, 0);
            a.recycle();
        }

        setBackground(createCustomDrawable());
    }

    private Drawable createCustomDrawable() {
        Drawable backgroundDrawable = createGradientDrawable(startColor, middleColor, endColor);
        Drawable shadowDrawable = createShadowDrawable(shadowRadius, shadowColor, shadowDx, shadowDy);

        Drawable[] layers = {backgroundDrawable, shadowDrawable};
        return createLayerDrawable(layers);
    }

    private Drawable createGradientDrawable(int startColor, int middleColor, int endColor) {
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{startColor, middleColor, endColor}
        );
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{
                cornerRadiusTopLeft,
                cornerRadiusTopLeft,
                cornerRadiusTopRight,
                cornerRadiusTopRight,
                cornerRadiusBottomRight,
                cornerRadiusBottomRight,
                cornerRadiusBottomLeft,
                cornerRadiusBottomLeft
        });
        return gradientDrawable;
    }

    private Drawable createShadowDrawable(float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        ShapeDrawable shadowDrawable = new ShapeDrawable(new RectShape());
        shadowDrawable.getPaint().setColor(Color.TRANSPARENT);
        shadowDrawable.getPaint().setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        shadowDrawable.setPadding(
                (int) Math.ceil(shadowRadius),
                (int) Math.ceil(shadowRadius),
                (int) Math.ceil(shadowRadius),
                (int) Math.ceil(shadowRadius)
        );
        return shadowDrawable;
    }

    private Drawable createLayerDrawable(Drawable... layers) {
        return new LayerDrawable(layers);
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        setBackground(createCustomDrawable());
    }

    public void setMiddleColor(int middleColor) {
        this.middleColor = middleColor;
        setBackground(createCustomDrawable());
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        setBackground(createCustomDrawable());
    }

    public void setCornerRadiusTopLeft(float cornerRadiusTopLeft) {
        this.cornerRadiusTopLeft = cornerRadiusTopLeft;
        setBackground(createCustomDrawable());
    }

    public void setCornerRadiusTopRight(float cornerRadiusTopRight) {
        this.cornerRadiusTopRight = cornerRadiusTopRight;
        setBackground(createCustomDrawable());
    }

    public void setCornerRadiusBottomLeft(float cornerRadiusBottomLeft) {
        this.cornerRadiusBottomLeft = cornerRadiusBottomLeft;
        setBackground(createCustomDrawable());
    }

    public void setCornerRadiusBottomRight(float cornerRadiusBottomRight) {
        this.cornerRadiusBottomRight = cornerRadiusBottomRight;
        setBackground(createCustomDrawable());
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
        setBackground(createCustomDrawable());
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        setBackground(createCustomDrawable());
    }

    public void setShadowDx(float shadowDx) {
        this.shadowDx = shadowDx;
        setBackground(createCustomDrawable());
    }

    public void setShadowDy(float shadowDy) {
        this.shadowDy = shadowDy;
        setBackground(createCustomDrawable());
    }
}
