package com.eutechpro.movies.discovery;

import android.support.annotation.StringDef;

import com.eutechpro.movies.Movie;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;

public interface DiscoveryRepository {
    Observable<List<Movie>> discoverMoviesByYear(int year, @Sort.Type String sortType, int page);

    interface Sort {
        @Retention(RetentionPolicy.SOURCE)
        @StringDef({DEFAULT, POPULARITY_ASC, POPULARITY_DESC, RELEASE_DATE_ASC, RELEASE_DATE_DESC, REVENUE_ASC, REVENUE_DESC, TITLE_ASC, TITLE_DESC, VOTE_AVERAGE_ASC, VOTE_AVERAGE_DESC, VOTE_COUNT_ASC, VOTE_COUNT_DESC})
        public @interface Type {
        }

        String DEFAULT = "popularity.asc";

        String POPULARITY_ASC    = "popularity.asc";
        String POPULARITY_DESC   = "popularity.desc";
        String RELEASE_DATE_ASC  = "release_date.asc";
        String RELEASE_DATE_DESC = "release_date.desc";
        String REVENUE_ASC       = "revenue.asc";
        String REVENUE_DESC      = "revenue.desc";
        String TITLE_ASC         = "original_title.asc";
        String TITLE_DESC        = "original_title.desc";
        String VOTE_AVERAGE_ASC  = "vote_average.asc";
        String VOTE_AVERAGE_DESC = "vote_average.desc";
        String VOTE_COUNT_ASC    = "vote_count.asc";
        String VOTE_COUNT_DESC   = "vote_count.desc";
    }
}
