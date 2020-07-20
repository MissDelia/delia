/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.net.callback;

import com.jmx.library.net.data.Response;

/**
 * REST Api的回调类
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 15:56
 */
public interface OnRequestCompleteListener {

    void onComplete(Response<?> response);
}
