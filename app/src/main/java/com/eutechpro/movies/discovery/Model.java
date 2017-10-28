package com.eutechpro.movies.discovery;


import com.eutechpro.movies.Movie;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    private PublishSubject<List<Movie>> moviesStream;
    private DiscoveryRepository         repository;
    private int                         page;
    private int                         year;
    private String                      sortType;

    public Model(DiscoveryRepository repository) {
        this.repository = repository;
        this.moviesStream = PublishSubject.create();
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.sortType = DiscoveryRepository.Sort.DEFAULT;
    }

    @Override
    public Observable<List<Movie>> getMoviesStream() {
        return moviesStream;
    }

    @Override
    public void loadInitialData() {
        fetchMovies();
    }

    @Override
    public void filterByYear(int year) {
        this.year = year;
        fetchMovies();
    }

    @Override
    public void changeSortOrder(String sortType) {
        this.sortType = sortType;
        fetchMovies();
    }

    @Override
    public void loadNextPage() {
        page++;
        fetchMovies();
    }

    private void fetchMovies() {
        repository.discoverMoviesByYear(year, sortType, page)
                .subscribe(movies -> moviesStream.onNext(movies));
    }
}
