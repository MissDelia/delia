/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils;

import android.util.Log;

import cool.delia.core.BuildConfig;
import cool.delia.core.CoreApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志工具类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:49:21
 */
public class LogUtil {

    private static final String DEFAULT_TAG = CoreApplication.getApplication().getPackageName();

    private static LogUtil instance;

    public synchronized static LogUtil getInstance() {
        if (instance == null) {
            instance = new LogUtil();
        }
        return instance;
    }

    public void v(String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.V, DEFAULT_TAG, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.V, DEFAULT_TAG, msg);
            }
        }
    }

    public void i(String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.I, DEFAULT_TAG, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.I, DEFAULT_TAG, msg);
            }
        }
    }

    public void d(String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.D, DEFAULT_TAG, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.D, DEFAULT_TAG, msg);
            }
        }
    }

    public void w(String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.W, DEFAULT_TAG, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.W, DEFAULT_TAG, msg);
            }
        }
    }

    public void e(String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.E, DEFAULT_TAG, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.E, DEFAULT_TAG, msg);
            }
        }
    }

    public void v(String tag, String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.V, tag, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.V, tag, msg);
            }
        }
    }

    public void i(String tag, String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.I, tag, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.I, tag, msg);
            }
        }
    }

    public void d(String tag, String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.D, tag, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.D, tag, msg);
            }
        }
    }

    public void w(String tag, String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.W, tag, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.W, tag, msg);
            }
        }
    }

    public void e(String tag, String msg) {
        if (msg != null) {
            if (CoreApplication.swSaveLog) {
                print(Level.E, tag, msg);
            }
            if (BuildConfig.DEBUG) {
                log(Level.E, tag, msg);
            }
        }
    }

    private void log(Level l, String tag, String msg) {
        switch (l) {
            case V:
                Log.v(tag, generateLogcatText(msg));
                break;
            case I:
                Log.i(tag, generateLogcatText(msg));
                break;
            case D:
                Log.d(tag, generateLogcatText(msg));
                break;
            case W:
                Log.w(tag, generateLogcatText(msg));
                break;
            case E:
                Log.e(tag, generateLogcatText(msg));
                break;
        }
    }

    /**
     * 将日志打印入文件
     * 仍不稳定，仅供测试环境使用
     * @param l 日志级别
     * @param tag 日志的TAG
     * @param msg 日志内容
     */
    private synchronized void print(Level l, String tag, String msg) {
        if (CoreApplication.logName != null && !"".equals(CoreApplication.logName)) {
            File logFile = new File(CoreApplication.logName);
            try (PrintStream ps = new PrintStream(new FileOutputStream(logFile))) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
                ps.println(df.format(new Date(System.currentTimeMillis())) + "\t" + l.name() + "\t" + tag + "\t" + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前堆栈信息
     */
    private static String generateLogcatText(String msg) {
        try {
            final StackTraceElement[] traceElements = new Throwable().getStackTrace();
            StringBuilder taskName = new StringBuilder();
            if (traceElements.length > 3) {
                StackTraceElement traceElement = traceElements[3];
                taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(") -> ");
                taskName.append(traceElement.getMethodName()).append("() : ").append(msg);
            }
            return taskName.toString();
        } catch (Throwable throwable) {
            return msg;
        }
    }

    private enum Level {
        V, I, D, W, E
    }
}
