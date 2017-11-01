package com.eutechpro.movies.discovery;

import android.os.Bundle;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.data.RetrofitMoviesRepository;
import com.eutechpro.movies.data.RetrofitWebApi;

public class DiscoveryActivity extends BaseActivity {

    public static final String TAG = "DiscoveryActivity";

    private Mvp.Presenter presenter;
    private Mvp.View      view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLastCustomNonConfigurationInstance() == null){
            presenter = new Presenter(new Model(new RetrofitMoviesRepository(new RetrofitWebApi())));
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
