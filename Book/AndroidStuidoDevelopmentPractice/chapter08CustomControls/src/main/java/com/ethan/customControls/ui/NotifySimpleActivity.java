package com.ethan.customControls.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ethan.customControls.CustomControlMainActivity;
import com.ethan.customControls.R;
import com.ethan.util.ViewUtil;

//发送一个简单的消息（包括消息标题、消息内容、小图标、大图标等基本信息）
public class NotifySimpleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title; // 声明一个编辑框对象
    private EditText et_message; // 声明一个编辑框对象

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_simple);

        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        findViewById(R.id.btn_send_simple).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_simple) {
            ViewUtil.hideOneInputMethod(this,et_message);// 隐藏输入法软键盘
            if (TextUtils.isEmpty(et_title.getText())){
                Toast.makeText(this,"请填写消息标题",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_message.getText())){
                Toast.makeText(this,"请填写消息内容",Toast.LENGTH_SHORT).show();
                return;
            }
            String title = et_title.getText().toString();
            String message = et_message.getText().toString();
            sendSimpleNotify(title,message); // 发送简单的通知消息
        }
    }

    // 发送简单的通知消息（包括消息标题和消息内容）
    private void sendSimpleNotify(String title, String message){
        // 发送消息之前要先创建通知渠道，创建代码见MainApplication.java
        // 创建一个跳转到活动页面的意图
        Intent clickIntent = new Intent(this, CustomControlMainActivity.class);
        // 创建一个用于页面跳转的延迟意图
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                R.string.book_name,clickIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // 创建一个通知消息的建造器
        // Android 8.0开始必须给每个通知分配对应的渠道
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, getString(R.string.book_name));
        }
        builder.setContentIntent(contentIntent)// 设置内容的点击意图
                .setAutoCancel(true)// 点击通知栏后是否自动清除该通知
                .setSmallIcon(R.mipmap.ic_app)// 设置应用名称左边的小图标
                .setSubText("这里是副本")// 设置通知栏里面的附加说明文本
                // 设置通知栏右边的大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_app))
                .setContentTitle(title)// 设置通知栏里面的标题文本
                .setContentText(message);// 设置通知栏里面的内容文本
        Notification notify = builder.build(); // 根据通知建造器构建一个通知对象
        // 从系统服务中获取通知管理器
        NotificationManager notifyMgr = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        // 使用通知管理器推送通知，然后在手机的通知栏就会看到该消息
        notifyMgr.notify(R.string.book_name, notify);
    }
}