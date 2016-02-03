package com.parteam.letspartya.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by miaomiao on 2015/12/20.
 */
public class FixedSizeLayout extends RelativeLayout {
    public FixedSizeLayout(Context context) {
        super(context);
    }

    public FixedSizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedSizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth * 9f / 16));
    }
}
