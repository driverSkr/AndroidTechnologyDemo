package com.example.banner.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.R
import com.example.banner.adapter.MyRecyclerViewAdapter

class BannerListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_recyclerview_banner, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text).text = "当前页：$index"
        view.findViewById<RecyclerView>(R.id.net_rv).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MyRecyclerViewAdapter(context)
        }
    }

    companion object {

        private var index: Int? = null

        fun newInstance(i: Int): Fragment {
            index = i
            return BannerListFragment()
        }
    }
}