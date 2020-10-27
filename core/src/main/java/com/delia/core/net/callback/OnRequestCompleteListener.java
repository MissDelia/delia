/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.net.callback;

/**
 * REST Api的回调类
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 15:56
 */
public interface OnRequestCompleteListener<T>  {

    void onComplete(T response);

    void onError(String message);
}
