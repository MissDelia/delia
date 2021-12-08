/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 需要监控下载进度，使用这个Service
 * @author xiong'MissDelia'zhengkun
 * 2020/11/3 16:10
 */
public interface FileService {

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> body);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @Streaming
    @GET
    Call<ResponseBody> downloadNoRxJava(@Url String url, @QueryMap Map<String, Object> body);
}
