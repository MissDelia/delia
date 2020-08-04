/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import android.os.Bundle;

/**
 * 基础MVP的Activity
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseViewActivity<V extends BaseView, P extends  BasePresenter<V>>
        extends BaseCompatActivity {

    private P presenter;
    private V view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (presenter != null && view != null) {
            presenter.bindView(view);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null && view != null) {
            presenter.detachView();
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public abstract P createPresenter();
    public abstract V createView();

}
