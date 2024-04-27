package com.ethan.customControls.util;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MeasureUtil {

    //获取指定文本的宽度（其实就是长度）
    public static float getTextWidth(String text,float textSize){
        if (TextUtils.isEmpty(text)){
            return 0;
        }
        Paint paint = new Paint();  //创建一个画笔对象
        paint.setTextSize(textSize);    //设置画笔的文本大小
        return paint.measureText(text); //利用画笔丈量指定文本的宽度
    }

    //获取指定文本的高度
    public static float getTextHeight(String text,float textSize){
        Paint paint = new Paint();  //创建一个画笔对象
        paint.setTextSize(textSize);    //设置画笔的文本大小
        FontMetrics fm = paint.getFontMetrics();    //获取画笔默认字体的度量衡
        return fm.descent - fm.ascent;  //返回文本自身的高度
        //return fm.bottom - fm.top + fm.leading;   //返回文本所在行的行高（leading：行间距）
    }

    //计算指定线性布局的实际高度
    public static float getRealHeight(View child){
        LinearLayout lLayout = (LinearLayout) child;
        //获得线性布局的布局参数
        ViewGroup.LayoutParams params = lLayout.getLayoutParams();
        if (params == null){
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //获得布局参数里面的宽度规格
        int wdSpec = ViewGroup.getChildMeasureSpec(0,0,params.width);
        int htSpec;
        //高度大于0，说明这是明确的dp数值
        if (params.height > 0){
            //按照精确数值的情况计算高度规格
            htSpec = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        }else { // MATCH_PARENT=-1，WRAP_CONTENT=-2，所以二者都进入该分支
            //按照不确定的情况计算高度规则
            htSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        lLayout.measure(wdSpec,htSpec); //重新丈量线性布局的宽、高
        //获得并返回线性布局丈量之后的高度。调用getMeasuredWidth方法可获得宽度
        return lLayout.getMeasuredHeight();
    }
}
