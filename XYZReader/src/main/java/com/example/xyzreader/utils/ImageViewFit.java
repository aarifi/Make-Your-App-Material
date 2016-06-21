package com.example.xyzreader.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by AdonisArifi on 20.6.2016 - 2016 . xyzreader
 */

public class ImageViewFit extends ImageView {
    private float mAspectRatio = 1.5f;

    public ImageViewFit(Context context) {
        super(context);
    }

    public ImageViewFit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewFit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
    }
}
