package com.eutechpro.movies.discovery;

import android.os.Bundle;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.MoviesApplication;

public class DiscoveryActivity extends BaseActivity {
    private Mvp.Presenter presenter;
    private Mvp.View      view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoviesApplication.getDiscoveryComponent().inject(this);
        if (getLastCustomNonConfigurationInstance() == null){
            presenter = MoviesApplication.getDiscoveryComponent().getPresenter();
        } else {
            presenter = (Mvp.Presenter) getLastCustomNonConfigurationInstance();
        }
        view = new View(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.bindPresenter(presenter);
        view.bindActivityCallback(this);
        presenter.bindActivityCallback(this);
        presenter.bindView(view);
        presenter.loadInitialData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unBind();
        view.unBind();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }


}
