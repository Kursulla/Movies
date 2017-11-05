package com.eutechpro.movies.details;


import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.mvp.BaseMvpPresenter;
import com.eutechpro.movies.mvp.BaseMvpView;

import io.reactivex.Observable;

interface Mvp {
    interface Model {
        /** Observable for listening all changes data source. In this case, data source is {@link Movie}. */
        Observable<Movie> getMovieDetailsStream();

        /**
         * Load initial {@link Movie} details data.
         *
         * @param movieId ID of movie you want to get details from.
         */
        void loadInitialData(long movieId);
    }

    interface View extends BaseMvpView<Mvp.Presenter> {
        /**
         * Show details about fetched {@link Movie} data set
         *
         * @param movie Movie to show data about
         */
        void drawMovieDetails(Movie movie);

        /**
         * In case of some error, show some info to the user.
         */
        void showError();
    }

    interface Presenter extends BaseMvpPresenter<Mvp.View> {
        /**
         * Load initial data of targeted {@link Movie}
         *
         * @param movieId ID of a movie we want to load.
         */
        void loadInitialData(long movieId);

        /**
         * Open default browser with provided URL;
         *
         * @param url URL to open in default browser.
         */
        void openHomePage(String url);

        /**
         * Open default browser with IMDB page that has details about provided movie;
         *
         * @param imdbId IMDB id of a movie
         */
        void openImdbPage(String imdbId);
    }
}
