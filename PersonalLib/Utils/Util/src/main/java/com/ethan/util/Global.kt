package com.ethan.util

import android.view.View

object Global {

    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param block 处理点击事件回调代码块
     */
    fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
        val listener = View.OnClickListener { it.block() }
        v.forEach { it?.setOnClickListener(listener) }
    }

    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param listener 处理点击事件监听器
     */
    fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
        v.forEach { it?.setOnClickListener(listener) }
    }
}