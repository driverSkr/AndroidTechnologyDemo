package com.ethan.customControls.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.DrawRelativeLayout;

//使用下拉框选择展示DrawRelativeLayout自定义View
public class ShowDrawActivity extends AppCompatActivity {

    private DrawRelativeLayout drl_content; // 声明一个绘画布局对象
    private Button btn_center; // 声明一个按钮对象

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_draw);

        // 从布局文件中获取名叫drl_content的绘画布局
        drl_content= findViewById(R.id.drl_content);
        btn_center = findViewById(R.id.btn_center);
        initTypeSpinner(); // 初始化绘图方式的下拉框
    }

    // 初始化绘图方式的下拉框
    private void initTypeSpinner() {
        ArrayAdapter<String> drawAdapter = new ArrayAdapter<>(this,
                R.layout.item_select, descArray);
        Spinner sp_draw = findViewById(R.id.sp_draw);
        sp_draw.setPrompt("请选择绘图方式");
        sp_draw.setAdapter(drawAdapter);
        sp_draw.setOnItemSelectedListener(new DrawSelectedListener());
        sp_draw.setSelection(0);
    }

    private final String[] descArray = {"不画图", "画矩形", "画圆角矩形", "画圆圈", "画椭圆",
            "onDraw画叉叉", "dispatchDraw画叉叉"};
    private final int[] typeArray = {0, 1, 2, 3, 4, 5, 6};

    class DrawSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int type = typeArray[position];
            if (type == 5 || type == 6){
                btn_center.setVisibility(View.VISIBLE);
            } else {
                btn_center.setVisibility(View.GONE);
            }
            drl_content.setDrawType(type); // 设置绘图布局的绘制类型
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}