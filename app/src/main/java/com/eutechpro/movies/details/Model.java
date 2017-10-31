package com.eutechpro.movies.details;

import com.eutechpro.movies.Movie;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

class Model implements Mvp.Model {
    private RetrofitApi           api;
    private PublishSubject<Movie> detailsStream;
    private Movie                 fetchedMovie;


    public Model(RetrofitApi api) {
        this.api = api;
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
        api.fetchMovieDetails(movieId).subscribe(new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) throws Exception {
                fetchedMovie = movie;
                detailsStream.onNext(fetchedMovie);
            }
        });
    }
}
