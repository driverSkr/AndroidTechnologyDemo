package com.ethan.customControls.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ethan.customControls.R;
import com.ethan.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author: create by boge
 *@Createtime: 2023/9/8 16:36
 *@Description:波哥自定义轮播图
*/
public class CustomBanner extends RelativeLayout implements View.OnClickListener {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private Context mContext;
    private List<ImageView> mViewList = new ArrayList<>(); // 声明一个图像视图列表
    private int mInterval = 3000; // 轮播的时间间隔，单位毫秒

    public CustomBanner(@NonNull Context context) {
        this(context,null,0);
    }

    public CustomBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.widget_custom_banner, this);
        viewPager = findViewById(R.id.vp_banner);
        radioGroup = findViewById(R.id.rg_banner);
        findViewById(R.id.itn_back).setOnClickListener(this);
        findViewById(R.id.itn_forward).setOnClickListener(this);
    }

    //设置视图
    public void setView(List<Integer> imageList){
        int dip_15 = Utils.dip2px(mContext, 15);

        // 根据图片列表生成图像视图列表
        for (int i = 0; i < imageList.size(); i++) {
            Integer imageResId = imageList.get(i); // 获取图片的资源编号
            ImageView iv = new ImageView(mContext); // 创建一个图像视图对象
            iv.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageResId); // 设置图像视图的资源图片
            iv.setOnClickListener(this);
            mViewList.add(iv); // 往视图列表添加新的图像视图
        }
        // 设置翻页视图的图像适配器
        viewPager.setAdapter(new ImageAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                ((RadioButton)radioGroup.getChildAt(position)).setChecked(true);
            }
        });
        // 根据图片列表生成指示按钮列表
        for (int i = 0; i < imageList.size(); i++){
            RadioButton radio = new RadioButton(mContext); // 创建一个单选按钮对象
            radio.setLayoutParams(new RadioGroup.LayoutParams(dip_15, dip_15));
            radio.setButtonDrawable(R.drawable.indicator_selector); // 设置单选按钮的资源图片
            radioGroup.addView(radio); // 往单选组添加新的单选按钮
        }
        viewPager.setCurrentItem(0);// 设置翻页视图显示第一页
        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
    }

    public void start(){
        mHandler.postDelayed(mScroll,mInterval);// 延迟若干秒后启动滚动任务
    }

    public void stop(){
        mHandler.removeCallbacks(mScroll); // 移除滚动任务
    }

    private final Handler mHandler = new Handler(Looper.myLooper()); // 声明一个处理器对象
    // 定义一个广告滚动任务
    private final Runnable mScroll = new Runnable() {
        @Override
        public void run() {
            int index = viewPager.getCurrentItem() + 1; // 获得下一张广告图的位置
            if (index >= mViewList.size()){
                index = 0;
            }
            viewPager.setCurrentItem(index); // 设置翻页视图显示第几页
            mHandler.postDelayed(this,mInterval); // 延迟若干秒后继续启动滚动任务
        }
    };

    @Override
    public void onClick(View v) {
        int position = viewPager.getCurrentItem();// 获取翻页视图当前页面项的序号
        int id = v.getId();
        if (id == R.id.itn_back) {
            if (position == mViewList.size() - 1) position = 0;
            else position += 1;
            viewPager.setCurrentItem(position);
        } else if (id == R.id.itn_forward) {
            if (position == 0) position = mViewList.size() - 1;
            else position -= 1;
            viewPager.setCurrentItem(position);
        } else {
            Toast.makeText(mContext, "你点击了第" + (position + 1) + "个图片", Toast.LENGTH_SHORT).show();
        }
    }

    private class ImageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mViewList.size();
        }
        // 判断当前视图是否来自指定对象
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        // 从容器中销毁指定位置的页面
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }
        // 实例化指定位置的页面，并将其添加到容器中
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    }
}
