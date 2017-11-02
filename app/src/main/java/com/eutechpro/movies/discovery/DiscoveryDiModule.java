package com.eutechpro.movies.discovery;

import com.eutechpro.movies.common.annotations.ActivityScope;
import com.eutechpro.movies.data.RetrofitMoviesRepository;
import com.eutechpro.movies.data.RetrofitWebApi;

import dagger.Module;
import dagger.Provides;

@Module
@ActivityScope
public class DiscoveryDiModule {
    @Provides
    Mvp.Presenter providePresenter() {
        return new Presenter(new Model(new RetrofitMoviesRepository(new RetrofitWebApi())));
    }
}
