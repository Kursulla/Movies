package com.eutechpro.movies.discovery;


import android.os.Bundle;

import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.details.MovieDetailsActivity;
import com.eutechpro.movies.mvp.MvpPresenterActivityCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

class Presenter implements Mvp.Presenter {
    private static final boolean KEEP_ACTIVITY = false;
    private       MvpPresenterActivityCallback activityCallback;
    private       Mvp.View                     view;
    private final Mvp.Model                    model;
    private final CompositeDisposable          compositeDisposable;

    public Presenter(Mvp.Model model) {
        this.model = model;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(Mvp.View view) {
        this.view = view;
        Disposable disposable = this.model.getMoviesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> {
                            view.drawMovies(movies);
                        },
                        throwable -> view.showError()
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void unBind() {
        this.view = null;
        this.compositeDisposable.clear();
    }

    @Override
    public void loadInitialData() {
        model.loadInitialData();
    }

    @Override
    public void loadMore() {
        model.loadNextPage();
    }

    @Override
    public void filterByYear(int year) {
        model.filterByYear(year);
    }

    @Override
    public void filterByGenre(Genre genre) {
        model.filterByGenre(genre);
    }
    @Override
    public void changeSortOrder(String sortType) {
        //tbd
    }

    @Override
    public void openMovieDetails(long movieId) {
        Bundle bundle = new Bundle();
        bundle.putLong(MovieDetailsActivity.MOVIE_ID_KEY, movieId);
        activityCallback.openActivity(bundle, MovieDetailsActivity.class, KEEP_ACTIVITY);
    }

    @Override
    public void bindActivityCallback(MvpPresenterActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }


}
