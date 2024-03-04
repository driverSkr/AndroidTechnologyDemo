package com.example.banner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.banner.adapter.ImageAdapter
import com.example.banner.adapter.ImageTitleAdapter
import com.example.banner.adapter.ImageTitleNumAdapter
import com.example.banner.adapter.MultipleTypesAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityBannerBinding
import com.example.banner.ui.ConstraintLayoutBannerActivity
import com.example.banner.ui.GalleryActivity
import com.example.banner.ui.RecyclerViewBannerActivity
import com.example.banner.ui.TVActivity
import com.example.banner.ui.TouTiaoActivity
import com.example.banner.ui.VideoActivity
import com.example.banner.ui.Vp2FragmentRecyclerviewActivity
import com.google.android.material.snackbar.Snackbar
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.RoundLinesIndicator
import com.youth.banner.util.BannerUtils
import java.lang.IllegalStateException

class BannerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = DataBean.getTestData2()
        //自定义的图片适配器，也可以使用默认的BannerImageAdapter
        val adapter = ImageAdapter(datas)

        binding.banner.setAdapter(adapter)
            //添加生命周期观察者
            .addBannerLifecycleObserver(this)
            //设置指示器
            .setIndicator(CircleIndicator(this))
            .setOnBannerListener { data, position ->
                Snackbar.make(binding.banner, (data as DataBean).title as CharSequence, Snackbar.LENGTH_SHORT).show()
                Log.d("boge","position:$position")
            }

        //添加item之间切换时的间距(如果使用了画廊效果就不要添加间距了，因为内部已经添加过了)
//        banner.addPageTransformer(new MarginPageTransformer( BannerUtils.dp2px(10)));

        //和下拉刷新配套使用
        binding.swipeRefresh.setOnRefreshListener {
            //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
            Handler().postDelayed ({
               binding.swipeRefresh.isRefreshing = false

                // 给banner重新设置数据（完全覆盖）
                binding.banner.setDatas(DataBean.getTestData())

               //模拟请求成功（原数据减少） 刷新banner
//                datas.remove(0);
//                adapter.notifyDataSetChanged();

                //对setDatas()方法不满意？你可以自己在adapter控制数据，参考setDatas()的实现修改
//                adapter.updateData(DataBean.getTestData());
//                banner.setCurrentItem(banner.getStartPosition(), false);
//                banner.setIndicatorPageChange();
            }, 2000)
        }

        binding.apply {
            styleImage.setOnClickListener(this@BannerActivity)
            styleImageTitle.setOnClickListener(this@BannerActivity)
            styleImageTitleNum.setOnClickListener(this@BannerActivity)
            styleMultiple.setOnClickListener(this@BannerActivity)
            styleNetImage.setOnClickListener(this@BannerActivity)
            changeIndicator.setOnClickListener(this@BannerActivity)
            gallery.setOnClickListener(this@BannerActivity)
            rvBanner.setOnClickListener(this@BannerActivity)
            clBanner.setOnClickListener(this@BannerActivity)
            vpBanner.setOnClickListener(this@BannerActivity)
            bannerVideo.setOnClickListener(this@BannerActivity)
            bannerTv.setOnClickListener(this@BannerActivity)
            topLine.setOnClickListener(this@BannerActivity)
        }
    }

    override fun onClick(v: View?) {
        binding.indicator.visibility = View.GONE
        when (v?.id) {
            R.id.style_image -> {
                binding.swipeRefresh.isEnabled = true
                binding.banner.apply {
                    setAdapter(ImageAdapter(DataBean.getTestData()))
                    indicator = CircleIndicator(this@BannerActivity)
                    setIndicatorGravity(IndicatorConfig.Direction.CENTER)
                }
            }
            R.id.style_image_title -> {
                binding.swipeRefresh.isEnabled = true
                binding.banner.apply {
                    setAdapter(ImageTitleAdapter(DataBean.getTestData()))
                    indicator = CircleIndicator(this@BannerActivity)
                    setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    setIndicatorMargins(IndicatorConfig.Margins(0, 0, BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12F)))
                }
            }
            R.id.style_image_title_num -> {
                binding.swipeRefresh.isEnabled = true
                //这里是将数字指示器和title都放在adapter中的，如果不想这样你也可以直接设置自定义的数字指示器
                binding.banner.setAdapter(ImageTitleNumAdapter(DataBean.getTestData()))
                binding.banner.removeIndicator()
            }
            R.id.style_multiple -> {
                binding.swipeRefresh.isEnabled = true
                binding.banner.indicator = CircleIndicator(this)
                binding.banner.setAdapter(MultipleTypesAdapter(this, DataBean.getTestData()))
            }
            R.id.style_net_image -> {
                binding.swipeRefresh.isEnabled = false
                //方法一：使用自定义图片适配器
//                binding.banner.setAdapter(ImageNetAdapter(DataBean.getTestData3()));
                //方法二：使用自带的图片适配器
                binding.banner.setAdapter(object: BannerImageAdapter<DataBean>(DataBean.getTestData3()){
                    override fun onBindView(holder: BannerImageHolder, data: DataBean?, position: Int, size: Int) {
                        //图片加载自己实现
                        Glide.with(holder.itemView)
                            .load(data?.imageUrl)
                            .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                            .into(holder.imageView)
                    }
                })

                binding.banner.indicator = RoundLinesIndicator(this)
                binding.banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15F))
            }
            R.id.change_indicator -> {
                binding.indicator.visibility = View.VISIBLE
                //在布局文件中使用指示器，这样更灵活
                binding.banner.setIndicator(binding.indicator, false)
                binding.banner.setIndicatorSelectedWidth(BannerUtils.dp2px(15f))
            }
            R.id.gallery -> startActivity(Intent(this, GalleryActivity::class.java))
            R.id.topLine -> startActivity(Intent(this, TouTiaoActivity::class.java))
            R.id.rv_banner -> startActivity(Intent(this, RecyclerViewBannerActivity::class.java))
            R.id.cl_banner -> startActivity(Intent(this, ConstraintLayoutBannerActivity::class.java))
            R.id.vp_banner -> startActivity(Intent(this, Vp2FragmentRecyclerviewActivity::class.java))
            R.id.banner_video -> startActivity(Intent(this, VideoActivity::class.java))
            R.id.banner_tv -> startActivity(Intent(this, TVActivity::class.java))
            else -> IllegalStateException("Unexpected value: ${v?.id}")
        }
    }
}