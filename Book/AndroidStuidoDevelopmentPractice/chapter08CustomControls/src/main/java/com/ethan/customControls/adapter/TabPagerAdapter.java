package com.ethan.customControls.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

//ViewPager翻页视图适配器
//FragmentPagerAdapter继承自PagerAdapter
public class TabPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    // 碎片页适配器的构造方法，传入碎片管理器
    public TabPagerAdapter(@NonNull FragmentManager fm , List<Fragment> fragments) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    // 获取指定位置的碎片Fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // 获取碎片Fragment的个数
    @Override
    public int getCount() {
        return fragments.size();
    }
}
