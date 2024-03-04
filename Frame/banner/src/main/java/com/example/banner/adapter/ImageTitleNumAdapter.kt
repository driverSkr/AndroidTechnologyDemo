package com.example.banner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.youth.banner.adapter.BannerAdapter

class ImageTitleNumAdapter(mDatas: List<DataBean>): BannerAdapter<DataBean, ImageTitleNumAdapter.BannerViewHolder>(mDatas) {

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        //注意布局文件，item布局文件要设置为match_parent，这个是viewpager2强制要求的
        //或者调用BannerUtils.getView(parent,R.layout.banner_image_title_num);
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.banner_image_title_num, parent, false)
        return BannerViewHolder(view)
    }

    //绑定数据
    @SuppressLint("SetTextI18n")
    override fun onBindView(holder: BannerViewHolder?, data: DataBean, position: Int, size: Int) {
        holder?.imageView?.setImageResource(data.imageRes)
        holder?.title?.text = data.title
        //可以在布局文件中自己实现指示器，亦可以使用banner提供的方法自定义指示器，目前样式较少，后面补充
        holder?.numIndicator?.text = "${position + 1}/$size"
    }

    inner class BannerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.bannerTitle)
        val numIndicator: TextView = view.findViewById(R.id.numIndicator)
    }
}