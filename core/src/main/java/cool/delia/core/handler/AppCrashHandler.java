/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.handler;

import android.os.Process;

import androidx.annotation.NonNull;

import cool.delia.core.BuildConfig;
import cool.delia.core.handler.callback.OnAppCrashListener;
import cool.delia.core.utils.LogUtil;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 处理未捕获的异常用于调试或记录
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 16:55
 */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = AppCrashHandler.class.getSimpleName();

    private static final AppCrashHandler sInstance = new AppCrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private ArrayList<OnAppCrashListener> mListenerArray;

    private AppCrashHandler() {
    }

    public static AppCrashHandler getInstance() {
        return sInstance;
    }

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void addOnAppCrashListener(OnAppCrashListener listener) {
        if (mListenerArray == null) {
            mListenerArray = new ArrayList<>();
        }
        mListenerArray.add(listener);
    }

    /**
     * 当程序中有未被捕获的异常，系统将会调用这个方法
     *
     * @param t 出现未捕获异常的线程
     * @param e 得到异常信息
     */
    @Override
    public void uncaughtException(Thread t, @NonNull Throwable e) {
        // 这里运行通用处理方法
        LogUtil.getInstance().e(TAG, String.format(Locale.getDefault(), "出错的线程ID：%d，", t.getId()));
        // 这里运行上层提供的异常处理方法
        if (mListenerArray != null) {
            for (OnAppCrashListener mListener : mListenerArray) {
                mListener.onCrash(t, e);
            }
        }
        // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就自己结束自己
        if (!BuildConfig.DEBUG) {
            if (mDefaultCrashHandler != null) {
                mDefaultCrashHandler.uncaughtException(t, e);
            } else {
                Process.killProcess(Process.myPid());
            }
        }
    }
}
