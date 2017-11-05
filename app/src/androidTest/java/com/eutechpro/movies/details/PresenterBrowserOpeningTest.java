package com.eutechpro.movies.details;

import android.content.Intent;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.eutechpro.movies.mvp.MvpPresenterActivityCallback;

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
    private static final String TEST_HOMEPAGE_URL     = "http://test_url";
    private static final String EXPECTED_HOMEPAGE_URL = "http://test_url";
    private static final String TEST_IMDB_ID          = "test_imdb_id";
    private static final String EXPECTED_IMDB_URL     = "http://www.imdb.com/title/test_imdb_id";
    @Mock
    private Mvp.Model                    model;
    @Mock
    private MvpPresenterActivityCallback callback;

    private Mvp.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new Presenter(model);
        presenter.bindActivityCallback(callback);
    }

    @Test
    public void testOpeningHomePage() throws Exception {
        presenter.openHomePage(TEST_HOMEPAGE_URL);
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        verify(callback).openActivity(captor.capture(), eq(false));

        Intent intent = captor.getValue();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(Uri.parse(EXPECTED_HOMEPAGE_URL), intent.getData());
    }
    @Test
    public void testOpeningImdb() throws Exception {
        presenter.openImdbPage(TEST_IMDB_ID);
        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);
        verify(callback).openActivity(captor.capture(), eq(false));

        Intent intent = captor.getValue();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(Uri.parse(EXPECTED_IMDB_URL), intent.getData());
    }
}