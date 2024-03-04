package com.example.banner.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.youth.banner.indicator.BaseIndicator
import com.youth.banner.util.BannerUtils

/**
 * 自定义数字指示器demo，比较简单，具体的自己发挥
 *
 * 这里没有用的自定义属性的参数，可以考虑加上
 */
class NumIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
): BaseIndicator(context, attrs, defStyleAttr) {

    private var width: Int
    private var height: Int
    private var radius: Int

    init {
        mPaint.textSize = BannerUtils.dp2px(10f).toFloat()
        mPaint.textAlign = Paint.Align.CENTER
        width = BannerUtils.dp2px(30f)
        height = BannerUtils.dp2px(15f)
        radius = BannerUtils.dp2px(20f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = config.indicatorSize
        if (count <= 1) {
            return
        }
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = config.indicatorSize
        if (count <= 1) {
            return
        }

        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        mPaint.color = Color.parseColor("#70000000")
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), mPaint)

        val text = "${config.currentPosition + 1}/$count"
        mPaint.color = Color.WHITE
        canvas.drawText(text, width / 2f, height * 0.7f, mPaint)
    }
}