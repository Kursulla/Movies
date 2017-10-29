package com.eutechpro.movies.discovery;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.RetrofitFactory;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

@SuppressWarnings("unused")
class RetrofitApi implements DiscoveryApi {
    private Api api;

    RetrofitApi() {
        this.api = RetrofitFactory.buildService(Api.class);
    }

    @Override
    public Observable<Api.Response> fetchMovies(Map<String, String> parameters) {
        return api
                .fetchMovies(parameters)
                .subscribeOn(Schedulers.io());
    }

    /** Interface required by Retrofit. It will be used by Retrofit to generate appropriate code in compile time. */
    interface Api {
        @GET("discover/movie")
        Observable<Response> fetchMovies(@QueryMap Map<String, String> parameters);

        @SuppressWarnings("unused")
        class Response {
            private int  page;
            @SerializedName("total_result")
            private long totalResult;
            @SerializedName("total_pages")
            private int  totalPages;
            private List<Movie> results;

            List<Movie> getResults() {
                return results;
            }
        }
    }
}
