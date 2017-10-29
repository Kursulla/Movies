package com.eutechpro.movies.details;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

class Presenter implements Mvp.Presenter {
    private CompositeDisposable compositeDisposable;
    private Mvp.View            view;
    private Mvp.Model           model;

    public Presenter(Mvp.Model model) {
        this.model = model;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(Mvp.View view) {
        this.view = view;
        Disposable disposable = this.model
                .getMovieDetailsStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movie -> view.drawMovieDetails(movie),
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
    public void loadInitialData(long movieId) {
        model.loadInitialData(movieId);
    }
}
