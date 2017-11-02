package com.eutechpro.movies.details;

import com.eutechpro.movies.ApplicationDiComponent;
import com.eutechpro.movies.common.annotations.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationDiComponent.class,
        modules = DetailsDiModule.class
)
public interface DetailsDiComponent {
    void inject(MovieDetailsActivity activity);

    Mvp.Presenter getPresenter();
}
