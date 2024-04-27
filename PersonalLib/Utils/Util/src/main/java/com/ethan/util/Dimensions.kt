package com.ethan.util

import android.content.Context

object Dimensions {

    /**
     * 根据手机的分辨率将dp转成为px。
     */
    fun dp2px(context: Context, dp: Float): Int{
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率将px转成dp。
     */
    fun px2dp(context: Context, px: Float): Int{
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕像素：对获取的宽高进行拼接。例：1080X2340。
     */
    fun screenPixel(context: Context): String{
        context.resources.displayMetrics.run {
            return "${widthPixels}X${heightPixels}"
        }
    }
}