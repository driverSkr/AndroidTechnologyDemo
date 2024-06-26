package com.ethan.customControls.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.ethan.customControls.R;
import com.ethan.customControls.adapter.PlanetListAdapter;
import com.ethan.customControls.bean.Planet;
import com.ethan.customControls.widget.NoScrollListView;

//对比展示ListView和自定义的NoScrollListView在ScrollView中的情况
public class NoscrollListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noscroll_list);

        PlanetListAdapter adapter1 = new PlanetListAdapter(this, Planet.getDefaultList());
        // 从布局文件中获取名叫lv_planet的列表视图
        // lv_planet是系统自带的ListView，被ScrollView嵌套只能显示一行
        ListView lv_planet = findViewById(R.id.lv_planet);
        lv_planet.setAdapter(adapter1); // 设置列表视图的行星适配器
        lv_planet.setOnItemClickListener(adapter1);
        lv_planet.setOnItemLongClickListener(adapter1);
        PlanetListAdapter adapter2 = new PlanetListAdapter(this, Planet.getDefaultList());
        // 从布局文件中获取名叫nslv_planet的不滚动列表视图
        // nslv_planet是自定义控件NoScrollListView，会显示所有行
        NoScrollListView nslv_planet = findViewById(R.id.nslv_planet);
        nslv_planet.setAdapter(adapter2); // 设置不滚动列表视图的行星适配器
        nslv_planet.setOnItemClickListener(adapter2);
        nslv_planet.setOnItemLongClickListener(adapter2);
    }
}