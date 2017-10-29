package com.eutechpro.movies.discovery;

import com.eutechpro.movies.Movie;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * API for communication with "Discovery" endpoints of TheMovieDb service.
 */
interface DiscoveryApi {
    /**
     * API call for fetching list of {@link Movie} searched by different parameters.<br/>
     * <b>Parameters</b> interesting for this use case are:
     * <ul>
     * <li><b>page</b>  <i>[1..1000]</i></li>
     * <li><b>sort_by</b>  <i>[One of values: popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc,
     * original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc]</i></li>
     * <li><b>year</b>  <i>[4 digit year:2017]</i></li>
     * </ul>
     *
     * @param parameters Map {@link Map <String,String>} that contains parameters mentioned above.
     *
     * @return Observable of {@link List <Movie>}
     */
    Observable<RetrofitApi.Api.Response> fetchMovies(Map<String, String> parameters);
}
