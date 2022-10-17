package com.aircraft.codelab.pioneer.async;

import com.aircraft.codelab.pioneer.service.RetrofitService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

/**
 * 2022-02-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
public class RetrofitCallBackImpl implements RetrofitCallBackService {

    private final RetrofitService retrofitService;

    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .readTimeout(15000, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://img2020.cnblogs.com/")
                .client(okHttpClient)
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    @Override
    public void downLoadFile() {
        Call<ResponseBody> call = retrofitService.downloadFileWithUrlAsync();

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressWarnings("ConstantConditions")
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    long fileSize = body.contentLength();
                    InputStream inputStream = body.byteStream();
                    Path baseLocation = Paths.get("D:\\lab");
                    Path targetLocation = baseLocation.resolve("newFileName.png").normalize();
                    try {
                        Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void downloadFromUrl(String videoUrl, File destFile) {
        retrofitService.downloadFileWithDynamicUrlAsync("blog/2294494/202108/2294494-20210809123935702-486846456.png")
                .enqueue(new Callback<ResponseBody>() {
                    @EverythingIsNonNull
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try (InputStream is = response.body().byteStream();
                             BufferedInputStream bis = IOUtils.buffer(is);
                             BufferedOutputStream bos = IOUtils.buffer(new FileOutputStream(destFile))) {
                            IOUtils.copyLarge(bis, bos);
                            bos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @EverythingIsNonNull
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                    }
                });
    }
}
