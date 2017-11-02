package com.eutechpro.movies.details;

import com.eutechpro.movies.common.annotations.ActivityScope;
import com.eutechpro.movies.data.RetrofitMoviesRepository;
import com.eutechpro.movies.data.RetrofitWebApi;

import dagger.Module;
import dagger.Provides;

@Module
@ActivityScope
public class DetailsDiModule {
    @Provides
    Mvp.Presenter providePresenter() {
        return new Presenter(new Model(new RetrofitMoviesRepository(new RetrofitWebApi())));
    }
}
