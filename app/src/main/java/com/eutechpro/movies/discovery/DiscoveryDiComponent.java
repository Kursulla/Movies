package com.eutechpro.movies.discovery;

import com.eutechpro.movies.ApplicationDiComponent;
import com.eutechpro.movies.common.annotations.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationDiComponent.class,
        modules = DiscoveryDiModule.class
)
public interface DiscoveryDiComponent {
    void inject(DiscoveryActivity activity);

    Mvp.Presenter getPresenter();
}
