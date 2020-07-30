/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.net;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 用于请求城市代码的REST Api的Service
 * @author xiong'MissDelia'zhengkun
 * 2020/7/27 9:12
 */
public interface RestCityService {

    @GET
    Observable<JsonObject> get(@Url String url);
}
