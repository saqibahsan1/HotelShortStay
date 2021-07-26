package com.shortstay.pk.utils

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class AspectRatioCardView constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(
    context!!, attrs, defStyleAttr
) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val ratio = 1.2f
        val ratioHeight = (measuredWidth * ratio).toInt()
        setMeasuredDimension(measuredWidth, ratioHeight)
        val lp = layoutParams
        lp.height = ratioHeight
        layoutParams = lp
    }
}