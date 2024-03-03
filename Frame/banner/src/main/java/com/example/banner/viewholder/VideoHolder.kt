package com.example.banner.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class VideoHolder(view: View): RecyclerView.ViewHolder(view) {
    val player: StandardGSYVideoPlayer = view.findViewById(R.id.player)
}