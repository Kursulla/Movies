package com.eutechpro.movies.matchers;

import android.support.annotation.NonNull;

import com.eutechpro.movies.discovery.YearSelector;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class YearsSelectorMatchers {

    public static Matcher<YearSelector> isAtPosition(@NonNull final Integer selectedYear) {
        return new TypeSafeMatcher<YearSelector>() {
            @Override
            protected boolean matchesSafely(YearSelector selector) {
                return selectedYear.equals(selector.getSelectedYear());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("expected that " + selectedYear + " is selected");
            }
        };
    }
}
