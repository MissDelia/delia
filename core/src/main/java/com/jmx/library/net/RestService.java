/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.library.net;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

}
