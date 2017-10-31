package com.eutechpro.movies.discovery;

import com.eutechpro.movies.Genre;
import com.eutechpro.movies.Movie;
import com.eutechpro.movies.TestConsumer;

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
    private static final String DEFAULT_SORT     = DiscoveryRepository.Sort.DEFAULT;
    private static final int    ANOTHER_YEAR     = 2020;
    private static final String ANOTHER_SORT     = DiscoveryRepository.Sort.POPULARITY_ASC;

    @Mock
    private DiscoveryRepository repository;
    private List<Movie>         movies;
    @Mock
    private Movie               movie1;
    @Mock
    private Movie               movie2;

    private TestConsumer<List<Movie>> testConsumer;
    private Mvp.Model                 model;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testConsumer = new TestConsumer<>();
        movies = new ArrayList<>(2);
        movies.add(movie1);
        movies.add(movie2);

        when(repository.discoverMovies(anyInt(), anyInt(), anyString(), anyInt())).thenReturn(Observable.just(movies));
        model = new Model(repository);
        model.getMoviesStream().subscribe(testConsumer);
    }

    @Test
    public void loadInitialData() throws Exception {
        model.loadInitialData();

        verify(repository).discoverMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }


    @Test
    public void filterByYear() throws Exception {
        model.filterByYear(ANOTHER_YEAR);
        verify(repository).discoverMovies(ANOTHER_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }

    @Test
    public void filterByGenre() throws Exception {
        Genre genre = mock(Genre.class);
        when(genre.getGenreId()).thenReturn(28);
        model.filterByGenre(genre);
        verify(repository).discoverMovies(DEFAULT_YEAR, 28, DEFAULT_SORT, DEFAULT_PAGE);
        verifyDataValidity(movies.size());
    }
    @Test
    public void changeSortOrder() throws Exception {
        //todo later
//        model.changeSortOrder(ANOTHER_SORT);
//        verify(repository).discoverMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, ANOTHER_SORT, DEFAULT_PAGE);
//        verifyDataValidity(movies.size());
    }

    @Test
    public void loadNextPage() throws Exception {
        //Given
        model.loadInitialData();
        verify(repository, times(1)).discoverMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE);

        //When
        model.loadNextPage();
        verify(repository, times(1)).discoverMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE + 1);
        model.loadNextPage();

        //Then
        verify(repository, times(1)).discoverMovies(DEFAULT_YEAR, DEFAULT_GENRE_ID, DEFAULT_SORT, DEFAULT_PAGE + 2);
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