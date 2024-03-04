package com.example.banner.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.banner.R
import com.example.banner.adapter.ImageNetAdapter
import com.example.banner.bean.DataBean
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils

class BlankFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.test, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayout = view?.findViewById<LinearLayout>(R.id.ll_view)

        //通过new的方式创建banner
        val banner = Banner<DataBean, ImageNetAdapter>(activity)
        banner.apply {
            setAdapter(ImageNetAdapter(DataBean.getTestData3()))
            addBannerLifecycleObserver(this@BlankFragment)
            indicator = CircleIndicator(activity)
        }

        //将banner加入到父容器中，实际使用不一定一样
        linearLayout?.addView(banner, LinearLayout.LayoutParams.MATCH_PARENT, BannerUtils.dp2px(120f))
    }

    companion object {
        fun newInstance(): Fragment = BlankFragment()
    }
}