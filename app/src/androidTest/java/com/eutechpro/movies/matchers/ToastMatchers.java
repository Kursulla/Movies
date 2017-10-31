package com.eutechpro.movies.matchers;

import android.app.Activity;
import android.support.annotation.StringRes;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@SuppressWarnings("unused")
public class ToastMatchers {
    /**
     * Match that Toast is shown!
     *
     * @param stringId ID of string that will be show in Toast.
     * @param activity Activity where Toast is shown
     */
    public static void isShown(@StringRes int stringId, Activity activity) {
        onView(withText(stringId))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    /**
     * Match that Toast is NOT shown!
     *
     * @param stringId ID of string that will be show in Toast.
     * @param activity Activity where Toast is shown
     */
    public static void notShown(@StringRes int stringId, Activity activity) {
        onView(withText(stringId))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(not(isDisplayed())));
    }

}
