package com.eutechpro.movies;


import android.content.Context;

import com.eutechpro.movies.common.annotations.ApplicationScope;

import dagger.Component;
import dagger.Provides;

@Component(modules = {ApplicationDiComponent.Module.class})
public interface ApplicationDiComponent {
    void inject(MoviesApplication application);

    Context getApplicationContext();

    @dagger.Module
    @ApplicationScope
    class Module {
        private final Context context;

        Module(Context context) {
            this.context = context;
        }

        @Provides
        Context provideApplicationContext() {
            return context;
        }
    }

}
