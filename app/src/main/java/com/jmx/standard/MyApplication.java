package com.jmx.standard;

import android.app.Application;
import android.os.Environment;

import com.delia.core.CoreApplication;
import com.delia.core.handler.AppCrashHandler;
import com.delia.core.utils.LogUtil;

/**
 * 用于测试的Application
 * 2020年7月16日14:23:10
 * 若2020年7月20日仍未删除此类，请勿使用
 * @author xiong'MissDelia'zhengkun
 */
public class MyApplication extends CoreApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setTimeout(10);
        // 可以在此处添加未知异常捕获的处理方法
        AppCrashHandler.getInstance().addOnAppCrashListener((t, e) ->
                LogUtil.getInstance().v(Environment.getDataDirectory().getAbsolutePath()));
    }

    @Override
    protected Application createApplication() {
        return this;
    }

    @Override
    protected String createBaseUrl() {
        return getString(R.string.baseURL);
    }
}
