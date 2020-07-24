package com.jmx.standard;

import android.os.Bundle;

import com.delia.core.base.BaseCompatActivity;
import com.delia.demo.DemoActivity;

public class MainActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goIntent(DemoActivity.class, null);
    }
}