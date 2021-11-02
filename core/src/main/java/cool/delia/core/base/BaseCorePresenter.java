/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 核心库基础Presenter
 * @param <V>
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:33:44
 */
public abstract class BaseCorePresenter<V extends BaseView> {

    private V baseView;

    protected CompositeDisposable mDisposable;

    public BaseCorePresenter() {
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
