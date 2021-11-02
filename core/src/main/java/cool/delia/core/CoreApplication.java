/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import cool.delia.core.exception.ApplicationInitialException;
import cool.delia.core.handler.AppCrashHandler;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;

import okhttp3.Interceptor;

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

    public static boolean secureMode = false;

    public static boolean swSaveLog = false;

    public static String logName = "";

    public static String SHARED_TAG = "";

    /**
     * 全局置灰配置，可手动配置后推送apk或通过网络配置<br>
     * 在特殊节日、纪念日或其它需要的日期时使用
     */
    public static boolean globalGrayMode = false;

    /**
     * 拦截器
     */
    private static final ArrayList<Interceptor> interceptors = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init();
        application = createApplication();
        BASE_URL = createBaseUrl();
        setTimeout(10);
        Utils.init(this);
        MMKV.initialize(this);
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

    public synchronized static ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    /**
     * 用户可以选择在Application中注册自定义的各种拦截器
     * @param i 自定义的拦截器
     */
    public static void addInterceptor(Interceptor i) {
        interceptors.add(i);
    }
}
