package com.eutechpro.movies.discovery;


import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.mvp.BaseMvpPresenter;
import com.eutechpro.movies.mvp.BaseMvpView;

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

    interface View extends BaseMvpView<Mvp.Presenter> {
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

    interface Presenter extends BaseMvpPresenter<Mvp.View> {

        void loadInitialData();

        void loadMore();

        void filterByYear(int year);

        void filterByGenre(Genre genre);

        void changeSortOrder(String sortType);

        void openMovieDetails(long movieId);
    }
}
