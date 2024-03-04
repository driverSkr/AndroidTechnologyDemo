package com.example.banner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.banner.adapter.ImageAdapter
import com.example.banner.bean.DataBean
import com.example.banner.databinding.ActivityVp2FragmentRecyclerviewBinding
import com.example.banner.ui.fragment.BannerFragment
import com.example.banner.ui.fragment.BannerListFragment
import com.example.banner.ui.fragment.BlankFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.indicator.CircleIndicator

/**
 * viewpaer2+fragment+RecyclerView嵌套banner
 */
class Vp2FragmentRecyclerviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVp2FragmentRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVp2FragmentRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vp2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> BannerListFragment.newInstance(position)
                    1 -> BlankFragment.newInstance()
                    else -> BannerFragment.newInstance()
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.vp2) {tab, position -> tab.setText("页面$position")}.attach()

        binding.banner.addBannerLifecycleObserver(this)
            .setAdapter(ImageAdapter(DataBean.getTestData()))
            .setIntercept(false)
            .setIndicator(CircleIndicator(this))
    }
}