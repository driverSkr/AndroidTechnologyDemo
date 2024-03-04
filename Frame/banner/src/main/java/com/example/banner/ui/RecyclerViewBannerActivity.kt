package com.example.banner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banner.adapter.MyRecyclerViewAdapter
import com.example.banner.databinding.ActivityRecyclerviewBannerBinding


/**
 * RecyclerView嵌套banner
 */
class RecyclerViewBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerviewBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.netRv.layoutManager = LinearLayoutManager(this)
        binding.netRv.adapter = MyRecyclerViewAdapter(this)
    }
}