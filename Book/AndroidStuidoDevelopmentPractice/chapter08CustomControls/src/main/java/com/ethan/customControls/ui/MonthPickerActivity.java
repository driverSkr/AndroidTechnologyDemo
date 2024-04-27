package com.ethan.customControls.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.MonthPicker;

public class MonthPickerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_month; // 声明一个文本视图对象
    private MonthPicker mp_month; // 声明一个月份选择器对象

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_picker);

        tv_month = findViewById(R.id.tv_month);
        // 从布局文件中获取名叫mp_month的月份选择器
        mp_month = findViewById(R.id.mp_month);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            // 获取月份选择器mp_month设定的年月
            String desc = String.format("您选择的月份是%d年%d月",
                    mp_month.getYear(), mp_month.getMonth() + 1);
            tv_month.setText(desc);
        }
    }
}