package com.eutechpro.movies.discovery;


import android.util.Log;

import com.eutechpro.movies.Genre;
import com.eutechpro.movies.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    public static final String TAG = "Model";
    private final PublishSubject<List<Movie>> moviesStream;
    private final DiscoveryRepository         repository;
    private final List<Movie>                 fetchedMovies;
    private int page = 1;
    private int    year;
    private int    genreId;
    private String sortType;

    public Model(DiscoveryRepository repository) {
        this.repository = repository;
        this.moviesStream = PublishSubject.create();
        this.year = 0;
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
        if(this.year == year){
            return;
        }
        this.year = year;
        this.page = 1;
        fetchMovies();
    }

    @Override
    public void filterByGenre(Genre genre) {
        if (genre == null || this.genreId == genre.getGenreId()){
            return;
        }
        this.genreId = genre.getGenreId();
        this.page = 1;
        fetchMovies();
    }

    @Override
    public void changeSortOrder(String sortType) {//todo sorting now works only against Repository. It has to be made to work on already fetched data
        this.sortType = sortType;
    }

    @Override
    public void loadNextPage() {
        page++;
        repository.discoverMovies(year, genreId, sortType, page)
                .subscribe(movies -> {
                    fetchedMovies.addAll(movies);
                    moviesStream.onNext(fetchedMovies);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                        moviesStream.onError(new IllegalStateException("Unable to load next page: " + throwable.getMessage()));
                    }
                });
    }

    private void fetchMovies() {
        repository.discoverMovies(year, genreId, sortType, page)
                .subscribe(movies -> {
                    fetchedMovies.clear();
                    fetchedMovies.addAll(movies);
                    moviesStream.onNext(fetchedMovies);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                        moviesStream.onError(new IllegalStateException("Unable to load movies: " + throwable.getMessage()));
                    }
                });
    }
}
