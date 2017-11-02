package com.eutechpro.movies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.MoviesApplication;
import com.eutechpro.movies.R;

public class MovieDetailsActivity extends BaseActivity {
    private static final String TAG          = "MovieDetailsActivity";
    public static final  String MOVIE_ID_KEY = "com.eutechpro.movies.MOVIE_ID_KEY";
    private Mvp.Presenter presenter;
    private Mvp.View      view;
    private long          movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieId = getIntent().getLongExtra(MOVIE_ID_KEY, -1);
        if (movieId == -1){
            Log.e(TAG, "onCreate: ", new IllegalArgumentException("Bad MovieId"));
            return;
        }
        MoviesApplication.getDetailsComponent().inject(this);
        if (getLastCustomNonConfigurationInstance() == null){
            presenter = MoviesApplication.getDetailsComponent().getPresenter();
        } else {
            presenter = (Mvp.Presenter) getLastCustomNonConfigurationInstance();
        }
        setTitle(getString(R.string.details_loading_label));
    }

    @Override
    protected void onResume() {
        super.onResume();

        view = new View(this);
        presenter.bindView(view);
        presenter.bindActivityCallback(this);
        view.bindPresenter(presenter);
        view.bindActivityCallback(this);

        presenter.loadInitialData(movieId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unBind();
        view.unBind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }
}
