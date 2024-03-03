package com.example.banner.adapter

import android.content.Context
import android.graphics.Color
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.example.banner.viewholder.ImageHolder
import com.example.banner.viewholder.TitleHolder
import com.example.banner.viewholder.VideoHolder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * 自定义布局,多个不同UI切换
 */
class MultipleTypesAdapter(private val context: Context, mDatas: List<DataBean>): BannerAdapter<DataBean, RecyclerView.ViewHolder>(mDatas) {

    private val mVHMap = SparseArray<RecyclerView.ViewHolder>()

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ImageHolder(BannerUtils.getView(parent, R.layout.banner_image))
            2 -> VideoHolder(BannerUtils.getView(parent, R.layout.banner_video))
            3 -> TitleHolder(BannerUtils.getView(parent, R.layout.banner_title))
            else -> ImageHolder(BannerUtils.getView(parent, R.layout.banner_image))
        }
    }

    override fun getItemViewType(position: Int): Int {
        //先取得真实的position,在获取实体
//        return getData(getRealPosition(position)).viewType;
        //直接获取真实的实体
        return getRealData(position).viewType
        //或者自己直接去操作集合
//        return mDatas.get(getRealPosition(position)).viewType;
    }

    override fun onBindView(
        holder: RecyclerView.ViewHolder?,
        data: DataBean,
        position: Int,
        size: Int
    ) {
        when (holder?.itemViewType) {
            1 -> {
                val imageHolder = holder as ImageHolder
                mVHMap.append(position, imageHolder)
                imageHolder.imageView.setImageResource(data.imageRes)
            }
            2 -> {
                val videoHolder = holder as VideoHolder
                mVHMap.append(position, videoHolder)
                videoHolder.player.setUp(data.imageUrl, true, null)
                videoHolder.player.backButton.visibility = View.GONE
                //增加封面
                val imageView = ImageView(context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setImageResource(R.drawable.image4)
                videoHolder.player.thumbImageView = imageView
            }
            3 -> {
                val titleHolder = holder as TitleHolder
                mVHMap.append(position, titleHolder)
                titleHolder.title.text = data.title
                titleHolder.title.setBackgroundColor(Color.parseColor(DataBean.getRandColor()))
            }
        }
    }

    fun getVHMap(): SparseArray<RecyclerView.ViewHolder> = mVHMap
}