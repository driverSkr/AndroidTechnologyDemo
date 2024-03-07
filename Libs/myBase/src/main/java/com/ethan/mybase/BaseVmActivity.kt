package com.ethan.mybase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVmActivity<T: ViewBinding, V: ViewModel>: BaseActivity<T>() {

    protected lateinit var viewModel: V

    override fun init() {
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        super.init()
    }

    private fun getViewModelClass(): Class<V> {
        val type = javaClass.genericSuperclass as ParameterizedType
        return  type.actualTypeArguments[0] as Class<V>
    }
}