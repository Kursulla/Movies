package com.eutechpro.movies.details;

import com.eutechpro.movies.Movie;

import io.reactivex.Single;

/**
 * API for communication with "Movie Details" endpoints of TheMovieDb service.
 */
interface MovieDetailsApi {
    Single<Movie> fetchMovieDetails(long movieId);
}
