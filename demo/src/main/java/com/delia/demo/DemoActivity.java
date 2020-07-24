/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.demo;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delia.core.base.BaseViewActivity;
import com.delia.core.util.StatusBarUtil;

/**
 * MVP使用教程
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 15:51
 */
public class DemoActivity extends BaseViewActivity<IDemoView, DemoPresenter>
        implements IDemoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        // 设置沉浸式状态栏
        useImmersiveBar();
        // 沉浸时设置状态栏字体为深色
        StatusBarUtil.getInstance().statusBarLightMode(this);
        getPresenter().request();

        initView();
    }

    private void initView() {
        ImageView iv = findViewById(R.id.iv_bar_back);
        Glide.with(this)
                .load(R.mipmap.icon_back_white)
                .into(iv);
    }

    @Override
    public DemoPresenter createPresenter() {
        return new DemoPresenter();
    }

    @Override
    public IDemoView createView() {
        return this;
    }

    @Override
    public void isRequestSuccess(boolean state) {
        if (state) {
            showCommon("Request demo api successful!");
        } else {
            showCommon("Request demo api failed!");
        }
    }
}
