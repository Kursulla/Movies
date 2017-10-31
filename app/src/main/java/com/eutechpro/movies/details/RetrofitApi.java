package com.eutechpro.movies.details;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.RetrofitFactory;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SuppressWarnings("unused")
class RetrofitApi implements MovieDetailsApi {
    private Api api;

    RetrofitApi() {
        this.api = RetrofitFactory.buildService(Api.class);
    }

    @Override
    public Single<Movie> fetchMovieDetails(long movieId) {
        return api
                .fetchMovieDetails(movieId)
                .subscribeOn(Schedulers.io());
    }

    /** Interface required by Retrofit. It will be used by Retrofit to generate appropriate code in compile time. */
    interface Api {
        @GET("movie/{movie_id}")
        Single<Movie> fetchMovieDetails(@Path("movie_id") long movieId);

    }
}
