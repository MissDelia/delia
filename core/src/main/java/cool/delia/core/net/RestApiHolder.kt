/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net

import cool.delia.core.CoreApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 使用单例模式持有Retrofit和RestService实例（运行时被动创建实例）
 * 2020年7月16日14:19:37
 * @author xiong'MissDelia'zhengkun
 */
object RestApiHolder {
    /**
     * 获取RestService实例
     */
    @JvmStatic
    val restService: RestService
        get() = RestServiceHolder.restService

    /**
     * 获取FileService实例
     */
    @JvmStatic
    val fileService: FileService
        get() = RestServiceHolder.fileService

    /**
     * 获取CoroutineService实例
     */
    @JvmStatic
    val coroutineService: CoroutineService
        get() = RestServiceHolder.coroutineService

    private object RetrofitHolder {
        private var RETROFIT_CLIENT: Retrofit? = null
        private var DOWNLOAD_RETROFIT_CLIENT: Retrofit? = null

        @get:Synchronized
        val retrofitClient: Retrofit
            get() {
                if (RETROFIT_CLIENT == null) {
                    RETROFIT_CLIENT = Retrofit.Builder()
                        .baseUrl(CoreApplication.getBaseUrl())
                        .client(OKHttpHolder.okHttpClient)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return RETROFIT_CLIENT as Retrofit
            }

        @get:Synchronized
        val downloadRetrofitClient: Retrofit
            get() {
                if (DOWNLOAD_RETROFIT_CLIENT == null) {
                    DOWNLOAD_RETROFIT_CLIENT = Retrofit.Builder()
                        .baseUrl(CoreApplication.getBaseUrl())
                        .callbackExecutor(Executors.newSingleThreadExecutor())
                        .build()
                }
                return DOWNLOAD_RETROFIT_CLIENT as Retrofit
            }
    }

    /**
     * 构建OKHttpClient
     */
    private object OKHttpHolder {
        private var OK_HTTP_CLIENT: OkHttpClient? = null

        // 加入Application中定义的拦截器
        @get:Synchronized
        val okHttpClient: OkHttpClient
            get() {
                if (OK_HTTP_CLIENT == null) {
                    val builder = OkHttpClient.Builder()
                        .readTimeout(CoreApplication.getTimeOut().toLong(), TimeUnit.SECONDS)
                        .connectTimeout(CoreApplication.getTimeOut().toLong(), TimeUnit.SECONDS)
                        .writeTimeout(CoreApplication.getTimeOut().toLong(), TimeUnit.SECONDS)
                    // 加入Application中定义的拦截器
                    for (interceptor in CoreApplication.getInterceptors()) {
                        builder.addInterceptor(interceptor)
                    }
                    OK_HTTP_CLIENT = builder
                        .build()
                }
                return OK_HTTP_CLIENT as OkHttpClient
            }
    }

    /**
     * 构建RestService
     */
    private object RestServiceHolder {
        private var REST_SERVICE: RestService? = null
        private var FILE_SERVICE: FileService? = null
        private var COROUTINE_SERVICE: CoroutineService? = null

        @get:Synchronized
        val restService: RestService
            get() {
                if (REST_SERVICE == null) {
                    REST_SERVICE = RetrofitHolder.retrofitClient.create(
                        RestService::class.java
                    )
                }
                return REST_SERVICE as RestService
            }

        @get:Synchronized
        val fileService: FileService
            get() {
                if (FILE_SERVICE == null) {
                    FILE_SERVICE = RetrofitHolder.downloadRetrofitClient.create(
                        FileService::class.java
                    )
                }
                return FILE_SERVICE as FileService
            }

        val coroutineService: CoroutineService
            get() {
                if (COROUTINE_SERVICE == null) {
                    COROUTINE_SERVICE = RetrofitHolder.retrofitClient.create(
                        CoroutineService::class.java
                    )
                }
                return COROUTINE_SERVICE as CoroutineService
            }
    }
}