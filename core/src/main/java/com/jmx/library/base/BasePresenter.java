/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.base;

import android.content.Context;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 基础Presenter
 * @param <V>
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:33:44
 */
public abstract class BasePresenter<V extends BaseView> {

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
