package com.example.banner.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.banner.adapter.ImageAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityTvBinding
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils

class TVActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner.setAdapter(ImageAdapter(DataBean.getTestData()))
            .setIndicator(CircleIndicator(this))
            .isAutoLoop(false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val count = binding.banner.itemCount
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                Log.d("boge", "向左")
                var prev = (binding.banner.currentItem - 1) % count
                if (prev == 0) {
                    prev = binding.banner.realCount
                } else if (prev == count - 1) {
                    prev = 1
                }
                binding.banner.setCurrentItem(prev, false)
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                Log.d("boge", "向右")
                var next = (binding.banner.currentItem + 1) % count
                if (next == 0) {
                    next = binding.banner.realCount
                } else if (next == count - 1) {
                    next = 1
                }
                binding.banner.setCurrentItem(next, false)
            }
        }
        //如果没有设置指示器，就不用执行下面两行
        val real = BannerUtils.getRealPosition(binding.banner.isInfiniteLoop, binding.banner.currentItem, binding.banner.realCount)
        binding.banner.indicator.onPageSelected(real)
        return super.onKeyDown(keyCode, event)
    }
}