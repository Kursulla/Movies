package com.eutechpro.movies.discovery;


import com.eutechpro.movies.Movie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    private final PublishSubject<List<Movie>> moviesStream;
    private final DiscoveryRepository         repository;
    private final List<Movie>                 fetchedMovies;
    private int page = 1;
    private int                         year;
    private String                      sortType;

    public Model(DiscoveryRepository repository) {
        this.repository = repository;
        this.moviesStream = PublishSubject.create();
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.sortType = DiscoveryRepository.Sort.DEFAULT;
        this.fetchedMovies = new ArrayList<>();
    }

    @Override
    public Observable<List<Movie>> getMoviesStream() {
        return moviesStream;
    }

    @Override
    public void loadInitialData() {
        if(fetchedMovies.isEmpty()){
            fetchMovies();
        }else{
            moviesStream.onNext(fetchedMovies);
        }
    }

    @Override
    public void filterByYear(int year) {
        this.year = year;
        fetchMovies();
    }

    @Override
    public void changeSortOrder(String sortType) {//todo sorting now works only against Repository. It has to be made to work on already fetched data
        this.sortType = sortType;
        fetchMovies();
    }

    @Override
    public void loadNextPage() {
        page++;
        repository.discoverMoviesByYear(year, sortType, page)
                .subscribe(movies -> {
                    fetchedMovies.addAll(movies);
                    moviesStream.onNext(fetchedMovies);
                });
    }

    private void fetchMovies() {
        repository.discoverMoviesByYear(year, sortType, page)
                .subscribe(movies -> {
                    fetchedMovies.clear();
                    fetchedMovies.addAll(movies);
                    moviesStream.onNext(fetchedMovies);
                });
    }
}
