package com.eutechpro.movies.discovery;

import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PresenterTest {
    private Mvp.Presenter presenter;
    @Mock
    private Mvp.Model     model;
    @Mock
    private Mvp.View      view;

    @Mock
    private Movie movie1;
    @Mock
    private Movie movie2;

    private List<Movie> movies;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());

        presenter = new Presenter(model);

        movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
    }

    @Test
    public void loadInitialData_OK() throws Exception {
        //Given
        when(model.getMoviesStream()).thenReturn(Observable.just(movies));
        presenter.bindView(view);

        //When
        presenter.loadInitialData();

        //Then
        verify(view, never()).showError();
        verify(view).drawMovies(movies);
    }

    @Test
    public void loadInitialData_Error() throws Exception {
        //Given
        when(model.getMoviesStream()).thenReturn(Observable.error(new IllegalArgumentException("Some exception")));
        presenter.bindView(view);

        //When
        presenter.loadInitialData();

        //Then
        verify(view).showError();
        verify(view, never()).drawMovies(movies);
    }

    @Test
    public void loadMore() throws Exception {
        //Given
        when(model.getMoviesStream()).thenReturn(Observable.just(movies));
        presenter.bindView(view);

        //When
        presenter.loadMore();

        //Then
        verify(model).loadNextPage();
        verify(view).drawMovies(movies);

    }

    @Test
    public void filterByYear() throws Exception {
        //Given
        when(model.getMoviesStream()).thenReturn(Observable.just(movies));
        presenter.bindView(view);

        //When
        presenter.filterByYear(2017);

        //Then
        verify(model).filterByYear(2017);
        verify(view).drawMovies(movies);
    }

    @Test
    public void filterByGenre() throws Exception {
        //Given
        when(model.getMoviesStream()).thenReturn(Observable.just(movies));
        presenter.bindView(view);
        Genre genre = mock(Genre.class);

        //When
        presenter.filterByGenre(genre);

        //Then
        verify(model).filterByGenre(genre);
        verify(view).drawMovies(movies);
    }

    @Test
    public void changeSortOrder() throws Exception {
        //todo later
//        //Given
//        when(model.getMoviesStream()).thenReturn(Observable.just(movies));
//        presenter.bindView(view);
//
//        //When
//        presenter.changeSortOrder(MoviesRepository.Sort.DEFAULT);
//
//        //Then
//        verify(model).changeSortOrder(anyString());
//        verify(view).drawMovies(movies);
    }

    @Test
    public void openMovieDetails() throws Exception {
        //todo
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
    }
}