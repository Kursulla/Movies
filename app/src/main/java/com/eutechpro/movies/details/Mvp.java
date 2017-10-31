package com.eutechpro.movies.details;


import com.eutechpro.movies.Movie;
import com.eutechpro.movies.MvpActivityCallback;

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

    interface View {
        /**
         * Bind Presenter to the View so we have bi-directional communication.
         *
         * @param presenter Presenter to bind to.
         */
        void bindPresenter(Presenter presenter);

        /**
         * Sometimes we need some action from an Activity. This is assignment to this callback.
         *
         * @param activityCallback Callback to reach Activity from a view.
         */
        void bindActivityCallback(MvpActivityCallback activityCallback);

        /**
         * Unbind all references so GC can do it's job.
         */
        void unBind();

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

    interface Presenter {
        /** We need to bind Presenter with a View in order to have Bi-Directional communication. */
        void bindView(View view);

        /**
         * Sometimes we need some action from an Activity. This is assignment to this callback.
         *
         * @param activityCallback Callback to reach Activity from a presenter.
         */
        void bindActivityCallback(MvpActivityCallback activityCallback);

        /** We have to release all references to the Views or subscriptions in order to prevent mem-leaks. */
        void unBind();

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

        void openImdbPage(String imdbId);
    }
}
