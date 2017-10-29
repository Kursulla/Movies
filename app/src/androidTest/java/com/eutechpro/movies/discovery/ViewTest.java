package com.eutechpro.movies.discovery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.eutechpro.movies.DrawerActivity;
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
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class ViewTest {
    @Rule
    public ActivityTestRule<DrawerActivity> activityTestRule = new ActivityTestRule<>(DrawerActivity.class);

    private Mvp.View            view;
    @Mock
    private Mvp.Presenter       presenter;
    @Mock
    private MvpActivityCallback callback;
    private List<Movie>         movies;

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
    public void drawMovies() throws Throwable {

        activityTestRule.runOnUiThread(() -> view.drawMovies(movies));

        onView(withText("2.1")).check(matches(isDisplayed()));
        onView(withText("testTitle_1")).check(matches(isDisplayed()));
    }

    @Test
    public void testOpeningDetails() throws Throwable {
        view.drawMovies(movies);

        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(0, click()));

        verify(presenter).openMovieDetails(1);
    }

    @Test
    public void testInfiniteScroll() throws Throwable {//todo fck RecyclerView!!! resolve later
//        activityTestRule.runOnUiThread(() -> view.drawMovies(movies));
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
        movie.setPosterPath("qvhlhfrjJZE0K9XOwAjvirBdcRk.jpg");

        return movie;
    }
}