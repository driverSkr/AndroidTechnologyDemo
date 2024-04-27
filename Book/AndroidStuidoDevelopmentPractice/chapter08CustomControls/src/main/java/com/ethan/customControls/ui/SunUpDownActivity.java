package com.ethan.customControls.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.SunUpDown;

@SuppressLint("WrongViewCast")
public class SunUpDownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_up_down);

        SunUpDown btn_sun_up_down = findViewById(R.id.circle_view);
        btn_sun_up_down.setProgressNum(3000);//设置动画时间为3000毫秒，即3秒
    }
}