package com.example.banner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banner.R
import com.example.banner.adapter.ImageTitleAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityConstraintLayoutBannerBinding
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils

/**
 * ConstraintLayout嵌套banner
 */
class ConstraintLayoutBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstraintLayoutBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintLayoutBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner.apply {
            setAdapter(ImageTitleAdapter(DataBean.getTestData()))
            indicator = CircleIndicator(this@ConstraintLayoutBannerActivity)
            setIndicatorSelectedColorRes(R.color.main_color)
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
            setIndicatorMargins(IndicatorConfig.Margins(0, 0,
                BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12f)))
            addBannerLifecycleObserver(this@ConstraintLayoutBannerActivity)
        }
    }
}