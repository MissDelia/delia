/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.util;

import android.util.Log;

import com.delia.core.CoreApplication;
import com.delia.core.BuildConfig;

/**
 * 日志工具类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:49:21
 */
public class LogUtil {

    private static final String DEFAULT_TAG = CoreApplication.getApplication().getPackageName();

    private static LogUtil instance;

    private static int stackLevel = 3;

    /**
     * 如果LogUtil默认栈深度不正确，可以使用此方法调整栈深度，
     * @param level 传入的栈深度（默认栈深度为3）
     * @return LogUtil
     */
    public synchronized static LogUtil getInstance(int level) {
        if (instance == null) {
            instance = new LogUtil();
        }
        stackLevel = level;
        return instance;
    }

    public synchronized static LogUtil getInstance() {
        if (instance == null) {
            instance = new LogUtil();
        }
        stackLevel = 3;
        return instance;
    }

    public void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    public void v(String tag, String msg) {
        if(BuildConfig.DEBUG && msg != null) {
            Log.v(tag, generateLogcatText(Thread.currentThread().getStackTrace(), msg, msg));
        }
    }

    public void i(String tag, String msg) {
        if(BuildConfig.DEBUG && msg != null) {
            Log.i(tag, generateLogcatText(Thread.currentThread().getStackTrace(), msg, msg));
        }
    }

    public void d(String tag, String msg) {
        if(BuildConfig.DEBUG && msg != null) {
            Log.d(tag, generateLogcatText(Thread.currentThread().getStackTrace(), msg, msg));
        }
    }

    public void w(String tag, String msg) {
        if(BuildConfig.DEBUG && msg != null) {
            Log.w(tag, generateLogcatText(Thread.currentThread().getStackTrace(), msg, msg));
        }
    }

    public void e(String tag, String msg) {
        if(BuildConfig.DEBUG && msg != null) {
            Log.e(tag, generateLogcatText(Thread.currentThread().getStackTrace(), msg, msg));
        }
    }

    /**
     * 获取当前堆栈信息
     */
    private static String generateLogcatText(StackTraceElement[] traceElements, String msg, String message) {
        try {
            StringBuilder taskName = new StringBuilder();
            if (traceElements != null && traceElements.length > stackLevel) {
                StackTraceElement traceElement = traceElements[stackLevel];
                taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(") -> ");
                taskName.append(traceElement.getMethodName()).append("() : ").append(message);
            }
            return taskName.toString();
        } catch (Throwable throwable) {
            return msg;
        }
    }

}
