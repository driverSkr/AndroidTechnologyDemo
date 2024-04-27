package com.ethan.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import com.ethan.util.Dimensions.dp2px

/**
 * 设置TextView图标
 *
 * @param drawable     图标
 * @param iconWidth    图标宽dp：默认自动根据图标大小
 * @param iconHeight   图标高dp：默认自动根据图标大小
 * @param direction    图标方向，0左 1上 2右 3下 默认图标位于左侧0
 */
fun TextView.setDrawable(context: Context, drawable: Drawable?,iconWidth: Float? = null, iconHeight: Float? = null,direction: Int = 0){
    if (iconWidth != null && iconHeight != null){
        //第一个0是距左边距离，第二个0是距上边距离，iconWidth、iconHeight 分别是长宽
        drawable?.setBounds(0,0, dp2px(context, iconWidth), dp2px(context, iconHeight))
    }
    when(direction){
        0 -> setCompoundDrawables(drawable,null,null,null)
        1 -> setCompoundDrawables(null, drawable, null, null)
        2 -> setCompoundDrawables(null, null, drawable, null)
        3 -> setCompoundDrawables(null, null, null, drawable)
        else -> throw NoSuchMethodError()
    }
}

/**
 * 设置TextView图标
 *
 * @param lDrawable     左边图标
 * @param rDrawable     右边图标
 * @param lIconWidth    左边图标宽dp：默认自动根据图标大小
 * @param lIconHeight   左边图标高dp：默认自动根据图标大小
 * @param rIconWidth    右边图标宽dp：默认自动根据图标大小
 * @param rIconHeight   右边图标高dp：默认自动根据图标大小
 */
fun TextView.setDrawables(context: Context, lDrawable: Drawable?,rDrawable: Drawable?, lIconWidth: Float? = null, lIconHeight: Float? = null, rIconWidth: Float? = null, rIconHeight: Float? = null){
    if (lIconWidth != null && lIconHeight != null) {
        lDrawable?.setBounds(0, 0, dp2px(context, lIconWidth), dp2px(context, lIconHeight))
    }
    if (rIconWidth != null && rIconHeight != null) {
        rDrawable?.setBounds(0, 0, dp2px(context, rIconWidth), dp2px(context, rIconHeight))
    }
    setCompoundDrawables(lDrawable, null, rDrawable, null)
}