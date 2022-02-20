package com.aircraft.codelab.labcore.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 2022-02-19
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface RetrofitService {
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @Streaming
    @GET("blog/2294494/202108/2294494-20210809123935702-486846456.png")
    Call<ResponseBody> downloadFileWithUrlAsync();
}
