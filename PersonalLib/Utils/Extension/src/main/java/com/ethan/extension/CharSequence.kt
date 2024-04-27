package com.ethan.extension

import android.content.Context
import android.widget.Toast

/**
 * 弹出Toast提示。
 * @param duration 显示消息的时间
 */
fun CharSequence.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

