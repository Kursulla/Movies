package com.eutechpro.movies.discovery;

import com.eutechpro.movies.TestConsumer;
import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.data.MoviesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ModelTest {
    private static final int    DEFAULT_PAGE     = 1;
    private static final int    DEFAULT_YEAR     = 0;
    private static final int    DEFAULT_GENRE_ID = 0;
    private static final String DEFAULT_SORT     = MoviesRepository.Sort.DEFAULT;
    private static final int    ANOTHER_YEAR     = 2020;
    private static final String ANOTHER_SORT     = MoviesRepository.Sort.POPULARITY_ASC;

    @Mock
    private MoviesRepository          repository;
    private List<Movie>               movies;
    @Mock
    private Movie                     movie1;
    @Mock
    private Movie                     movie2;
    private TestConsumer<List<Movie>> testConsumer;
    private Mvp.Model                 model;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testConsumer = new TestConsumer<>();
        movies = new ArrayList<>(2);
        movies.add(movie1);
        movies.add(movie2);

        when(repository.fetchMovies(anyInt(), anyInt(), anyString(), anyInt())).thenReturn(Observable.just(movies));
        model = new Model(repository);
        model.getMoviesStream().subscribe(testConsumer);
    }

    @Test
    public void testLoadInitialData_once() throws Exception {
        //When
        model.loadInitialData();

        //Then
        verify(repository).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }

    @Test
    public void testLoadInitialData_twice() throws Exception {
        //When
        model.loadInitialData();
        model.loadInitialData();

        //Then
        verify(repository, times(1)).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }

    @Test
    public void filterByYear() throws Exception {
        //When
        model.filterByYear(ANOTHER_YEAR);

        //Then
        verify(repository).fetchMovies(ANOTHER_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }

    @Test
    public void filterByGenre() throws Exception {
        //Given
        Genre genre = mock(Genre.class);
        when(genre.getGenreId()).thenReturn(28);

        //When
        model.filterByGenre(genre);

        //Then
        verify(repository).fetchMovies(DEFAULT_YEAR, 28, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }
    @Test
    public void changeSortOrder() throws Exception {
        //todo later
//        model.changeSortOrder(ANOTHER_SORT);
//        verify(repository).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, ANOTHER_SORT, DEFAULT_PAGE);
//        verifyDataValidity(movies.size());
    }

    @Test
    public void loadNextPage() throws Exception {
        //Given
        model.loadInitialData();
        verify(repository, times(1)).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);

        //When
        model.loadNextPage();
        verify(repository, times(1)).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE + 1);
        model.loadNextPage();

        //Then
        verify(repository, times(1)).fetchMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE + 2);
        verifyDataValidity(6);
    }

    private void verifyDataValidity(int size) {
        testConsumer.assertNotComplete();
        testConsumer.assertNoErrors();
        assertEquals(size, testConsumer.getContent().size());
        assertEquals(movie1, testConsumer.getContent().get(0));
        assertEquals(movie2, testConsumer.getContent().get(1));
    }
}