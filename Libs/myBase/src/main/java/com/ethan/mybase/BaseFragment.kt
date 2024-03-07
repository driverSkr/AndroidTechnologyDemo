package com.ethan.mybase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment() {

    protected lateinit var binding: T
    private var isLoaded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initEvent()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            isLoaded = true
            loadData()
        }
    }

    protected abstract fun bindView(): T

    abstract fun initView(view: View?)

    abstract fun initEvent()

    /**
     * 数据初始化，只会执行一次
     */
    abstract fun loadData()

    override fun onDestroy() {
        super.onDestroy()
        isLoaded = false
    }
}