package com.ethan.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment

abstract class AbsDialogFragment: AppCompatDialogFragment() {

    //默认暗淡值(0.0f（不暗化，即背景完全可见）到1.0f（完全暗化，背景几乎不可见）)
    private val defaultDimAmount = 0.2f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        if (getLayoutRes() > 0) {
            view = inflater.inflate(getLayoutRes(), container, false)
        }
        if (view == null) {
            view = getDialogView()
        }
        bindView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCanceledOnTouchOutside(isCancelableOutside())
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window?.let {
            //对话框（Dialog）窗口背景透明
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val layoutParams = window.attributes
            layoutParams.width = if (getDialogWidth() > 0) getDialogWidth() else WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.height = if (getDialogHeight() > 0) getDialogHeight() else WindowManager.LayoutParams.WRAP_CONTENT
            //背景暗化程度（也称为背景模糊或者背景灰度）,0.0f（不暗化，即背景完全可见）到1.0f（完全暗化，背景几乎不可见）
            layoutParams.dimAmount = getDimAmount()
            layoutParams.gravity = getGravity()
            it.attributes = layoutParams

            if (dialog!!.window != null && getDialogAnimationRes() > 0) {
                //设置对话框显示或消失时所使用的动画资源
                dialog!!.window!!.setWindowAnimations(getDialogAnimationRes())
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        //指示当软输入法出现时，窗口的内容区域应当被重新调整大小（resize），以便在剩余的空间里显示内容，同时保持软输入法可见。
        // 这意味着当软键盘弹出时，对话框内的内容将会自动压缩，以避免被键盘遮挡。
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    open fun getGravity(): Int = Gravity.CENTER

    open fun getDialogHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun getDialogWidth(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun getDimAmount(): Float = defaultDimAmount

    open fun getFragmentTag(): String? = AbsDialogFragment::class.java.simpleName

    abstract fun show(): BaseDialog?

    abstract fun bindView(view: View?)

    protected open fun isCancelableOutside(): Boolean = true

    protected abstract fun setCancelableOutside(b: Boolean)

    protected open fun getDialogAnimationRes(): Int = 0

    protected abstract fun getLayoutRes(): Int

    protected abstract fun getDialogView(): View?
}