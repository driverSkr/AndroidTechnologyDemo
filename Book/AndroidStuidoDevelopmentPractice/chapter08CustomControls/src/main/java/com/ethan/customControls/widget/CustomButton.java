package com.ethan.customControls.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.ethan.customControls.R;


//自定义Button：四个构造方法中，前两个必须实现，否则要么不能在代码中创建视图对象，要么不能在XML文件中添加视图节点
//样式优先级：style属性 > defStyleAttr > defStyleRes
@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    //在java代码中通过new关键字创建视图对象时，会调用这个构造方法
    public CustomButton(Context context){
        super(context);
    }

    //在XML文件中添加视图节点时，会调用这个构造方法
    public CustomButton(Context context, AttributeSet attrs){
        this(context,attrs, R.attr.customButtonStyle);//设置默认的样式属性
    }

    //在采取默认的样式属性时，会调用这个构造方法。如果defStyleAttr填0，则表示无样式资源
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        //super(context,attrs,defStyleAttr);
        this(context,attrs,0,R.style.CommonButton);
    }

    //在采取默认的样式资源时，会调用这个构造方法。如果defStyleRes填0，则表示无样式资源
    @SuppressLint("NewApi")
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
