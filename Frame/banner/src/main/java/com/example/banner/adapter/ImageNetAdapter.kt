package com.example.banner.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.example.banner.viewholder.ImageHolder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class ImageNetAdapter(mDatas: List<DataBean>): BannerAdapter<DataBean, ImageHolder>(mDatas) {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = BannerUtils.getView(parent, R.layout.banner_image)
        //通过裁剪实现圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(imageView, 20f)
        }
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder, data: DataBean?, position: Int, size: Int) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
            .load(data?.imageUrl)
            .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            //设置圆角
            //.apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)
    }
}