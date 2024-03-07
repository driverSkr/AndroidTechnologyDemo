package com.ethan.mybase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

open class BaseViewModel(app: Application): AndroidViewModel(app) {


    private var runningCount = AtomicInteger(0)

    /**
     * 是否正在请求网络
     */
    fun isStopped(): Boolean {
        return runningCount.get() == 0
    }


    /**
     * 开始显示loading,结束关闭loading
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            /**切换到 IO 线程执行传入的 block 挂起函数**/
            withContext(Dispatchers.IO) {
                /**
                 * block.invoke(this) 和 block()等价
                 * 使用 invoke(this) 的方式是为了提供更多的语义，特别是当函数类型的参数具有接收者类型时（即带有 suspend CoroutineScope.() -> Unit 这样的带接收者的函数类型）
                 * 通过 invoke(this)，你明确指定了接收者为当前的 CoroutineScope，使代码更易读
                 */
                block.invoke(this)
            }
        }
    }
}