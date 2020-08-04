package com.jmx.standard;

import android.os.Bundle;

import com.delia.core.base.BaseCompatActivity;

public class MainActivity extends BaseCompatActivity {

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        initToolBar(R.id.title_bar, true, false, true);
    }

    @Override
    protected void attachData() {

    }
}