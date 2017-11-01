package com.eutechpro.movies.details;

import com.eutechpro.movies.TestConsumer;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.data.MoviesRepository;

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
    private Mvp.Model        model;
    @Mock
    private MoviesRepository moviesRepository;
    @Mock
    private Movie            movie;

    private TestConsumer<Movie> testConsumer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testConsumer = new TestConsumer<>();
        model = new Model(moviesRepository);
        model.getMovieDetailsStream().subscribe(testConsumer);
    }

    @Test
    public void testLoadingInitData_once() throws Exception {
        //Given
        when(moviesRepository.fetchMovieDetails(MOVIE_ID)).thenReturn(Single.just(movie));

        //When
        model.loadInitialData(MOVIE_ID);

        //Then
        verify(moviesRepository).fetchMovieDetails(MOVIE_ID);
        testConsumer.assertNoErrors();
        testConsumer.assertNotComplete();
        assertEquals(movie, testConsumer.getContent());
    }

    @Test
    public void testLoadingInitData_twice() throws Exception {
        //Given
        when(moviesRepository.fetchMovieDetails(MOVIE_ID)).thenReturn(Single.just(movie));

        //When
        model.loadInitialData(MOVIE_ID);
        model.loadInitialData(MOVIE_ID);

        //Then
        verify(moviesRepository, times(1)).fetchMovieDetails(MOVIE_ID);

        testConsumer.assertNoErrors();
        testConsumer.assertNotComplete();
        assertEquals(movie, testConsumer.getContent());
    }
}