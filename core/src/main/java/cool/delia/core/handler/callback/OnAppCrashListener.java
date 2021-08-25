/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.handler.callback;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 17:08
 */
public interface OnAppCrashListener {

    void onCrash(Thread t, Throwable e);
}
