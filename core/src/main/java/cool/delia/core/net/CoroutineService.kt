/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * 用于Retrofit协程的Service
 *
 * @author xiong'MissDelia'zhengkun
 *
 * 2021/12/6 9:39
 */
interface CoroutineService {

//    @GET
//    operator fun get(
//        @Url url: String,
//        @QueryMap params: Map<String, Any>
//    ): Observable<JsonObject>
//
//    @FormUrlEncoded
//    @POST
//    fun post(@Url url: String, @FieldMap params: Map<String, Any>): Observable<JsonObject>

    @Headers("Content-type:application/json;charset=UTF-8")
    @POST
    suspend fun post(@Url url: String, @Body body: RequestBody): JsonObject

    @Headers("Content-type:application/json;charset=UTF-8")
    @POST
    suspend fun post(@Url url: String, @Body body: Map<String, @JvmSuppressWildcards Any>): JsonObject

//    @GET
//    operator fun get(
//        @Url url: String,
//        @QueryMap params: Map<String, Any>,
//        @HeaderMap headers: Map<String, Any>
//    ): Observable<JsonObject>
//
//    @Headers("Content-type:application/json;charset=UTF-8")
//    @POST
//    fun upload(@Url url: String, @Body body: Map<String, Any>): Observable<JsonObject>
}