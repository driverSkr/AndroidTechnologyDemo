package com.ethan.flowbus

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


/**
 *
 * 使用例子,在有lifecycle的Activity或service里使用
 * 注册:
 *  FlowBus.with<String>(FlowBus.BUS_MAIN).register(this) { event ->
 *      if(event==FlowBus.EVEN_TEST)
 *      {
 *         }
 *  }
 * 发送:
 *  FlowBus.with<String>(FlowBus.BUS_MAIN).postMain(FlowBus.EVEN_TEST)
 *
 */
object FlowBus {

    private val busMap = mutableMapOf<String, EventBus<*>>()
    private val busStickMap = mutableMapOf<String, StickEventBus<*>>()
    const val BUS_MAIN = "main"
    const val BUS_SERVICE = "service"
    const val EVEN_TEST = "EVEN_TEST"



    @Synchronized
    fun <T> with(key: String): EventBus<T> {
        var eventBus = busMap[key]
        if (eventBus == null) {
            eventBus = EventBus<T>(key)
            busMap[key] = eventBus
        }
        return eventBus as EventBus<T>
    }

    @Synchronized
    fun <T> withStick(key: String): StickEventBus<T> {
        var eventBus = busStickMap[key]
        if (eventBus == null) {
            eventBus = StickEventBus<T>(key)
            busStickMap[key] = eventBus
        }
        return eventBus as StickEventBus<T>
    }

    //真正实现类
    open class EventBus<T>(private val key: String) : LifecycleEventObserver {

        //私有对象用于发送消息
        private val _events: MutableSharedFlow<T> by lazy {
            obtainEvent()
        }

        //暴露的公有对象用于接收消息
        val events = _events.asSharedFlow()

        open fun obtainEvent(): MutableSharedFlow<T> =
            MutableSharedFlow(0, 5, BufferOverflow.SUSPEND)

        //主线程接收数据
        fun register(lifecycleOwner: LifecycleOwner, action: (t: T) -> Unit) {
            lifecycleOwner.lifecycle.addObserver(this)
            lifecycleOwner.lifecycleScope.launch {
                events.collect {
                    try {
                        action(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        //协程中发送数据
        suspend fun postCurrentSp(event: T) {
            _events.emit(event)
        }

        //主线程发送数据
        fun postMain(event: T) {
            MainScope().launch {
                _events.emit(event)
            }
        }

        //子主线程发送数据
        fun post(event: T) {
            MainScope().launch(Dispatchers.IO) {
                _events.emit(event)
            }
        }


        //自动销毁（LifecycleObserver被LifecycleEventObserver替代）
        //@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        private fun onDestroy() {
            val subscriptCount = _events.subscriptionCount.value
            if (subscriptCount <= 0)
                busMap.remove(key)
        }

        //使用回调方式监控Activity的生命周期
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_DESTROY -> onDestroy()
                else -> Log.d("ethan","$event")
            }
        }
    }

    class StickEventBus<T>(key: String) : EventBus<T>(key) {
        override fun obtainEvent(): MutableSharedFlow<T> =
            MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
    }

}


