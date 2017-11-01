package com.eutechpro.movies.details;


import android.app.Activity;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.mvp.MvpPresenterActivityCallback;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

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
         * Bind this view with instance of {@link MvpViewActivityCallback}.
         * <br/>
         * That will enrich the View with possibilities of drawing different Android UI components
         *
         * @param activityCallback Instance of MvpActivityCallback
         */
        void bindActivityCallback(MvpViewActivityCallback activityCallback);

        /**
         * Unbind all things that might cause mem leaks.
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
         * We might need to reach to the {@link Activity} from a Presenter.
         *
         * @param activityCallback Callback to reach Activity from a presenter.
         */
        void bindActivityCallback(MvpPresenterActivityCallback activityCallback);

        /** Unbind all things that might cause mem leaks. */
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

        /**
         * Open default browser with IMDB page that has details about provided movie;
         *
         * @param url URL to open in default browser.
         */
        void openImdbPage(String imdbId);
    }
}
