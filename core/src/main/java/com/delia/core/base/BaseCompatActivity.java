/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ConvertUtils;
import com.delia.core.R;
import com.delia.core.util.ToastUtil;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Activity不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    protected CompositeDisposable mDisposable;

    protected ViewGroup toolBar, tools;

    protected View btnBack;

    protected ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        mDisposable = new CompositeDisposable();
        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    /**
     * 标题栏组件初始化
     * @param toolBarId include后定义的ID
     * @param isBack 是否显示返回键
     * @param isBiggerBackIcon 返回键图片大小
     * @param isTool 是否显示右上角菜单栏
     */
    protected void initToolBar(int toolBarId, boolean isBack, boolean isBiggerBackIcon, boolean isTool) {
        toolBar = findViewById(toolBarId);
        if (toolBar != null) {
            // 初始化返回键
            if (isBack) {
                if (isBiggerBackIcon) {
                    btnBack = findViewById(R.id.iv_bar_back);
                    ivBack = (ImageView) btnBack;
                } else {
                    btnBack = findViewById(R.id.ll_bar_back);
                    ivBack = findViewById(R.id.iv_bar_back_);
                }
                if (getIconBackId() != -1) {
                    ivBack.setImageResource(getIconBackId());
                }
                btnBack.setOnClickListener(v -> {
                    finish();
                });
                btnBack.setVisibility(View.VISIBLE);
            }
            LinearLayout llBarTitle = findViewById(R.id.ll_bar_title);
            ViewGroup.LayoutParams layoutParams
                    = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llBarTitle.addView(getBarCenterView(), layoutParams);
            if (isTool) {
                tools = findViewById(R.id.ll_bar_tool);
                tools.setVisibility(View.VISIBLE);
                ArrayList<View> barToolViews = getBarToolViews();
                for (View barToolView : barToolViews) {
                    tools.addView(barToolView);
                }
            }
            toolBar.setBackground(getBarBackground());
        }
    }

    /**
     * 获取标题栏返回键图片ID
     */
    protected int getIconBackId() {
        return -1;
    }

    protected View getBarCenterView() {
        TextView tvTitle = new TextView(this);
        tvTitle.setText(getString(R.string.title_bar_default_str));
        tvTitle.setTextSize(19);
        tvTitle.setTextColor(getResources().getColor(R.color.default_black));
        return tvTitle;
    }

    protected ArrayList<View> getBarToolViews() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = ConvertUtils.dp2px(48);
        layoutParams.width = ConvertUtils.dp2px(48);
        layout.setLayoutParams(layoutParams);

        ImageView iv = new ImageView(this);
        ViewGroup.LayoutParams ivLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.height = ConvertUtils.dp2px(24);
        ivLayoutParams.width = ConvertUtils.dp2px(24);
        iv.setLayoutParams(ivLayoutParams);
        iv.setImageResource(R.drawable.more);

        layout.addView(iv);
        ArrayList<View> list = new ArrayList<>();
        list.add(layout);
        return list;
    }

    protected Drawable getBarBackground() {
        return getResources().getDrawable(R.drawable.drawable_default_bar);
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
     * 获取页面布局ID
     */
    protected abstract int getViewId();

    /**
     * 载入初始化数据
     */
    protected abstract void loadData();

    /**
     * 初始化页面及控件
     */
    protected abstract void initView();

    /**
     * 将数据写入控件
     */
    protected abstract void attachData();

    /**
     * 提示内容
     *
     * @param msg 内容
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
    protected void goIntent(Class<? extends Activity> c, Bundle extras){
        Intent intent = new Intent(this, c);
        if (extras != null) intent.putExtras(extras);
        startActivity(intent);
    }

    protected void goIntent(Class<? extends Activity> c) {
        goIntent(c, null);
    }

}
