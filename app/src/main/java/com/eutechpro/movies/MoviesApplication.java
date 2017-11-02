package com.eutechpro.movies;

import android.app.Application;

import com.eutechpro.movies.details.DaggerDetailsDiComponent;
import com.eutechpro.movies.details.DetailsDiComponent;
import com.eutechpro.movies.details.DetailsDiModule;
import com.eutechpro.movies.discovery.DaggerDiscoveryDiComponent;
import com.eutechpro.movies.discovery.DiscoveryDiComponent;
import com.eutechpro.movies.discovery.DiscoveryDiModule;


public class MoviesApplication extends Application {

    private static ApplicationDiComponent applicationDiComponent;
    private static DetailsDiComponent     detailsDiComponent;
    private static DiscoveryDiComponent   discoveryDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationComponent().inject(this);
    }

    private ApplicationDiComponent getApplicationComponent() {
        if (applicationDiComponent == null){
            applicationDiComponent = DaggerApplicationDiComponent.builder()
                    .module(new ApplicationDiComponent.Module(getApplicationContext()))
                    .build();
        }
        return applicationDiComponent;
    }

    /**
     * Dagger component for a Details domain.
     */
    public static DetailsDiComponent getDetailsComponent() {
        if (detailsDiComponent == null){
            detailsDiComponent = DaggerDetailsDiComponent.builder()
                    .applicationDiComponent(applicationDiComponent)
                    .detailsDiModule(new DetailsDiModule())
                    .build();
        }
        return detailsDiComponent;
    }

    /**
     * Dagger component for a Discovery domain.
     */
    public static DiscoveryDiComponent getDiscoveryComponent() {
        if (discoveryDiComponent == null){
            discoveryDiComponent = DaggerDiscoveryDiComponent.builder()
                    .applicationDiComponent(applicationDiComponent)
                    .discoveryDiModule(new DiscoveryDiModule())
                    .build();
        }
        return discoveryDiComponent;
    }
}