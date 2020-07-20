/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.handler;

import android.content.Context;
import android.os.Process;

import androidx.annotation.NonNull;

import com.jmx.library.BuildConfig;
import com.jmx.library.handler.callback.OnAppCrashListener;
import com.jmx.library.util.LogUtil;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 处理未捕获的异常用于调试或记录
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 16:55
 */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = AppCrashHandler.class.getSimpleName();

    private static AppCrashHandler sInstance = new AppCrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private ArrayList<OnAppCrashListener> mListenerArray;
    private boolean shutdown = !BuildConfig.DEBUG;

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
    public void uncaughtException(Thread t, Throwable e) {
        // 这里运行通用处理方法
        LogUtil.getInstance().e(TAG, String.format(Locale.getDefault()
                , "出错的线程ID：%d，以下是异常堆栈信息：", t.getId()));
        e.getStackTrace();
        // 这里运行上层提供的异常处理方法
        if (mListenerArray != null) {
            for (OnAppCrashListener mListener : mListenerArray) {
                mListener.onCrash(t, e);
            }
        }
        // 如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            if (shutdown) {
                Process.killProcess(Process.myPid());
            }
        }
    }
}
