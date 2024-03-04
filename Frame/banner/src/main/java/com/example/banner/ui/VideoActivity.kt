package com.example.banner.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.banner.adapter.MultipleTypesAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityVideoBinding
import com.example.banner.indicator.NumIndicator
import com.example.banner.viewholder.VideoHolder
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.listener.OnPageChangeListener

/**
 * 仿淘宝商品详情，banner第一个放视频,然后首尾不能自己滑动，加上自定义数字指示器
 */
class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private var player: StandardGSYVideoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner.addBannerLifecycleObserver(this)
            .setAdapter(MultipleTypesAdapter(this, DataBean.getTestDataVideo()))
            .setIndicator(NumIndicator(this))
            .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
            .addOnPageChangeListener(object: OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    stopVideo(position)
                }

                override fun onPageSelected(position: Int) {
                    Log.e("boge", "position:$position")
                    stopVideo(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
    }

    private fun stopVideo(position: Int) {
        if (player == null) {
            val viewHolder = binding.banner.adapter.viewHolder
            if (viewHolder is VideoHolder) {
                player = viewHolder.player
                if (position != 0) {
                    player?.onVideoPause()
                }
            }
        } else {
            if (position != 0) {
                player?.onVideoPause()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        player?.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        player?.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        //释放所有
        player?.setVideoAllCallBack(null)
        super.onBackPressed()
    }
}