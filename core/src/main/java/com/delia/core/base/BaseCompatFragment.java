/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.delia.core.util.ToastUtil;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Fragment不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatFragment extends Fragment {

    protected CompositeDisposable mDisposable;

    protected boolean isFirstLoad = true;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            lazyLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable.size() > 0) {
            mDisposable.clear();
        }
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {
        return getContentView().findViewById(id);
    }

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

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
