package com.eutechpro.movies.discovery;


import com.eutechpro.movies.Movie;
import com.eutechpro.movies.MvpActivityCallback;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

class Presenter implements Mvp.Presenter {
    private MvpActivityCallback activityCallback;
    private Mvp.View            view;
    private Mvp.Model           model;
    private CompositeDisposable compositeDisposable;

    public Presenter(Mvp.Model model) {
        this.model = model;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(Mvp.View view) {
        this.view = view;
        Disposable disposable = this.model.getMoviesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        view.drawMovies(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
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
        //todo I need ActivityCallback here
    }

    @Override
    public void bindActivityCallback(MvpActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }

}
