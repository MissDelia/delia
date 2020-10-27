/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.demo;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delia.core.base.BaseViewActivity;
import com.delia.core.utils.StatusBarUtil;

/**
 * MVP使用教程
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 15:51
 */
public class DemoActivity extends BaseViewActivity<IDemoView, DemoPresenter>
        implements IDemoView {

    @Override
    protected int getViewId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void loadData() {
        getPresenter().request();
    }

    @Override
    protected void initView() {
        // 设置沉浸式状态栏
        useImmersiveBar();
        // 沉浸时设置状态栏字体为深色
        StatusBarUtil.getInstance().statusBarLightMode(this);
        ImageView iv = findViewById(R.id.iv_bar_back);
        Glide.with(this)
                .load(R.mipmap.icon_back_white)
                .into(iv);
    }

    @Override
    protected void attachData() {

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
