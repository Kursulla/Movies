package com.eutechpro.movies.discovery;


import android.os.Bundle;

import com.eutechpro.movies.MvpActivityCallback;
import com.eutechpro.movies.details.MovieDetailsActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

class Presenter implements Mvp.Presenter {
    private MvpActivityCallback activityCallback;
    private Mvp.View            view;
    private final Mvp.Model           model;
    private final CompositeDisposable compositeDisposable;

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
                        movies -> view.drawMovies(movies),
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
    public void changeSortOrder(String sortType) {
        model.changeSortOrder(sortType);
    }

    @Override
    public void openMovieDetails(long movieId) {
        Bundle bundle = new Bundle();
        bundle.putLong(MovieDetailsActivity.MOVIE_ID_KEY, movieId);
        activityCallback.openActivity(bundle, MovieDetailsActivity.class, false);
    }

    @Override
    public void bindActivityCallback(MvpActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }

}
