package com.stafiiyevskyi.mlsdev.droidfm.data.api;

import android.os.Environment;

import com.stafiiyevskyi.mlsdev.droidfm.app.DroidFMApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleksandr on 20.04.16.
 */
public class LastFMRestClient {

    private static LastFMService service;

    private static String BASE_URL = "https://ws.audioscrobbler.com/2.0/";


    private LastFMRestClient() {
    }

    private static File getDirectory() {
        File file = new File(DroidFMApplication.getInstance().getExternalCacheDir(), "cache");

        return file;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        service = null;
    }

    public static LastFMService getService() {
        if (service == null) {
            Cache cache = new Cache(getDirectory(), 1024 * 1024 * 10);


            Interceptor interceptor = chain -> {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("format", "json")
                        .addQueryParameter("api_key", "c0cca0938e628d1582474f036955fcfa")
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            };
            Interceptor interceptorNetwork = chain -> {
                Request request = chain.request();
                request = request.newBuilder().addHeader("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0)).build();
                return chain.proceed(request);
            };
            HttpLoggingInterceptor interceptorLogging = new HttpLoggingInterceptor();
            interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptorLogging);
            builder.interceptors().add(interceptor);
            builder.addNetworkInterceptor(interceptorNetwork);
            builder.cache(cache);
            OkHttpClient client = builder.build();


            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(LastFMService.class);

            return service;
        } else {
            return service;
        }

    }
}
