/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.net;

import com.jmx.library.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用单例模式持有Retrofit和RestService实例（运行时被动创建实例）
 * 2020年7月16日14:19:37
 * @author xiong'MissDelia'zhengkun
 */
public final class RestApiHolder {

    private static final class RetrofitHolder {

        private static Retrofit RETROFIT_CLIENT;

        private synchronized static Retrofit getRetrofitClient() {
            if (RETROFIT_CLIENT == null) {
                RETROFIT_CLIENT=new Retrofit.Builder()
                        .baseUrl(BaseApplication.getBaseUrl())
                        .client(OKHttpHolder.getOkHttpClient())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return RETROFIT_CLIENT;
        }
    }

    /**
     * 构建OKHttpClient
     */
    private static final class OKHttpHolder{

        private static OkHttpClient OK_HTTP_CLIENT;

        private synchronized static OkHttpClient getOkHttpClient() {
            if (OK_HTTP_CLIENT == null) {
                OK_HTTP_CLIENT = new OkHttpClient.Builder()
                        .connectTimeout(BaseApplication.getTimeOut(), TimeUnit.SECONDS)
                        .build();
            }
            return OK_HTTP_CLIENT;
        }
    }


    /**
     * 构建RestService
     */

    private static final class RestServiceHolder{
        private static RestService REST_SERVICE;

        private synchronized static RestService getRestService() {
            if (REST_SERVICE == null) {
                REST_SERVICE = RetrofitHolder.getRetrofitClient().create(RestService.class);
            }
            return REST_SERVICE;
        }
    }

    /**
     * 获取RestService实例
     */
    public static RestService getRestService() {

        return RestServiceHolder.getRestService();

    }
}
