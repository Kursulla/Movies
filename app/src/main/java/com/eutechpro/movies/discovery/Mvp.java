package com.eutechpro.movies.discovery;


import android.app.Activity;

import com.eutechpro.movies.Genre;
import com.eutechpro.movies.Movie;
import com.eutechpro.movies.mvp.MvpPresenterActivityCallback;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

import java.util.List;

import io.reactivex.Observable;

interface Mvp {
    interface Model {
        /**
         * {@link Observable} of {@link List<Movie>}to listen on. <br/>
         * All changes in data will be published via this stream.
         */
        Observable<List<Movie>> getMoviesStream();

        /**
         * Ask model to load initial set of data.
         */
        void loadInitialData();

        /** Load next page of data. */
        void loadNextPage();

        /**
         * Filter movies by year of release.
         *
         * @param year Just a year in YYYY format.
         */
        void filterByYear(int year);

        /**
         * Filter movies by provided genre!
         *
         * @param genre Genre to filter by
         */
        void filterByGenre(Genre genre);

        /**
         * Change sort order of content. It can have one of predefined values.
         * //todo TBD what values. Use them from repo or create new ones.
         *
         * @param sortType Sort type.
         */
        void changeSortOrder(String sortType);
    }

    interface View {
        /** We need to bind View with a Presenter in order to have Bi-Directional communication. */
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
         * Draw movies in View.
         *
         * @param movies List of movies
         */
        void drawMovies(List<Movie> movies);

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

        void loadInitialData();

        void loadMore();

        void filterByYear(int year);

        void filterByGenre(Genre genre);

        void changeSortOrder(String sortType);

        void openMovieDetails(long movieId);
    }
}
