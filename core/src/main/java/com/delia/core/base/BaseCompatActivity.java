/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    /**
     * 对于定义的整形常量推荐使用十六进制
     * 默认的startActivityForResult的requestCode
     */
    protected static final int DEFAULT_REQUEST_CODE = -0x00000001;

    /**
     * 默认的onActivityResult常量，表示成功返回数据
     */
    protected static final int RESPONSE_SUCCESS = 0x00000100;

    /**
     * 默认的onActivityResult常量，表示无返回数据或返回数据存在问题
     */
    protected static final int RESPONSE_FAULT = 0x00000200;

    /**
     * 默认无效的flag
     */
    protected static final int DEFAULT_FLAGS = -0x00000001;

    protected CompositeDisposable mDisposable;

    protected ViewGroup toolBar, tools;

    protected View btnBack;

    protected ImageView ivBack;

    protected LinearLayout llBarTitle;

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

    protected void initToolBar(int toolBarId) {
        initToolBar(toolBarId, false, false, false);
    }

    protected void initToolBar(int toolBarId, boolean isBack) {
        initToolBar(toolBarId, isBack, false, false);
    }

    protected void initToolBar(int toolBarId, boolean isBack, boolean isTool) {
        initToolBar(toolBarId, isBack, false, isTool);
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
            llBarTitle = findViewById(R.id.ll_bar_title);
            llBarTitle.addView(getBarCenterView(), getLayoutParams());
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

    protected ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    protected boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
     * @param c 目标页面对应的Class
     */
    protected void goIntent(Class<? extends Activity> c) {
        goIntent(c, null, DEFAULT_FLAGS, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param extras 要传的值
     */
    protected void goIntent(Class<? extends Activity> c, Bundle extras){
        goIntent(c, extras, DEFAULT_FLAGS, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param flags 跳转的flags
     */
    protected void goIntent(Class<? extends Activity> c, int flags){
        goIntent(c, null, flags, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param extras 要传的值
     * @param flags 跳转的flags
     */
    protected void goIntent(Class<? extends Activity> c, Bundle extras, int flags, int requestCode) {
        Intent intent = new Intent(this, c);
        if (extras != null) intent.putExtras(extras);
        if (flags != DEFAULT_FLAGS) intent.addFlags(flags);
        startActivityForResult(intent, requestCode);
    }

}
