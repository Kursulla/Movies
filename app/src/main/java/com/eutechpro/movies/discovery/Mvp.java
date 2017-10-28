package com.eutechpro.movies.discovery;


import com.eutechpro.movies.Movie;

import java.util.List;

import io.reactivex.Observable;

interface Mvp {
    interface Model {
        /**
         * {@link android.database.Observable<List< com.eutechpro.movies.Movie>>} to listen on. <br/>
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
    }

    interface Presenter {
        /** We need to bind Presenter with a View in order to have Bi-Directional communication. */
        void bindView(View view);

        /** We have to release all references to the Views or subscriptions in order to prevent mem-leaks. */
        void unbind();
    }
}
