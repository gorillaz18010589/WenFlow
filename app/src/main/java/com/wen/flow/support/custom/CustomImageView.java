package com.wen.flow.support.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.wen.flow.R;

public class CustomImageView extends AppCompatImageView {
    private float cornerRadius;
    private String watermarkText;

    public CustomImageView(Context context) {
        super(context);
        init(null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomImageView);
            cornerRadius = ta.getDimension(R.styleable.CustomImageView_cornerRadius, 0);
            watermarkText = ta.getString(R.styleable.CustomImageView_watermarkText);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw corner radius
        if (cornerRadius > 0) {
            Path clipPath = new Path();
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            clipPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
            canvas.clipPath(clipPath);
        }

        // Draw watermark text
        if (watermarkText != null && !watermarkText.isEmpty()) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(watermarkText, getWidth() / 2f, getHeight() / 2f, paint);
        }
    }
}