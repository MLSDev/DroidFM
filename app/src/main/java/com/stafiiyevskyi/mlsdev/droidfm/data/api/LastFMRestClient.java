package com.stafiiyevskyi.mlsdev.droidfm.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VKTrackResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackItemResponse;

import java.lang.reflect.Type;

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

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        service = null;
    }

    public static LastFMService getService() {
        if (service == null) {
            Interceptor interceptor = chain -> {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("format", "json")
                        .addQueryParameter("api_key", "c0cca0938e628d1582474f036955fcfa").build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            };
            HttpLoggingInterceptor interceptorLogging = new HttpLoggingInterceptor();
            interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptorLogging);
            builder.interceptors().add(interceptor);
            OkHttpClient client = builder.build();

            Gson gson = new GsonBuilder().registerTypeAdapter(VKTrackResponse.class, new VkTrackResponseDeserializer()).create();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(LastFMService.class);

            return service;
        } else {
            return service;
        }

    }

    private static class VkTrackResponseDeserializer implements JsonDeserializer<VKTrackResponse> {

        @Override
        public VKTrackResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject parentObjec = json.getAsJsonObject();
            JsonArray array = parentObjec.getAsJsonArray("response");
            if (array != null) {
                if (array.size() == 1) return new VKTrackResponse();
            } else {
                return new VKTrackResponse();
            }

            JsonObject object = array.get(1).getAsJsonObject();

            VKTrackResponse resultResponse = new VKTrackResponse();

            VkTrackItemResponse itemResponse = new VkTrackItemResponse();
            itemResponse.setDuration(object.getAsJsonPrimitive("duration").getAsInt());
            itemResponse.setUrl(object.getAsJsonPrimitive("url").getAsString());
            itemResponse.setArtist(object.getAsJsonPrimitive("artist").getAsString());
            itemResponse.setGenre(object.getAsJsonPrimitive("genre").getAsInt());
            itemResponse.setAid(object.getAsJsonPrimitive("aid").getAsInt());
            itemResponse.setTitle(object.getAsJsonPrimitive("title").getAsString());
            itemResponse.setOwnerId(object.getAsJsonPrimitive("owner_id").getAsInt());

            VkTrackItemResponse[] responses = {itemResponse};
            resultResponse.setResponse(responses);

            return resultResponse;
        }
    }
}
