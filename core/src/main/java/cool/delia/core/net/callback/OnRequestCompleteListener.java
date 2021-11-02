/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net.callback;

/**
 * REST Api的回调类
 * @author xiong'MissDelia'zhengkun
 * 2020/7/16 15:56
 */
public interface OnRequestCompleteListener<T>  {

    void onComplete(T response);

    void onError(String message);
}
