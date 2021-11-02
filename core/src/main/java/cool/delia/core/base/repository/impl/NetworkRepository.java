/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base.repository.impl;

import androidx.annotation.NonNull;

import cool.delia.core.CoreApplication;
import cool.delia.core.base.repository.IBaseRepository;
import cool.delia.core.net.RestApiHolder;
import cool.delia.core.net.callback.DownloadProgressListener;
import cool.delia.core.net.callback.OnRequestCompleteListener;
import cool.delia.core.utils.LogUtil;
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
 * @author xiong'MissDelia'zhengkun
 * 2020/12/10 10:18
 */
public class NetworkRepository implements IBaseRepository {

    private static final int MAX_BUFFER_SIZE = 4096;

    private static NetworkRepository mInstance;

    private NetworkRepository() {

    }

    public synchronized static NetworkRepository getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkRepository();
        }
        return mInstance;
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
}
