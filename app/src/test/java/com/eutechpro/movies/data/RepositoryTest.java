package com.eutechpro.movies.data;

import com.eutechpro.movies.TestConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.BaseTestConsumer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class RepositoryTest {
    private static final int    PAGE       = 11;
    private static final int    YEAR       = 2011;
    private static final int    GENRE_ID   = 28;
    private static final String SORT       = MoviesRepository.Sort.REVENUE_ASC;
    private static final String YEAR_KEY   = "primary_release_year";
    private static final String SORT_KEY   = "sort_by";
    private static final String PAGE_KEY   = "page";
    private static final String GENRES_KEY = "with_genres";
    @Mock
    private WebApi                            api;
    @Mock
    private RetrofitWebApi.Api.MoviesResponse response;
    @Mock
    private List<Movie>                       movies;
    @Mock
    private Movie                             movie;
    private MoviesRepository                  repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(api.fetchMovies(anyMap())).thenReturn(Observable.just(response));
        when(api.fetchMovieDetails(anyLong())).thenReturn(Single.just(movie));
        when(response.getResults()).thenReturn(movies);
        repository = new RetrofitMoviesRepository(api);
    }

    @Test
    public void testFetchingMovies() throws Exception {
        //Given
        TestConsumer<List<Movie>> testConsumer = new TestConsumer();
        //When
        repository.fetchMovies(YEAR, GENRE_ID, MoviesRepository.Sort.DEFAULT, PAGE).subscribe(testConsumer);

        //Then
        testConsumer.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_10MS, 500);
        assertEquals(movies, testConsumer.getContent());
    }

    @Test
    public void testFetchingDetails() throws Exception {
        //Given
        TestConsumer<Movie> testConsumer = new TestConsumer();

        //When
        repository.fetchMovieDetails(anyLong()).subscribe(testConsumer);

        //Then
        testConsumer.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_10MS, 500);

        assertEquals(movie, testConsumer.getContent());
    }

    @Test
    public void testParametersSetting() throws Exception {
        //When
        repository.fetchMovies(YEAR, GENRE_ID, SORT, PAGE);

        //Then
        ArgumentCaptor<Map<String, String>> captor = forClass(HashMap.class);
        verify(api).fetchMovies(captor.capture());

        Map<String, String> params = captor.getValue();
        assertEquals(String.valueOf(GENRE_ID), params.get(GENRES_KEY));
        assertEquals(String.valueOf(YEAR), params.get(YEAR_KEY));
        assertEquals(MoviesRepository.Sort.REVENUE_ASC, params.get(SORT_KEY));
        assertEquals(String.valueOf(PAGE), params.get(PAGE_KEY));
    }

}