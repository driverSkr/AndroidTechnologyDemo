package com.example.banner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banner.R
import com.example.banner.adapter.MultipleTypesAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityVideoBinding
import com.example.banner.indicator.NumIndicator

/**
 * 仿淘宝商品详情，banner第一个放视频,然后首尾不能自己滑动，加上自定义数字指示器
 */
class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner.addBannerLifecycleObserver(this)
            .setAdapter(MultipleTypesAdapter(this, DataBean.getTestDataVideo()))

    }
}