package com.example.banner.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * 自定义布局，实现类似1号店、淘宝头条的滚动效果
 */
class TopLineAdapter(mDatas: List<DataBean>): BannerAdapter<DataBean, TopLineAdapter.TopLineHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): TopLineHolder = TopLineHolder(BannerUtils.getView(parent, R.layout.top_line_item2))

    override fun onBindView(holder: TopLineHolder?, data: DataBean, position: Int, size: Int) {
        holder?.message?.text = data.title
        when (data.viewType) {
            1 -> {
                holder?.source?.text = "1号店"
            }
            2 -> {
                holder?.source?.text = "淘宝头条"
            }
            3 -> {
                holder?.source?.text = "京东快报"
            }
        }
    }

    inner class TopLineHolder(view: View): RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.message)
        val source: TextView = view.findViewById(R.id.source)
    }
}