package com.ethan.customControls.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//不滚动的列表视图
/*扩展ListView在ScrollView中高度设为wrap_content时，只展示一行的情况*/
public class NoScrollListView extends ListView {

    public NoScrollListView(Context context) {super(context);}

    public NoScrollListView(Context context, AttributeSet attrs) {super(context, attrs);}

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写onMeasure方法，以便自行设定视图的高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 将高度设为最大值，即所有项加起来的总高度
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec); // 按照新的高度规格重新测量视图尺寸
    }
}
