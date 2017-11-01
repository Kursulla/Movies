package com.eutechpro.movies.details;

import android.content.Intent;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.eutechpro.movies.Movie;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class PresenterBrowserOpeningTest {
    @Mock
    private Mvp.View                view;
    @Mock
    private Mvp.Model               model;
    @Mock
    private Movie                   movie;
    @Mock
    private MvpViewActivityCallback callback;

    private Mvp.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new Presenter(model);
        presenter.bindActivityCallback(callback);
    }

    @Test
    public void testOpeningHomePage() throws Exception {
        presenter.openHomePage("test_url");
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        verify(callback).openActivity(captor.capture(), eq(false));

        Intent intent = captor.getValue();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(Uri.parse("http://test_url"), intent.getData());
    }
    @Test
    public void testOpeningImdb() throws Exception {
        presenter.openImdbPage("test_imdb_id");
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        verify(callback).openActivity(captor.capture(), eq(false));

        Intent intent = captor.getValue();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(Uri.parse("http://www.imdb.com/title/test_imdb_id"), intent.getData());
    }
}