package com.example.banner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banner.R
import com.example.banner.adapter.TopLineAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityTouTiaoBinding
import com.google.android.material.snackbar.Snackbar
import com.youth.banner.Banner
import com.youth.banner.transformer.ZoomOutPageTransformer

/**
 * 用banner实现类似淘宝头条的效果
 */
class TouTiaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTouTiaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTouTiaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //实现1号店和淘宝头条类似的效果
        binding.banner.setAdapter(TopLineAdapter(DataBean.getTestData2()))
            .setOrientation(Banner.VERTICAL)
            .setPageTransformer(ZoomOutPageTransformer())
            .setOnBannerListener { data, position ->
                Snackbar.make(binding.banner, (data as DataBean).title as CharSequence, Snackbar.LENGTH_SHORT).show()
                Log.d("boge", "position：$position")
            }
    }
}