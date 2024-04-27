package com.ethan.customControls.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ethan.customControls.R;
import com.ethan.customControls.fragment.DiscoverFragment;
import com.ethan.customControls.fragment.HomeFragment;
import com.ethan.customControls.fragment.MineFragment;
import com.ethan.customControls.fragment.NearbyFragment;
import com.ethan.customControls.fragment.OrderFragment;
import com.ethan.customControls.widget.CustomBottomTabWidget;

import java.util.ArrayList;
import java.util.List;

public class CustomBottomTabActivity extends AppCompatActivity {
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //让背景图和状态栏融合到一起（非借助Material库完成）
        View decorView = getWindow().getDecorView();//拿到当前Activity的DecorView
        //表示Activity 的布局会显示在状态栏上面
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        //将状态栏设置成透明色
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_bottom_tab);

        addFragments();
        CustomBottomTabWidget tabWidget = findViewById(R.id.tabWidget);
        tabWidget.init(getSupportFragmentManager(),fragmentList);
    }

    private void addFragments(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new NearbyFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new MineFragment());
    }
}