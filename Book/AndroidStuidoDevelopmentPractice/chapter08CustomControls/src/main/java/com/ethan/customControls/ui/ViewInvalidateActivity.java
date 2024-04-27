package com.ethan.customControls.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.OvalView;

//刷新UI
/*重新绘制视图界面的三种方法invalidate、postInvalidate、postInvalidateDelayed*/
public class ViewInvalidateActivity extends AppCompatActivity {
    private OvalView ov_validate; // 声明一个椭圆视图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invalidate);

        ov_validate = findViewById(R.id.ov_validate);
        initRefreshSpinner(); // 初始化刷新方式的下拉框
    }

    // 初始化刷新方式的下拉框
    private void initRefreshSpinner(){
        ArrayAdapter<String> refreshAdapter = new ArrayAdapter<>(this,
                R.layout.item_select,refreshArray);
        Spinner sp_refresh = findViewById(R.id.sp_refresh);
        sp_refresh.setPrompt("请选择刷新方式"); // 设置下拉框的标题
        sp_refresh.setAdapter(refreshAdapter); // 设置下拉框的数组适配器
        sp_refresh.setSelection(0); // 设置下拉框默认显示第一项
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_refresh.setOnItemSelectedListener(new RefreshSelectedListener());
    }

    private final String[] refreshArray = {
            "主线程调用invalidate",
            "主线程调用postInvalidate",
            "延迟3秒后刷新",
            "分线程调用invalidate",
            "分线程调用postInvalidate"
    };

    class RefreshSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0){ // 主线程调用invalidate
                ov_validate.invalidate(); // 刷新视图（用于主线程）
            } else if (position == 1){
                // 主线程调用postInvalidate
                ov_validate.postInvalidate(); // 刷新视图（主线程和分线程均可使用）
            }else if (position == 2) {  // 延迟3秒后刷新
                ov_validate.postInvalidateDelayed(3000); // 延迟若干时间后再刷新视图
            } else if (position == 3) {  // 分线程调用invalidate
                // invalidate不是线程安全的，虽然下面代码在分线程中调用invalidate方法也没报错，但在复杂场合可能出错
                new Thread(() -> {
                    ov_validate.invalidate(); // 刷新视图（用于主线程）
                }).start();
            }else if (position == 4) {  // 分线程调用postInvalidate
                // postInvalidate是线程安全的，分线程中建议调用postInvalidate方法来刷新视图
                new Thread(() -> {
                    ov_validate.postInvalidate(); // 刷新视图（主线程和分线程均可使用）
                }).start();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}