package com.example.banner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banner.adapter.ImageAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityGalleryBinding
import com.youth.banner.indicator.CircleIndicator

/**
 * 画廊,魅族效果,Drawable指示器
 */
class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 画廊效果
         */
        binding.banner1.apply {
            setAdapter(ImageAdapter(DataBean.getTestData2()))
            indicator = CircleIndicator(this@GalleryActivity)
            //添加画廊效果
            setBannerGalleryEffect(50, 10)
            //(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
            //添加透明效果(画廊配合透明效果更棒)
            //mBanner1.addPageTransformer(new AlphaPageTransformer());
        }

        /**
         * 魅族效果
         */
        binding.banner2.apply {
            setAdapter(ImageAdapter(DataBean.getTestData()))
            setIndicator(binding.indicator, false)
            //添加魅族效果
            setBannerGalleryMZ(20)
        }
    }
}