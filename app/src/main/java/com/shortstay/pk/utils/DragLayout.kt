package com.shortstay.pk.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.shortstay.pk.R
import kotlin.math.abs

@SuppressLint("CustomViewStyleable")
class DragLayout constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val bottomDragVisibleHeight: Int
    private val bottomExtraIndicatorHeight: Int
    private var dragTopDest = 0
    private var downState
            = 0
    private val mDragHelper: ViewDragHelper
    private val moveDetector: GestureDetectorCompat
    private var mTouchSlop = 5
    private var originX = 0
    private var originY = 0
    private var bottomView: View? = null
    private var topView: View? = null
    private var gotoDetailListener: GotoDetailListener? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomView = getChildAt(0)
        topView = getChildAt(1)
        topView?.setOnClickListener {
            val state = currentState
            if (state == STATE_CLOSE) {
                if (mDragHelper.smoothSlideViewTo(topView!!, originX, dragTopDest)) {
                    ViewCompat.postInvalidateOnAnimation(this@DragLayout)
                }
            } else {
                gotoDetailActivity()
            }
        }
    }

    private fun gotoDetailActivity() {
        if (null != gotoDetailListener) {
            gotoDetailListener!!.gotoDetail()
        }
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            if (changedView == topView) {
                processLinkageView()
            }
        }

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == topView
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val currentTop = child.top
            if (top > child.top) {
                return currentTop + (top - currentTop) / 2
            }
            return when {
                currentTop > DECELERATE_THRESHOLD * 3 -> {
                    currentTop + (top - currentTop) / 2
                }
                currentTop > DECELERATE_THRESHOLD * 2 -> {
                    currentTop + (top - currentTop) / 4
                }
                currentTop > 0 -> {
                    currentTop + (top - currentTop) / 8
                }
                currentTop > -DECELERATE_THRESHOLD -> {
                    currentTop + (top - currentTop) / 16
                }
                currentTop > -DECELERATE_THRESHOLD * 2 -> {
                    currentTop + (top - currentTop) / 32
                }
                currentTop > -DECELERATE_THRESHOLD * 3 -> {
                    currentTop + (top - currentTop) / 48
                }
                else -> {
                    currentTop + (top - currentTop) / 64
                }
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return child.left
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return 600
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return 600
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            var finalY = originY
            if (downState == STATE_CLOSE) {
                // 按下的时候，状态为：初始状态
                if (originY - releasedChild.top > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel < -DRAG_SWITCH_VEL_THRESHOLD) {
                    finalY = dragTopDest
                }
            } else {
                // 按下的时候，状态为：展开状态
                val gotoBottom =
                    releasedChild.top - dragTopDest > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel > DRAG_SWITCH_VEL_THRESHOLD
                if (!gotoBottom) {
                    finalY = dragTopDest

                    // 如果按下时已经展开，又向上拖动了，就进入详情页
                    if (dragTopDest - releasedChild.top > mTouchSlop) {
                        gotoDetailActivity()
                        postResetPosition()
                        return
                    }
                }
            }
            if (mDragHelper.smoothSlideViewTo(releasedChild, originX, finalY)) {
                ViewCompat.postInvalidateOnAnimation(this@DragLayout)
            }
        }
    }

    private fun postResetPosition() {
        postDelayed({ topView!!.offsetTopAndBottom(dragTopDest - topView!!.top) }, 500)
    }

    private fun processLinkageView() {
        if (topView!!.top > originY) {
            bottomView!!.alpha = 0f
        } else {
            var alpha = (originY - topView!!.top) * 0.01f
            if (alpha > 1) {
                alpha = 1f
            }
            bottomView!!.alpha = alpha
            val maxDistance = originY - dragTopDest
            val currentDistance = topView!!.top - dragTopDest
            var scaleRatio = 1f
            val distanceRatio = currentDistance.toFloat() / maxDistance
            if (currentDistance > 0) {
                scaleRatio =
                    MIN_SCALE_RATIO + (MAX_SCALE_RATIO - MIN_SCALE_RATIO) * (1 - distanceRatio)
            }
            bottomView!!.scaleX = scaleRatio
            bottomView!!.scaleY = scaleRatio
        }
    }

    internal inner class MoveDetector : SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent, dx: Float,
            dy: Float
        ): Boolean {
            // 拖动了，touch不往下传递
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop
        }
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private val currentState: Int
        get() {
            return if (abs(topView!!.top - dragTopDest) <= mTouchSlop) {
                STATE_EXPANDED
            } else {
                STATE_CLOSE
            }
        }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (!changed) {
            return
        }
        super.onLayout(changed, left, top, right, bottom)
        originX = topView!!.x.toInt()
        originY = topView!!.y.toInt()
        dragTopDest = bottomView!!.bottom - bottomDragVisibleHeight - topView!!.measuredHeight
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val yScroll = moveDetector.onTouchEvent(ev)
        var shouldIntercept = false
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }

        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            downState = currentState
            mDragHelper.processTouchEvent(ev)
        }
        return shouldIntercept && yScroll
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val bottomMarginTop =
            (bottomDragVisibleHeight + topView!!.measuredHeight / 2 - bottomView!!.measuredHeight / 2) / 2 - bottomExtraIndicatorHeight
        val lp1 = bottomView!!.layoutParams as LayoutParams
        lp1.setMargins(0, bottomMarginTop, 0, 0)
        bottomView!!.layoutParams = lp1
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        try {
            mDragHelper.processTouchEvent(e)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return true
    }

    fun setGotoDetailListener(gotoDetailListener: GotoDetailListener?) {
        this.gotoDetailListener = gotoDetailListener
    }

    interface GotoDetailListener {
        fun gotoDetail()
    }

    companion object {
        private const val DECELERATE_THRESHOLD = 120
        private const val DRAG_SWITCH_DISTANCE_THRESHOLD = 100
        private const val DRAG_SWITCH_VEL_THRESHOLD = 800
        private const val MIN_SCALE_RATIO = 0.5f
        private const val MAX_SCALE_RATIO = 1.0f
        private const val STATE_CLOSE = 1
        private const val STATE_EXPANDED = 2
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.app, 0, 0)
        bottomDragVisibleHeight =
            a.getDimension(R.styleable.app_bottomDragVisibleHeight, 0f).toInt()
        bottomExtraIndicatorHeight =
            a.getDimension(R.styleable.app_bototmExtraIndicatorHeight, 0f).toInt()
        a.recycle()
        mDragHelper = ViewDragHelper
            .create(this, 10f, DragHelperCallback())
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP)
        moveDetector = GestureDetectorCompat(context, MoveDetector())
        moveDetector.setIsLongpressEnabled(false) // 不处理长按事件
        val configuration = ViewConfiguration.get(getContext())
        mTouchSlop = configuration.scaledTouchSlop
    }
}