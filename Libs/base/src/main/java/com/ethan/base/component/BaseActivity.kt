package com.ethan.base.component

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.ethan.base.R
import com.ethan.base.dialog.BaseDialog
import com.ethan.base.progress.ProgressSupport
import com.ethan.base.utils.AndroidBarUtils

private const val TAG = "BaseActivity"

class BaseActivity: AppCompatActivity(), ProgressSupport {

    private var contentLayout: FrameLayout? = null
    private val handler = Handler(Looper.getMainLooper())
    private var loadingDialog: BaseDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        fitStatusBar()
        //AndroidBarUtils.transparentNavBar(this)
        setStatusTextDark(false)
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.layout_activity)
        contentLayout = findViewById(R.id.view_content)
    }

    /**
     * 让Android应用的当前Activity与状态栏融合，实现沉浸式全屏效果
     */
    private fun fitStatusBar() {
        //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN表示要求系统在计算布局时，将状态栏区域视为可利用的空间，
        // 使Activity内容延伸至状态栏下方，但状态栏本身依然可见，只是内容可以覆盖其上
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //设置了当前Activity的状态栏颜色为透明
        window.statusBarColor = Color.TRANSPARENT
    }

    /**
     * 改变当前Activity窗口的状态栏文字颜色是否为深色（通常是黑色）
     */
    fun setStatusTextDark(dark: Boolean) {
        val controller = ViewCompat.getWindowInsetsController(window.decorView)
        controller?.isAppearanceLightStatusBars = dark
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (f: Fragment in fragments) {
            dispatchActivityResult(f, requestCode, resultCode, data)
        }
    }

    /**
     * 确保在Activity及其嵌套的所有Fragment层次结构中正确分发Activity的结果数据
     */
    private fun dispatchActivityResult(fragment: Fragment, requestCode: Int, resultCode: Int, data: Intent?) {
        fragment.onActivityResult(requestCode, resultCode, data)
        val fragments = fragment.childFragmentManager.fragments
        for (child: Fragment in fragments) {
            dispatchActivityResult(child, requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //dismdismissLoadingDialog()
        loadingDialog = null
    }

    override fun isDestroy(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUIHandler(): Handler {
        TODO("Not yet implemented")
    }
}