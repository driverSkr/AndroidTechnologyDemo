package com.example.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.example.banner.viewholder.ImageTitleHolder
import com.youth.banner.adapter.BannerAdapter

/**
 * 自定义布局，图片+标题
 */
class ImageTitleAdapter(mDatas: List<DataBean>): BannerAdapter<DataBean, ImageTitleHolder>(mDatas) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageTitleHolder = ImageTitleHolder(LayoutInflater.from(parent?.context).inflate(R.layout.banner_image_title, parent, false))

    override fun onBindView(holder: ImageTitleHolder?, data: DataBean, position: Int, size: Int) {
        holder?.imageView?.setImageResource(data.imageRes)
        holder?.title?.text = data.title
    }
}