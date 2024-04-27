package com.ethan.customControls.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.BannerPager;
import com.ethan.util.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class BannerPagerActivity extends AppCompatActivity implements BannerPager.BannerClickListener {
    private static final String TAG = "BannerPagerActivity";
    private TextView tv_pager; // 声明一个文本视图对象

    private List<Integer> getImageList() {
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.mipmap.banner_1);
        imageList.add(R.mipmap.banner_2);
        imageList.add(R.mipmap.banner_3);
        imageList.add(R.mipmap.banner_4);
        imageList.add(R.mipmap.banner_5);
        return imageList; // 返回默认的广告图片列表
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pager);

        tv_pager = findViewById(R.id.tv_pager);
        // 从布局文件中获取名叫banner_pager的广告轮播条
        BannerPager banner = findViewById(R.id.banner_pager);
        // 获取广告轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        banner.setLayoutParams(params); // 设置广告轮播条的布局参数
        banner.setImage(getImageList()); // 设置广告轮播条的广告图片列表
        banner.setOnBannerListener(this); // 设置广告轮播条的广告点击监听器
        banner.start(); // 开始广告图片的轮播滚动
    }

    // 一旦点击了广告图，就回调监听器的onBannerClick方法
    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        tv_pager.setText(desc);
    }
}