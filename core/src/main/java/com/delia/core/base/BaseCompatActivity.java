/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.delia.core.util.ToastUtil;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Activity不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    protected CompositeDisposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    /**
     * 沉浸式状态栏
     */
    protected void useImmersiveBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 提示内容
     *
     * @param msg
     */
    protected void showCommon(String msg){
        ToastUtil.getInstance().showCommon(msg, Gravity.CENTER);
    }

    protected void showCommonBottom(String msg){
        ToastUtil.getInstance().showCommon(msg);
    }

    /**
     * 跳转UI页面
     * @param c 下一个UI类
     * @param extras 传递参数
     */
    protected void goIntent(Class<?> c, Bundle extras){
        Intent intent = new Intent(this, c);
        if (extras != null) intent.putExtras(extras);
        startActivity(intent);
    }

}
