/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.delia.core.exception.ApplicationInitialException;
import com.delia.core.handler.AppCrashHandler;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020年7月15日13:40:57
 * Application的基类
 */
public abstract class CoreApplication extends Application {

    // 全局Application实例
    private static Application application;

    // 接口基础地址，由顶层Application定义
    private static String BASE_URL;

    private static int TIME_OUT;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init();
        application = createApplication();
        BASE_URL = createBaseUrl();
        setTimeout(30);
        Utils.init(this);
    }

    protected abstract Application createApplication();

    protected abstract String createBaseUrl();

    /**
     * 子类可在onCreate中重新运行此方法修改Retrofit超时时间，单位为秒
     */
    protected void setTimeout(int timeout) {
        TIME_OUT = timeout;
    }

    public static Application getApplication() {
        if (application == null) {
            throw new ApplicationInitialException("You must implement BaseApplication first!");
        }
        return application;
    }

    public static String getBaseUrl() {
        if (BASE_URL == null) {
            throw new ApplicationInitialException("You must implement BaseApplication first!");
        }
        return BASE_URL;
    }

    public static int getTimeOut() {
        return TIME_OUT;
    }
}
