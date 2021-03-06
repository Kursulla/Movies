package com.eutechpro.movies.details;

import android.content.Intent;
import android.net.Uri;

import com.eutechpro.movies.mvp.MvpPresenterActivityCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

class Presenter implements Mvp.Presenter {
    private static final String  IMDB_URL      = "http://www.imdb.com/title/";
    private static final boolean KEEP_ACTIVITY = false;
    private final CompositeDisposable          compositeDisposable;
    private final Mvp.Model                    model;
    private       Mvp.View                     view;
    private       MvpPresenterActivityCallback activityCallback;

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
                        view::drawMovieDetails,
                        throwable -> view.showError()
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void bindActivityCallback(MvpPresenterActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
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

    @Override
    public void openHomePage(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(normaliseUrl(url)));
        activityCallback.openActivity(browserIntent, KEEP_ACTIVITY);
    }

    private String normaliseUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            return "http://" + url;
        }
        return url;
    }

    @Override
    public void openImdbPage(String imdbId) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(normaliseUrl(IMDB_URL + imdbId)));
        activityCallback.openActivity(browserIntent, KEEP_ACTIVITY);
    }
}
