package com.eutechpro.movies.discovery;


import android.util.Log;

import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.data.MoviesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    private static final String TAG = "Model";
    private final PublishSubject<List<Movie>> moviesStream;
    private final MoviesRepository            repository;
    private final List<Movie>                 fetchedMovies;
    private int page = 1;
    private int    year;
    private int    genreId;
    private String sortType;

    public Model(MoviesRepository repository) {
        this.repository = repository;
        this.moviesStream = PublishSubject.create();
        this.year = 0;
        this.sortType = MoviesRepository.Sort.DEFAULT;
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
        if (genre == null){
            this.genreId = 0;
        } else if (this.genreId == genre.getGenreId()){
            return;
        } else {
            this.genreId = genre.getGenreId();
        }

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
        repository.fetchMovies(year, genreId, sortType, page)
                .subscribe(
                        movies -> {
                            fetchedMovies.addAll(movies);
                            moviesStream.onNext(fetchedMovies);
                        },
                        throwable -> {
                            Log.e(TAG, "accept: ", throwable);
                            moviesStream.onError(new IllegalStateException("Unable to load next page: " + throwable.getMessage()));
                });
    }

    private void fetchMovies() {
        repository.fetchMovies(year, genreId, sortType, page)
                .subscribe(
                        movies -> {
                            fetchedMovies.clear();
                            fetchedMovies.addAll(movies);
                            moviesStream.onNext(fetchedMovies);
                        }, throwable -> {
                            Log.e(TAG, "accept: ", throwable);
                            moviesStream.onError(new IllegalStateException("Unable to load movies: " + throwable.getMessage()));
                        });
    }
}
