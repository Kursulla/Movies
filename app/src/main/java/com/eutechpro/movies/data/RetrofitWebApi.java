package com.eutechpro.movies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

@SuppressWarnings("unused")
public class RetrofitWebApi implements WebApi {
    private Api api;

    public RetrofitWebApi() {
        this.api = RetrofitFactory.buildService(Api.class);
    }

    @Override
    public Observable<Api.MoviesResponse> fetchMovies(Map<String, String> parameters) {
        return api
                .fetchMovies(parameters)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Movie> fetchMovieDetails(long movieId) {
        return api
                .fetchMovieDetails(movieId)
                .subscribeOn(Schedulers.io());
    }

    /** Interface required by Retrofit. It will be used by Retrofit to generate appropriate code in compile time. */
    public interface Api {
        @GET("discover/movie")
        Observable<MoviesResponse> fetchMovies(@QueryMap Map<String, String> parameters);

        @GET("movie/{movie_id}")
        Single<Movie> fetchMovieDetails(@Path("movie_id") long movieId);

        @SuppressWarnings("unused")
        class MoviesResponse {
            private int         page;
            @SerializedName("total_result")
            private long        totalResult;
            @SerializedName("total_pages")
            private int         totalPages;
            private List<Movie> results;

            List<Movie> getResults() {
                return results;
            }
        }
    }
}
