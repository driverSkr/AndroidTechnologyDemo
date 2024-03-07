package com.ethan.mybase

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVmFragment<T: ViewBinding, V: ViewModel>: BaseFragment<T>() {

    protected lateinit var viewModel: V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getViewModelClass(): Class<V> {
        val type = javaClass.genericSuperclass as ParameterizedType
        return  type.actualTypeArguments[0] as Class<V>
    }
}