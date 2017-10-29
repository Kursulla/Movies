package com.eutechpro.movies.discovery;

import android.util.Log;

import com.eutechpro.movies.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public class RetrofitRepository implements DiscoveryRepository {
    private static final String TAG         = "RetrofitRepository";
    private static final String SORT_BY_KEY = "sort_by";
    private static final String YEAR_KEY    = "year";
    private static final String PAGE_KEY    = "page";
    private final DiscoveryApi api;

    public RetrofitRepository(DiscoveryApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Movie>> discoverMoviesByYear(int year, @Sort.Type String sortType, int page) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SORT_BY_KEY, sortType);
        parameters.put(YEAR_KEY, String.valueOf(year));
        parameters.put(PAGE_KEY, String.valueOf(page));

        return api.fetchMovies(parameters)
                .map(response -> {
                    Log.d(TAG, "discoverMoviesByYear: ");
                    return response.getResults();
                });
    }
}
