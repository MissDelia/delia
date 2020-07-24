/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.base;

import com.delia.core.BuildConfig;
import com.delia.core.exception.SharedInitialException;
import com.delia.core.net.RestApiHolder;
import com.delia.core.net.callback.OnRequestCompleteListener;
import com.delia.core.net.data.Response;
import com.delia.core.util.LogUtil;
import com.delia.core.util.SharedPreUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 框架目前仅使用Retrofit和SharedPreferences作为数据持久层
 * {
 *     开发者注：由于通用性问题未解决，暂不加入数据库作为数据持久层
 *     TODO 我们将会在未来数个版本内提供数据库持久层选择
 * }
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 13:49
 */
public class Repository {

    private static Repository instance;

    public static HttpLoggingInterceptor interceptor;

    private Repository() {
        interceptor = new HttpLoggingInterceptor(message -> {
            try {
                if (BuildConfig.DEBUG) {
                    LogUtil.getInstance().i(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //包含header、body数据
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    /**
     * 用户可以选择在Application中注册自定义的Http日志拦截器，否则使用系统默认的日志拦截器
     * @param interceptor 自定义的日志拦截器
     */
    public void init(HttpLoggingInterceptor interceptor) {
        Repository.interceptor = interceptor;
    }

    /**
     * 通用请求方法
     * @param url api地址
     * @param params 请求参数
     * @param listener 回调监听
     * @return 返回Disposable，用于取消Retrofit订阅的事件
     */
    public <T> Disposable getDataFromNetwork(String url, Map<String, Object> params, OnRequestCompleteListener<T> listener)
            throws JsonParseException {
        if (params == null) {
            return RestApiHolder.getRestService().get(url, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonObject -> {
                        LogUtil.getInstance().d(jsonObject + "");
                        T bean = new Gson().fromJson(jsonObject
                                , new TypeToken<T>(){}.getType());
                        listener.onComplete(bean);
                    }, e -> {
                        LogUtil.getInstance().e(e.getMessage() + "");
                        listener.onError(e.getMessage());
                    });
        } else {
            return RestApiHolder.getRestService().post(url, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonObject -> {
                        LogUtil.getInstance().d(jsonObject + "");
                        T bean = new Gson().fromJson(jsonObject
                                , new TypeToken<T>(){}.getType());
                        listener.onComplete(bean);
                    }, e -> {
                        LogUtil.getInstance().e(e.getMessage() + "");
                        listener.onError(e.getMessage());
                    });
        }
    }

    /**
     * 此方法和{@link #setDataIntoShared}方法将SharedPreUtil中的方法映射到Repository中
     * 交给上层调用，上层暂时禁止直接使用SharedPreUtil中的方法
     * 1、由于无法抽象所有基本数据类型且不暴露给其它类型，我直接根据基本数据类型重载了总共八个方法
     */
    public <T extends Number> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.getInstance().getValue(key, def);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T extends CharSequence> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.getInstance().getValue(key, def);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T extends Character> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.getInstance().getValue(key, def);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T extends Boolean> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.getInstance().getValue(key, def);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T extends Number> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.getInstance().setValue(key, value);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
    }

    public <T extends CharSequence> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.getInstance().setValue(key, value);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
    }

    public <T extends Character> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.getInstance().setValue(key, value);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
    }

    public <T extends Boolean> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.getInstance().setValue(key, value);
        } catch (SharedInitialException e) {
            e.printStackTrace();
        }
    }
}
