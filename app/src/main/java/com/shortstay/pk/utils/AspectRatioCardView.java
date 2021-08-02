package com.shortstay.pk.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;

public class AspectRatioCardView extends CardView {

    public AspectRatioCardView(Context context) {
        this(context, null);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float ratio = 1.2f;
        int ratioHeight = (int) (getMeasuredWidth() * ratio);
        setMeasuredDimension(getMeasuredWidth(), ratioHeight);
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = ratioHeight;
        setLayoutParams(lp);
    }
}
