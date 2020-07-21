/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.handler.callback;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 17:08
 */
public interface OnAppCrashListener {

    void onCrash(Thread t, Throwable e);
}
