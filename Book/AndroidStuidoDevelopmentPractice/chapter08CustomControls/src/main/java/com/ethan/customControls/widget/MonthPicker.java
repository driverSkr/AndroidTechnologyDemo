package com.ethan.customControls.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

//由日期选择器派生出月份选择器
/*此月份选择器只对下拉框生效，日历方式不行
* android:calendarViewShown="false"：表示不显示日历视图*/
public class MonthPicker extends DatePicker {

    public MonthPicker(Context context, AttributeSet attrs) {
        super(context,attrs);
        //获取年月日的下拉列表项
        ViewGroup vg = ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(0));
        if (vg.getChildCount() == 3){   //拥有3个下级视图
            //有的机型显示格式为“年月日”，此时隐藏第3个控件
            vg.getChildAt(2).setVisibility(View.GONE);
        }else if (vg.getChildCount() == 5){     //拥有5个下级视图
            //有的机型显示格式为“年|月|日”，此时隐藏第4个和第5个控件（即“|日”）
            vg.getChildAt(3).setVisibility(View.GONE);
            vg.getChildAt(4).setVisibility(View.GONE);
        }
    }
}
