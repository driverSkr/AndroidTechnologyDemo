package com.example.banner.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.example.banner.bean.DataBean
import com.youth.banner.Banner
import com.youth.banner.indicator.RoundLinesIndicator
import com.youth.banner.util.BannerUtils

class MyRecyclerViewAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item) {
            MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        } else MyBannerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.banner, parent, false))
    }

    override fun getItemCount(): Int = 10

    override fun getItemViewType(position: Int): Int = if (position % 2 == 0) R.layout.item else R.layout.banner

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.cardView.setBackgroundColor(Color.parseColor(DataBean.getRandColor()))
        } else if (holder is MyBannerViewHolder) {
            holder.banner.apply {
                setAdapter(ImageNetAdapter(DataBean.getTestData3()))
                setBannerRound(BannerUtils.dp2px(5f).toFloat())
                indicator = RoundLinesIndicator(context)
                setIndicatorSelectedWidth(BannerUtils.dp2px(15f))
            }
        }
    }

    //banner 内部已实现
//    @Override
//    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        Log.e("banner_log", "onViewDetachedFromWindow:" + holder.getAdapterPosition());
//        //定位你的位置
//        if (holder.getAdapterPosition()%2!=0) {
//            if (holder instanceof MyBannerViewHolder) {
//                ((MyBannerViewHolder) holder).banner.stop();
//            }
//        }
//    }
//
//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        Log.e("banner_log", "onViewAttachedToWindow:" + holder.getAdapterPosition());
//        if (holder.getAdapterPosition()%2!=0) {
//            if (holder instanceof MyBannerViewHolder) {
//                ((MyBannerViewHolder) holder).banner.start();
//            }
//        }
//    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.card_view)
    }

    inner class MyBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val banner: Banner<DataBean, ImageNetAdapter> = itemView.findViewById(R.id.banner)
    }
}