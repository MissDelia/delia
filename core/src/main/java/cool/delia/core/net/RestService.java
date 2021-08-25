/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * REST Api的请求方法定义
 * 目前仅用到get和post，其它REST api等待后续补充
 * @author xiong'Delia'zhengkun
 * 2020年7月16日16:02:35
 */
public interface RestService {

    @GET
    Observable<JsonObject> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<JsonObject> post(@Url String url, @FieldMap Map<String, Object> params);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST
    Observable<JsonObject> post(@Url String url, @Body RequestBody body);

    @GET
    Observable<JsonObject> get(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, Object> headers);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST
    Observable<JsonObject> upload(@Url String url, @Body Map<String, Object> body);
}
