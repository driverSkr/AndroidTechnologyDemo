package com.example.banner.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.banner.R
import com.example.banner.adapter.ImageNetAdapter
import com.example.banner.bean.DataBean
import com.youth.banner.Banner
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.util.BannerUtils

class BannerFragment: Fragment() {

    private lateinit var banner: Banner<DataBean, ImageNetAdapter>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.banner, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        banner = view.findViewById(R.id.banner)
        banner.apply {
            setAdapter(ImageNetAdapter(DataBean.getTestData3()))
            indicator = RectangleIndicator(activity)
            setIndicatorSpace(BannerUtils.dp2px(4f))
            setIndicatorRadius(0)
        }
    }

    override fun onStart() {
        super.onStart()
        banner.start()
    }

    override fun onStop() {
        super.onStop()
        banner.stop()
    }

    companion object {
        fun newInstance(): Fragment = BannerFragment()
    }
}