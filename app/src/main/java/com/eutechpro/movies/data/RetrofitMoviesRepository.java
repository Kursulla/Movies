package com.eutechpro.movies.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class RetrofitMoviesRepository implements MoviesRepository {
    private static final String SORT_BY_KEY = "sort_by";
    private static final String YEAR_KEY    = "primary_release_year";
    private static final String GENRE_KEY   = "with_genres";
    private static final String PAGE_KEY    = "page";
    private final WebApi api;

    public RetrofitMoviesRepository(WebApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Movie>> fetchMovies(int year, int genreId, @Sort.Type String sortType, int page) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SORT_BY_KEY, sortType);
        if (year != 0){
            parameters.put(YEAR_KEY, String.valueOf(year));
        }
        if (genreId != 0){
            parameters.put(GENRE_KEY, String.valueOf(genreId));
        }
        parameters.put(PAGE_KEY, String.valueOf(page));

        return api.fetchMovies(parameters)
                .subscribeOn(Schedulers.io())
                .map(response -> response.getResults());
    }

    @Override
    public Single<Movie> fetchMovieDetails(long movieId) {
        return api
                .fetchMovieDetails(movieId)
                .subscribeOn(Schedulers.io());
    }
}
