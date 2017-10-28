package com.eutechpro.movies;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static final String BASE_URL      = "https://api.themoviedb.org/3/";
    private static final String API_KEY_KEY   = "api_key";//What a retarded name!
    private static final String API_KEY_VALUE = "b6e5d26b6b960dc0fc6b99744f9cecaf";

    /**
     * Create Retrofit service based on defined API.
     *
     * @param apiClass API class already defined.
     *
     * @return API ready to be used
     */
    public static <T> T buildService(@NonNull final Class<T> apiClass) {
        return buildRetrofit().create(apiClass);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addInterceptor(new ApiKeyInterceptor());
        builder.client(httpClientBuilder.build());

        return builder.build();//Build retrofit
    }

    private static class ApiKeyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original        = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY_KEY, API_KEY_VALUE)
                    .build();

            Request.Builder requestBuilder = original.newBuilder().url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
