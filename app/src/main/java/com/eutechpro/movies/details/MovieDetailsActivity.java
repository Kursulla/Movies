package com.eutechpro.movies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.eutechpro.movies.DrawerActivity;
import com.eutechpro.movies.R;

public class MovieDetailsActivity extends DrawerActivity {
    public static final String TAG          = "MovieDetailsActivity";
    public static final String MOVIE_ID_KEY = "com.eutechpro.movies.MOVIE_ID_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        long movieId = getIntent().getLongExtra(MOVIE_ID_KEY, 0);
        if (movieId == 0){
            Log.e(TAG, "onCreate: ", new IllegalArgumentException("MovieId is not passed in"));
            finish();
        }


//        RetrofitApi api = new RetrofitApi();
//        api.fetchMovieDetails(movieId).subscribe(new BiConsumer<Movie, Throwable>() {
//            @Override
//            public void accept(Movie movie, Throwable throwable) throws Exception {
//                Log.d(TAG, "accept: ");
//            }
//        });
    }
}
