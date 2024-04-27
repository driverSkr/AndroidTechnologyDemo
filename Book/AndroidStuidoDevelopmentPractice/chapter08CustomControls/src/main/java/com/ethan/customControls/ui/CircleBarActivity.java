package com.ethan.customControls.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.CircleBarView;
import com.ethan.util.LinearGradientUtil;

public class CircleBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_bar);

        CircleBarView circle_bar = findViewById(R.id.circle_bar);
        TextView text_progress = findViewById(R.id.text_progress);
        circle_bar.setTextView(text_progress);
        circle_bar.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float progressNum, float maxNum) {
                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                return decimalFormat.format(interpolatedTime * progressNum / maxNum * 100) + "%";
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil(Color.YELLOW,Color.RED);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
        //设置动画时间为3000毫秒，即3秒
        circle_bar.setProgressNum(80,3000);
    }
}