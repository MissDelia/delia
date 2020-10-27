/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 基础Presenter
 * @param <V>
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:33:44
 */
public abstract class BasePresenter<V extends BaseView> {

    /**
     * 查询成功
     */
    public static final String RESPONSE_CODE_NORMAL = "0000";

    /**
     * 查询失败
     */
    public static final String RESPONSE_CODE_FAILURE = "0001";

    /**
     * 结果为空
     */
    public static final String RESPONSE_CODE_EMPTY = "0002";

    /**
     * 登录过期
     */
    public static final String RESPONSE_CODE_VALIDATE = "0003";

    /**
     * 登录超时
     */
    public static final String RESPONSE_CODE_TIMEOUT = "0004";

    /**
     * 异常
     */
    public static final String RESPONSE_CODE_EXCEPTION = "9999";

    private V baseView;

    protected CompositeDisposable mDisposable;

    public BasePresenter() {
        mDisposable = new CompositeDisposable();
    }

    public void bindView(V baseView) {
        this.baseView = baseView;
    }

    public void detachView() {
        baseView = null;
        if (mDisposable.size() > 0) {
            mDisposable.clear();
        }
    }

    public V getView() {
        return baseView;
    }
}
