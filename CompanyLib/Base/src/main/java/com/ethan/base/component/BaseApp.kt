package com.ethan.base.component

import android.annotation.SuppressLint
import android.os.Build
import android.os.Looper
import android.os.Process
import java.util.concurrent.Executors
import androidx.multidex.MultiDexApplication
import java.util.concurrent.TimeUnit

abstract class BaseApp : MultiDexApplication(){

    private val GC_CALL_DELAY = 15000L
    private var mGcRunning = false

    private val mExec = Executors.newSingleThreadScheduledExecutor { r ->
        object : Thread(r) {
            override fun run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST)
                super.run()
            }
        }
    }

    /**
     * 由于涉及到多进程的时候,onCreate会在每个进程初始化的时候被调用,可能需要对不同进程做一些处理,
     * 比如:有的模块只需在主进程中执行初始化,而无需在其他子进程中调用.
     * 第三方进程的初始化请写在第三方进程里面
     * 请重写[.onCreate]
     */
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        // 试运行:每隔固定时间,在消息队列闲时执行GC,降低内存峰值
        mExec.scheduleWithFixedDelay(Runnable {
            if (mGcRunning) return@Runnable
            mGcRunning = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Looper.getMainLooper().queue.addIdleHandler {
                    Runtime.getRuntime().gc()
                    mGcRunning = false
                    false
                }
            }
        }, GC_CALL_DELAY, GC_CALL_DELAY, TimeUnit.MILLISECONDS)

        initLibs()
    }

    protected abstract fun initLibs()
}