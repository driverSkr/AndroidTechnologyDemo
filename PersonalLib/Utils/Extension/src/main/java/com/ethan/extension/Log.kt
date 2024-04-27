package com.ethan.extension

import android.util.Log

/**
 * 日志调试工具类。
 *
 * @author boge
 * @since  2023/10/17
 */
private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5

private val level = 0

fun logV(tag: String,msg: String?){
    if (level <= VERBOSE){
        Log.v(tag,msg.toString())
    }
}

fun logD(tag: String, msg: String?) {
    if (level <= DEBUG) {
        Log.d(tag, msg.toString())
    }
}

fun logI(tag: String, msg: String?) {
    if (level <= INFO) {
        Log.i(tag, msg.toString())
    }
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Log.w(tag, msg.toString())
        } else {
            Log.w(tag, msg.toString(), tr)
        }
    }
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    if (level <= ERROR) {
        Log.e(tag, msg.toString(), tr)
    }
}