/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.net;

import com.delia.core.CoreApplication;
import com.delia.core.R;
import com.delia.core.base.Repository;

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

        private static Retrofit CITY_RETROFIT_CLIENT;

        private synchronized static Retrofit getRetrofitClient() {
            if (RETROFIT_CLIENT == null) {
                RETROFIT_CLIENT=new Retrofit.Builder()
                        .baseUrl(CoreApplication.getBaseUrl())
                        .client(OKHttpHolder.getOkHttpClient())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return RETROFIT_CLIENT;
        }

        private synchronized static Retrofit getCityRetrofitClient() {
            if (CITY_RETROFIT_CLIENT == null) {
                CITY_RETROFIT_CLIENT = new Retrofit.Builder()
                        .baseUrl(CoreApplication.getApplication().getString(R.string.city_base))
                        .client(OKHttpHolder.getOkHttpClient())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return CITY_RETROFIT_CLIENT;
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
                        .connectTimeout(CoreApplication.getTimeOut(), TimeUnit.SECONDS)
                        .addInterceptor(Repository.getInterceptor())
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

        private static RestCityService CITY_REST_SERVICE;

        private synchronized static RestService getRestService() {
            if (REST_SERVICE == null) {
                REST_SERVICE = RetrofitHolder.getRetrofitClient().create(RestService.class);
            }
            return REST_SERVICE;
        }

        private synchronized static RestCityService getCityRestService() {
            if (CITY_REST_SERVICE == null) {
                CITY_REST_SERVICE = RetrofitHolder.getCityRetrofitClient().create(RestCityService.class);
            }
            return CITY_REST_SERVICE;
        }
    }

    /**
     * 获取RestService实例
     */
    public static RestService getRestService() {

        return RestServiceHolder.getRestService();

    }

    /**
     * 获取RestService实例
     */
    public static RestCityService getCityRestService() {

        return RestServiceHolder.getCityRestService();

    }
}
