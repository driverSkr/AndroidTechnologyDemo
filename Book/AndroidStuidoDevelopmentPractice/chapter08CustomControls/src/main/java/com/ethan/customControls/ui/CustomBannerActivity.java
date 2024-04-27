package com.ethan.customControls.ui;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.CustomBanner;

import java.util.ArrayList;
import java.util.List;

public class CustomBannerActivity extends AppCompatActivity {

    List<Integer> imageList;
    private CustomBanner cb_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_banner);
        initImageList();

        cb_banner = findViewById(R.id.cb_banner);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cb_banner.getLayoutParams();
        /*params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        cb_banner.setLayoutParams(params); // 设置广告轮播条的布局参数*/
        cb_banner.setView(imageList);// 设置广告轮播条的广告图片列表
    }

    @Override
    protected void onStart() {
        super.onStart();
        cb_banner.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cb_banner.stop();
    }

    private void initImageList(){
        imageList = new ArrayList<>();
        imageList.add(R.mipmap.huawei);
        imageList.add(R.mipmap.iphone);
        imageList.add(R.mipmap.oppo);
        imageList.add(R.mipmap.rongyao);
        imageList.add(R.mipmap.vivo);
        imageList.add(R.mipmap.xiaomi);
    }
}