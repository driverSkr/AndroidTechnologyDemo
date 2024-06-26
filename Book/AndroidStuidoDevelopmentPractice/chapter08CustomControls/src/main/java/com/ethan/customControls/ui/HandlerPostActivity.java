package com.ethan.customControls.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;

import java.util.Objects;

//计时器
/*计时器是Handler+Runnable组合的简单应用，每隔若千时间就刷新当前的计数值，使得界面上的数字持续跳跃*/
public class HandlerPostActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_count; // 声明一个按钮对象
    private TextView tv_result; // 声明一个文本视图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_post);

        btn_count = findViewById(R.id.btn_count);
        tv_result = findViewById(R.id.tv_result);
        btn_count.setOnClickListener(this); // 设置按钮的点击监听器
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_count){
            if (!isStarted){ // 不在计数，则开始计数
                btn_count.setText("停止计数");
                mHandler.post(mCounter); // 立即启动计数任务
            } else { // 已在计数，则停止计数
                btn_count.setText("开始计数");
                mHandler.removeCallbacks(mCounter); // 立即取消计数任务
            }
            isStarted = !isStarted;
        }
    }

    private boolean isStarted = false; // 是否开始计数
    private final Handler mHandler = new Handler(Objects.requireNonNull(Looper.myLooper())); // 声明一个处理器对象
    private int mCount = 0; // 计数值

    // 定义一个计数任务
    private final Runnable mCounter = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            mCount ++;
            tv_result.setText("当前计数值为：" + mCount);
            mHandler.postDelayed(this,1000); // 延迟一秒后重复计数任务
        }
    };
}