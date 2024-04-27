package com.ethan.customControls.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ethan.customControls.R;
import com.ethan.customControls.adapter.TabPagerAdapter;

import java.util.List;

/*自定义底部导航控件*/
@SuppressLint("NonConstantResourceId")
public class CustomBottomTabWidget extends LinearLayout implements View.OnClickListener {

    private LinearLayout llMenuHome;
    private LinearLayout llMenuNearby;
    private LinearLayout llMenuDiscover;
    private LinearLayout llMenuOrder;
    private LinearLayout llMenuMine;
    private ViewPager viewPager;

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;


    public CustomBottomTabWidget(Context context) {
        this(context,null,0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绑定视图
        LayoutInflater.from(context).inflate(R.layout.widget_custom_bottom_tab,this);
        //初始化绑定控件
        initBinding();
        //初始化点击
        initClick();
        //设置默认的选中项
        selectTab(MenuTab.HOME);
    }

    /*
     * 外部调用初始化，传入必要的参数
     * @param fm
     */
    public void init(FragmentManager fm,List<Fragment> fragmentList){
        mFragmentManager = fm;
        mFragmentList = fragmentList;
        initViewPager();
    }

    /**
     * 初始化 ViewPager
     */
    private void initViewPager(){
        TabPagerAdapter mAdapter = new TabPagerAdapter(mFragmentManager, mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                //将ViewPager与下面的tab关联起来
                switch (position){
                    case 1:
                        selectTab(MenuTab.NEARBY);
                        break;
                    case 2:
                        selectTab(MenuTab.DISCOVER);
                        break;
                    case 3:
                        selectTab(MenuTab.ORDER);
                        break;
                    case 4:
                        selectTab(MenuTab.MINE);
                        break;
                    default:
                        selectTab(MenuTab.HOME);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * 设置 Tab 的选中状态
     * @param tab 要选中的标签
     */
    public void selectTab(MenuTab tab){
        //先将所有tab取消选中，再单独设置要选中的tab
        unCheckedAll();
        switch (tab){
            case HOME:
                llMenuHome.setActivated(true);
                break;
            case NEARBY:
                llMenuNearby.setActivated(true);
                break;
            case DISCOVER:
                llMenuDiscover.setActivated(true);
                break;
            case ORDER:
                llMenuOrder.setActivated(true);
                break;
            case MINE:
                llMenuMine.setActivated(true);
        }
    }

    //让所有tab都取消选中
    private void unCheckedAll(){
        llMenuHome.setActivated(false);
        llMenuNearby.setActivated(false);
        llMenuDiscover.setActivated(false);
        llMenuOrder.setActivated(false);
        llMenuMine.setActivated(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_menu_home_page) {
            selectTab(MenuTab.HOME);
            //使ViewPager跟随tab点击事件滑动
            viewPager.setCurrentItem(0);
        } else if (id == R.id.ll_menu_nearby) {
            selectTab(MenuTab.NEARBY);
            viewPager.setCurrentItem(1);
        } else if (id == R.id.ll_menu_discover) {
            selectTab(MenuTab.DISCOVER);
            viewPager.setCurrentItem(2);
        } else if (id == R.id.ll_menu_order) {
            selectTab(MenuTab.ORDER);
            viewPager.setCurrentItem(3);
        } else if (id == R.id.ll_menu_mine) {
            selectTab(MenuTab.MINE);
            viewPager.setCurrentItem(4);
        }
    }

    /*
     * tab的枚举类型
     */
    public enum MenuTab {
        HOME,
        NEARBY,
        DISCOVER,
        ORDER,
        MINE
    }

    /*初始化绑定控件*/
    private void initBinding(){
        llMenuHome = findViewById(R.id.ll_menu_home_page);
        llMenuNearby = findViewById(R.id.ll_menu_nearby);
        llMenuDiscover = findViewById(R.id.ll_menu_discover);
        llMenuOrder = findViewById(R.id.ll_menu_order);
        llMenuMine = findViewById(R.id.ll_menu_mine);
        viewPager = findViewById(R.id.vp_tab_widget);
    }

    /*初始化绑定点击*/
    private void initClick(){
        llMenuHome.setOnClickListener(this);
        llMenuNearby.setOnClickListener(this);
        llMenuDiscover.setOnClickListener(this);
        llMenuOrder.setOnClickListener(this);
        llMenuMine.setOnClickListener(this);
    }
}
