package com.eutechpro.movies.details;

import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.data.MoviesRepository;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    private final MoviesRepository      repository;
    private final PublishSubject<Movie> detailsStream;
    private Movie                 fetchedMovie;


    public Model(MoviesRepository repository) {
        this.repository = repository;
        this.detailsStream = PublishSubject.create();
    }

    @Override
    public Observable<Movie> getMovieDetailsStream() {
        return detailsStream;
    }

    @Override
    public void loadInitialData(long movieId) {
        if (fetchedMovie != null){
            detailsStream.onNext(fetchedMovie);
            return;
        }
        repository.fetchMovieDetails(movieId).subscribe(movie -> {
            fetchedMovie = movie;
            detailsStream.onNext(fetchedMovie);
        });
    }
}
