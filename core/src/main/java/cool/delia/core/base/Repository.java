/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import androidx.annotation.NonNull;

import cool.delia.core.CoreApplication;
import cool.delia.core.exception.CacheInitialException;
import cool.delia.core.net.RestApiHolder;
import cool.delia.core.net.callback.DownloadProgressListener;
import cool.delia.core.net.callback.OnRequestCompleteListener;
import cool.delia.core.utils.LogUtil;
import cool.delia.core.utils.SharedPreUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 框架目前使用Retrofit和MMKV作为数据持久层
 * 除Application的子类和未实现MVP的视图外，不直接调用此类作为数据源，而是通过Presenter进行数据的增删改查和二次处理
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 13:49
 */
public class Repository {

    private static final int MAX_BUFFER_SIZE = 4096;

    private static Repository instance;

    private Repository() {

    }

    public synchronized static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
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
     * 通用请求方法
     * @param url api地址
     * @param params 请求参数
     * @param listener 回调监听
     * @return 返回Disposable，用于取消Retrofit订阅的事件
     */
    public <T> Disposable getDataFromNetwork(String url, RequestBody params, OnRequestCompleteListener<T> listener)
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
     * 通用请求方法（直接返回JsonObject）
     * @param url api地址
     * @param params 请求参数
     * @param listener 回调监听
     * @return 返回Disposable，用于取消Retrofit订阅的事件
     */
    public Disposable getJsonFromNetwork(String url, Map<String, Object> params, OnRequestCompleteListener<JsonObject> listener)
            throws JsonParseException {
        if (params == null) {
            return RestApiHolder.getRestService().get(url, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonObject -> {
                        LogUtil.getInstance().d(jsonObject + "");
                        listener.onComplete(jsonObject);
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
                        listener.onComplete(jsonObject);
                    }, e -> {
                        LogUtil.getInstance().e(e.getMessage() + "");
                        listener.onError(e.getMessage());
                    });
        }
    }

    /**
     * 通用请求方法（直接返回JsonObject）
     * @param url api地址
     * @param params 请求参数
     * @param listener 回调监听
     * @return 返回Disposable，用于取消Retrofit订阅的事件
     */
    public Disposable getJsonFromNetwork(String url, RequestBody params, OnRequestCompleteListener<JsonObject> listener)
            throws JsonParseException {
        if (params == null) {
            return RestApiHolder.getRestService().get(url, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonObject -> {
                        LogUtil.getInstance().d(jsonObject + "");
                        listener.onComplete(jsonObject);
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
                        listener.onComplete(jsonObject);
                    }, e -> {
                        LogUtil.getInstance().e(e.getMessage() + "");
                        listener.onError(e.getMessage());
                    });
        }
    }

    /**
     * 使用Map参数（数据为Base64）上传图片（或文件）
     * 大小限制：1MByte以下
     * @param url api地址
     * @param params 请求参数
     * @param listener 回调监听
     * @return 返回Disposable，用于取消Retrofit订阅的事件
     */
    public Disposable uploadFileWithMapBody(String url, Map<String, Object> params, OnRequestCompleteListener<JsonObject> listener)
            throws JsonParseException {
        return RestApiHolder.getRestService().upload(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    LogUtil.getInstance().d(jsonObject + "");
                    listener.onComplete(jsonObject);
                }, e -> {
                    LogUtil.getInstance().e(e.getMessage() + "");
                    listener.onError(e.getMessage());
                });
    }

    /**
     * 使用Map参数（数据为Base64）上传图片（或文件）
     * 大小限制：1MByte以下
     * @param url api地址
     * @param params 请求参数
     */
    public Observable<JsonObject> uploadFileWithMapBody(String url, Map<String, Object> params)
            throws JsonParseException {
        return RestApiHolder.getRestService().upload(url, params);
    }

    /**
     * 带有进度监控的下载接口
     * @param downloadUrl 下载URL
     * @param listener 回调监听
     */
    public void downloadWithProgress(String downloadUrl, String fileName, DownloadProgressListener listener) {
        Call<ResponseBody> call = RestApiHolder.getFileService().downloadNoRxJava(downloadUrl, new HashMap<>());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //开始下载
                listener.onStart();
                //将Response写入到从磁盘中，详见下面分析
                //注意，这个方法是运行在子线程中的
                //创建文件
                ResponseBody body = response.body();
                if (body != null) {
                    File apk = new File(CoreApplication.getApplication().getExternalCacheDir() + File.separator + fileName);

                    try (InputStream inputStream = body.byteStream();
                         OutputStream outputStream = new FileOutputStream(apk)) {
                        byte[] fileReader = new byte[MAX_BUFFER_SIZE];
                        long fileSize = body.contentLength();
                        long fileSizeDownloaded = 0;
                        int len;
                        while ((len = inputStream.read(fileReader, 0, MAX_BUFFER_SIZE)) != -1) {
                            outputStream.write(fileReader, 0, len);
                            fileSizeDownloaded += len;
                            listener.onProgress((int) (100 * fileSizeDownloaded / fileSize));
                        }
                        outputStream.flush();
                        //下载完成，并返回保存的文件路径
                        listener.onFinish(apk.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.onFail("IOException");
                    }
                } else {
                    listener.onFail("BodyNullException");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                listener.onFail("网络错误：" + throwable.getMessage());
            }
        });
    }

    /**
     * 此方法和{@link #setDataIntoShared}方法将SharedPreUtil中的方法映射到Repository中
     * 交给上层调用，上层暂时禁止直接使用SharedPreUtil中的方法
     * 1、由于无法抽象所有基本数据类型且不暴露给其它类型，我直接根据基本数据类型重载了总共八个方法
     * @since V1.0.0
     *
     * 去掉了重载方法，在数据仓库层不再限制泛型为基本数据类型，我看哪个傻逼会传基本数据类型和String之外的类型进来
     * @since V1.3.1 2021年8月19日15:44:39
     */
    public <T> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.INSTANCE.getValue(key, def);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.INSTANCE.setValue(key, value);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
    }

    public boolean hasKeyInShared(String key) {
        try {
            return SharedPreUtil.INSTANCE.hasKey(key);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeKeyInShared(String key) {
        try {
            SharedPreUtil.INSTANCE.removeKey(key);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
    }
}
