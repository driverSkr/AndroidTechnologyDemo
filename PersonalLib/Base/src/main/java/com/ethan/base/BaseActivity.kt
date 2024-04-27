package com.ethan.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding>: AppCompatActivity(),  CreateInit<T>{

    private var contentLayout: FrameLayout? = null  // 子类view容器
    protected lateinit var context: Context
    protected lateinit var binding: T

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityOrientation(ActivityOrientation.UNSPECIFIED)
        super.setContentView(R.layout.activity_base)

        context = this
        init()
    }

    protected open fun init() {
        val toolbar: Toolbar = super.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        contentLayout = super.findViewById(R.id.frameLayout)

        binding = bindView()
        setContentView(binding.root)

        // 初始化数据
        prepareData(intent)
        // 初始化view
        initView()
        // 初始化事件
        initEvent()
        // 加载数据
        initData()
    }

    /**
     * 用于处理在活动（Activity）已经存在且位于前台时，收到新的 Intent 的情况。
     * 这种情况通常发生在活动已经启动并且用户再次点击应用程序图标或者从通知栏启动应用时。
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 初始化数据
        prepareData(intent)
    }

    fun setMyContentView(view: View) {
        contentLayout?.removeAllViews()
        contentLayout?.addView(view)
    }

    /**
     * 沉浸式状态栏
     */
    @SuppressLint("ObsoleteSdkInt")
    fun immersionStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                // 沉浸式状态栏
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                // 状态栏改为透明
                statusBarColor = Color.TRANSPARENT
            }
        }
    }

    /**
     * 当前 Activity 界面将会锁定为竖屏状态，即使用户旋转设备，界面也不会随着设备的物理旋转而切换到横屏模式
     */
    private fun setActivityOrientation(orientation: ActivityOrientation) {
        requestedOrientation = when (orientation) {
            ActivityOrientation.PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ActivityOrientation.LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            ActivityOrientation.UNSPECIFIED -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            ActivityOrientation.USER -> ActivityInfo.SCREEN_ORIENTATION_USER
            ActivityOrientation.BEHIND -> ActivityInfo.SCREEN_ORIENTATION_BEHIND
            ActivityOrientation.SENSOR -> ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }

    enum class ActivityOrientation {
        PORTRAIT, //强制竖屏显示
        LANDSCAPE, //强制横屏显示
        UNSPECIFIED, //未指定方向，由系统自动决定
        USER, //跟随用户设定的系统屏幕方向
        BEHIND, //与前一个Activity相同的方向
        SENSOR, //跟随设备传感器自动旋转
    }
}