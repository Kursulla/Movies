package com.eutechpro.movies.details;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.MvpActivityCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterTest {
    private static final int MOVIE_ID = 111;
    @Mock
    private Mvp.View            view;
    @Mock
    private Mvp.Model           model;
    @Mock
    private Movie               movie;
    @Mock
    private MvpActivityCallback callback;

    private Mvp.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
        presenter = new Presenter(model);
        presenter.bindActivityCallback(callback);
    }

    @Test
    public void testLoadingInitDetails_OK() throws Exception {
        when(model.getMovieDetailsStream()).thenReturn(Observable.just(movie));
        presenter.bindView(view);

        presenter.loadInitialData(MOVIE_ID);

        verify(view).drawMovieDetails(movie);
        verify(view, never()).showError();
    }

    @Test
    public void testLoadingInitDetails_error() throws Exception {
        when(model.getMovieDetailsStream()).thenReturn(Observable.error(new IllegalArgumentException("Some exception")));
        presenter.bindView(view);

        presenter.loadInitialData(MOVIE_ID);

        verify(view, never()).drawMovieDetails(movie);
        verify(view).showError();
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
    }
}