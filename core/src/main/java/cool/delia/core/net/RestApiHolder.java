/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net;

import cool.delia.core.CoreApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
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

        private static Retrofit DOWNLOAD_RETROFIT_CLIENT;

        private synchronized static Retrofit getRetrofitClient() {
            if (RETROFIT_CLIENT == null) {
                RETROFIT_CLIENT = new Retrofit.Builder()
                        .baseUrl(CoreApplication.getBaseUrl())
                        .client(OKHttpHolder.getOkHttpClient())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return RETROFIT_CLIENT;
        }

        private synchronized static Retrofit getDownloadRetrofitClient() {
            if (DOWNLOAD_RETROFIT_CLIENT == null) {
                DOWNLOAD_RETROFIT_CLIENT = new Retrofit.Builder()
                        .baseUrl(CoreApplication.getBaseUrl())
                        .callbackExecutor(Executors.newSingleThreadExecutor())
                        .build();
            }
            return DOWNLOAD_RETROFIT_CLIENT;
        }
    }

    /**
     * 构建OKHttpClient
     */
    private static final class OKHttpHolder{

        private static OkHttpClient OK_HTTP_CLIENT;

        private synchronized static OkHttpClient getOkHttpClient() {
            if (OK_HTTP_CLIENT == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .readTimeout(CoreApplication.getTimeOut(), TimeUnit.SECONDS)
                        .connectTimeout(CoreApplication.getTimeOut(), TimeUnit.SECONDS)
                        .writeTimeout(CoreApplication.getTimeOut(), TimeUnit.SECONDS);
                // 加入Application中定义的拦截器
                for (Interceptor interceptor : CoreApplication.getInterceptors()) {
                    builder.addInterceptor(interceptor);
                }
                OK_HTTP_CLIENT = builder
                        .build();
            }
            return OK_HTTP_CLIENT;
        }
    }


    /**
     * 构建RestService
     */

    private static final class RestServiceHolder {
        private static RestService REST_SERVICE;

        private static FileService FILE_SERVICE;

        private synchronized static RestService getRestService() {
            if (REST_SERVICE == null) {
                REST_SERVICE = RetrofitHolder.getRetrofitClient().create(RestService.class);
            }
            return REST_SERVICE;
        }

        private synchronized static FileService getFileService() {
            if (FILE_SERVICE == null) {
                FILE_SERVICE = RetrofitHolder.getDownloadRetrofitClient().create(FileService.class);
            }
            return FILE_SERVICE;
        }
    }

    /**
     * 获取RestService实例
     */
    public static RestService getRestService() {

        return RestServiceHolder.getRestService();
    }

    /**
     * 获取FileService实例
     */
    public static FileService getFileService() {

        return RestServiceHolder.getFileService();
    }
}
