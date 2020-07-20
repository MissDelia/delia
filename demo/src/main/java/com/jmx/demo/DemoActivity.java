/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.demo;

import android.os.Bundle;

import com.jmx.library.base.BaseViewActivity;
import com.jmx.library.util.StatusBarUtil;

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
        // 设置沉浸式状态栏
        useImmersiveBar();
        // 沉浸时设置状态栏字体为深色
        StatusBarUtil.getInstance().statusBarLightMode(this);
        getPresenter().request();
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
