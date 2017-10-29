package com.eutechpro.movies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eutechpro.movies.DrawerActivity;
import com.eutechpro.movies.R;

public class MovieDetailsActivity extends DrawerActivity {
    public static final String MOVIE_ID_KEY = "com.eutechpro.movies.MOVIE_ID_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);
    }
}
