package com.eutechpro.movies.discovery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.R;
import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class ViewTest {
    private static final String                         EXPECTED_RATE       = "2.1";
    private static final String                         EXPECTED_TITLE      = "testTitle_1";
    private static final int                            EXPECTED_MOVIE_ID   = 1;
    private static final String                         SELECTED_YEAR       = "2016";
    private static final int                            EXPECTED_YEAR       = 2016;
    private static final String                         SELECTED_GENRE_NAME = "Action";
    private static final String                         EXPECTED_GENRE_NAME = "Action";
    @Rule
    public               ActivityTestRule<BaseActivity> activityTestRule    = new ActivityTestRule<>(BaseActivity.class);

    private Mvp.View                view;
    @Mock
    private Mvp.Presenter           presenter;
    @Mock
    private MvpViewActivityCallback callback;
    private List<Movie>             movies;

    @Before
    public void setUp() throws Throwable {
        MockitoAnnotations.initMocks(this);

        activityTestRule.runOnUiThread(() ->
                view = new View(activityTestRule.getActivity())
        );
        view.bindPresenter(presenter);
        view.bindActivityCallback(callback);

        movies = new ArrayList<>();
        movies.add(generateMovieEntity(1));
        movies.add(generateMovieEntity(2));
        movies.add(generateMovieEntity(3));
        movies.add(generateMovieEntity(4));
        movies.add(generateMovieEntity(5));
        movies.add(generateMovieEntity(6));
        movies.add(generateMovieEntity(7));
        movies.add(generateMovieEntity(8));
    }

    @Test
    public void testDrawingMovies() throws Throwable {
        //When
        activityTestRule.runOnUiThread(() -> view.drawMovies(movies));

        //Then
        onView(withText(EXPECTED_RATE)).check(matches(isDisplayed()));
        onView(withText(EXPECTED_TITLE)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpeningDetails() throws Throwable {
        //Given
        view.drawMovies(movies);

        //When
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(0, click()));

        //Then
        verify(presenter).openMovieDetails(EXPECTED_MOVIE_ID);
    }

    @Test
    public void testYearSelection() throws Exception {
        //Given
        view.drawMovies(movies);

        //When
        onView(withId(R.id.year_selector)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(SELECTED_YEAR))).perform(click());

        //Then
        verify(presenter).filterByYear(EXPECTED_YEAR);
    }

    @Test
    public void testGenreSelection() throws Exception {
        //Given
        view.drawMovies(movies);

        //When
        onView(withId(R.id.genres_selector)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(SELECTED_GENRE_NAME))).perform(click());

        //Then
        ArgumentCaptor<Genre> captor = ArgumentCaptor.forClass(Genre.class);
        verify(presenter).filterByGenre(captor.capture());
        assertEquals(EXPECTED_GENRE_NAME, captor.getValue().getGenreName());
    }

    @Test
    public void testInfiniteScroll() throws Throwable {//todo fck RecyclerView!!! resolve later
//        activityTestRule.runOnUiThread(() -> view.testDrawingMovies(movies));
//
//        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(8));
//
//        Thread.sleep(5000);
//        verify(presenter).loadMore();
    }


    private Movie generateMovieEntity(int index) {
        Movie movie = new Movie();
        movie.setId(index);
        movie.setOriginalTitle("testTitle_" + index);
        movie.setVoteAverage(1.1 + index);
        movie.setPosterPath("http://some_path.jpg");

        return movie;
    }
}