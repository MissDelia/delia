/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.base;

import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jmx.library.util.ToastUtil;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Fragment不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatFragment extends Fragment {

    protected CompositeDisposable mDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable.size() > 0) {
            mDisposable.clear();
        }
    }

    /**
     * 提示内容
     *
     * @param msg
     */
    public void showCommon(String msg){
        ToastUtil.getInstance().showCommon(msg, Gravity.CENTER);
    }

    public void showCommonBottom(String msg){
        ToastUtil.getInstance().showCommon(msg);
    }
}
