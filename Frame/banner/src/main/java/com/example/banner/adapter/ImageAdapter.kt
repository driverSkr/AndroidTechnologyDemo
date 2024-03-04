package com.example.banner.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import com.example.banner.bean.DataBean
import com.example.banner.viewholder.ImageHolder
import com.youth.banner.adapter.BannerAdapter

/**
 * 自定义布局，图片
 */
//设置数据，也可以调用banner提供的方法,或者自己在adapter中实现 super(mDatas);
class ImageAdapter(mDatas: List<DataBean>): BannerAdapter<DataBean, ImageHolder>(mDatas) {

    //更新数据
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<DataBean>) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear()
        mDatas.addAll(data)
        notifyDataSetChanged()
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent?.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder?, data: DataBean, position: Int, size: Int) {
        holder?.imageView?.setImageResource(data.imageRes)
    }
}