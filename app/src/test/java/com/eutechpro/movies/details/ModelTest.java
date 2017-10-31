package com.eutechpro.movies.details;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.TestConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ModelTest {
    private static final int MOVIE_ID = 111;
    private Mvp.Model model;
    @Mock
    private RetrofitApi api;
    @Mock
    private Movie       movie;

    private TestConsumer<Movie> testConsumer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testConsumer = new TestConsumer<>();
        model = new Model(api);
        model.getMovieDetailsStream().subscribe(testConsumer);
    }

    @Test
    public void testLoadingInitialData() throws Exception {
        when(api.fetchMovieDetails(MOVIE_ID)).thenReturn(Single.just(movie));

        model.loadInitialData(MOVIE_ID);

        verify(api).fetchMovieDetails(MOVIE_ID);
        testConsumer.assertNoErrors();
        testConsumer.assertNotComplete();
        assertEquals(movie, testConsumer.getContent());
    }

    @Test
    public void testItWontInitLoadMultipleTimes() throws Exception {
        when(api.fetchMovieDetails(MOVIE_ID)).thenReturn(Single.just(movie));

        model.loadInitialData(MOVIE_ID);
        model.loadInitialData(MOVIE_ID);

        verify(api, times(1)).fetchMovieDetails(MOVIE_ID);

        testConsumer.assertNoErrors();
        testConsumer.assertNotComplete();
        assertEquals(movie, testConsumer.getContent());
    }
}