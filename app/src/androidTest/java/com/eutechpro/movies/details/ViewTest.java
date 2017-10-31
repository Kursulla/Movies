package com.eutechpro.movies.details;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.Genre;
import com.eutechpro.movies.Movie;
import com.eutechpro.movies.MvpActivityCallback;
import com.eutechpro.movies.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.eutechpro.movies.matchers.ToolbarMatchers.withCollapsibleToolbarTitle;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ViewTest {
    private static final String                         TEST_TITLE            = "test_title";
    private static final String                         TEST_TAG_LINE         = "test_tag_line";
    private static final String                         TEST_OVERVIEW         = "test_overview";
    private static final double                         TEST_RATING           = 9.9;
    private static final Date                           TEST_RELEASE_DATE     = new Date(1509450240186L);
    private static final String                         EXPECTED_RELEASE_DATE = "Oktober, 2017";
    private static final String                         TEST_GENRE_1_NAME     = "test_genre_1";
    private static final String                         TEST_GENRE_2_NAME     = "test_genre_2";
    private static final String                         EXPECTED_GENRES       = "test_genre_1 test_genre_2 ";
    private static final int                            TEST_DURATION         = 123;
    private static final String                         EXPECTED_DURATION     = "02:03";
    private static final String                         TEST_HOMEPAGE         = "http://test_homepage";
    private static final String                         TEST_IMDB_ID          = "tt_test_imdb_id";
    @Rule
    public               ActivityTestRule<BaseActivity> activityTestRule      = new ActivityTestRule<>(BaseActivity.class);

    private Mvp.View            view;
    @Mock
    private Mvp.Presenter       presenter;
    @Mock
    private MvpActivityCallback callback;

    @Before
    public void setUp() throws Throwable {
        MockitoAnnotations.initMocks(this);

        activityTestRule.runOnUiThread(() ->
                view = new View(activityTestRule.getActivity())
        );
        view.bindPresenter(presenter);
        view.bindActivityCallback(callback);
    }


    @Test
    public void drawMovieDetails() throws Throwable {
        //Given
        Movie movie = generateMovie();

        //WHen
        activityTestRule.runOnUiThread(() ->
                view.drawMovieDetails(movie));

        //Then
        onView(isAssignableFrom(CollapsingToolbarLayout.class))
                .check(matches(withCollapsibleToolbarTitle(is(TEST_TITLE))));
        onView(withId(R.id.tagline)).check(matches(withText(TEST_TAG_LINE)));
        onView(withId(R.id.overview)).check(matches(withText(TEST_OVERVIEW)));
        onView(withId(R.id.rating)).check(matches(withText(String.valueOf(TEST_RATING))));
        onView(withId(R.id.year)).check(matches(withText(EXPECTED_RELEASE_DATE)));
        onView(withId(R.id.genres)).check(matches(withText(EXPECTED_GENRES)));
        onView(withId(R.id.duration)).check(matches(withText(EXPECTED_DURATION)));
        onView(withId(R.id.homepage_btn)).check(matches(isEnabled()));
        onView(withId(R.id.imdb_btn)).check(matches(isEnabled()));
    }

    @Test
    public void testDisablingButtonsIfUrls_null() throws Throwable {
        //Given
        Movie movie = generateMovie();
        when(movie.getHomepage()).thenReturn(null);
        when(movie.getImdbId()).thenReturn(null);

        //When
        activityTestRule.runOnUiThread(() ->
                view.drawMovieDetails(movie));

        //Then
        onView(withId(R.id.homepage_btn)).check(matches(not(isEnabled())));
        onView(withId(R.id.imdb_btn)).check(matches(not(isEnabled())));
    }

    @Test
    public void testDisablingButtonsIfUrls_empty() throws Throwable {
        //Given
        Movie movie = generateMovie();
        when(movie.getHomepage()).thenReturn("");
        when(movie.getImdbId()).thenReturn("");

        //When
        activityTestRule.runOnUiThread(() ->
                view.drawMovieDetails(movie));

        //Then
        onView(withId(R.id.homepage_btn)).check(matches(not(isEnabled())));
        onView(withId(R.id.imdb_btn)).check(matches(not(isEnabled())));
    }

    @NonNull
    private Movie generateMovie() {
        Movie movie = mock(Movie.class);
        when(movie.getTitle()).thenReturn(TEST_TITLE);
        when(movie.getTagLine()).thenReturn(TEST_TAG_LINE);
        when(movie.getOverview()).thenReturn(TEST_OVERVIEW);
        when(movie.getVoteAverage()).thenReturn(TEST_RATING);
        when(movie.getReleaseDate()).thenReturn(TEST_RELEASE_DATE);
        List<Genre> genres = new ArrayList<>();
        Genre       genre1 = mock(Genre.class);
        Genre       genre2 = mock(Genre.class);
        when(genre1.getGenreName()).thenReturn(TEST_GENRE_1_NAME);
        when(genre2.getGenreName()).thenReturn(TEST_GENRE_2_NAME);
        genres.add(genre1);
        genres.add(genre2);
        when(movie.getGenres()).thenReturn(genres);
        when(movie.getDuration()).thenReturn(TEST_DURATION);
        when(movie.getHomepage()).thenReturn(TEST_HOMEPAGE);
        when(movie.getImdbId()).thenReturn(TEST_IMDB_ID);
        return movie;
    }

    @Test
    public void showError() throws Exception {
        view.showError();
    }
}