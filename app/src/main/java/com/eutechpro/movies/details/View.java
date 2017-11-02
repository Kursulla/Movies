package com.eutechpro.movies.details;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.GlideApp;
import com.eutechpro.movies.GlideRequests;
import com.eutechpro.movies.R;
import com.eutechpro.movies.data.Genre;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

import java.text.SimpleDateFormat;
import java.util.Locale;


class View implements Mvp.View {
    private static final boolean CLOSE_ACTIVITY = true;
    private       Resources               resources;
    private       Mvp.Presenter           presenter;
    private       MvpViewActivityCallback activityCallback;
    private final ImageView               image;
    private final ImageView               homePageBtn;
    private final ImageView               imdbBtn;
    private final TextView                overview;
    private final TextView                tagLine;
    private final TextView                rating;
    private final TextView                year;
    private final TextView                genres;
    private final TextView                duration;
    private final GlideRequests           glideRequests;
    private final CollapsingToolbarLayout collapsingToolbarLayout;


    public View(BaseActivity activity) {
        activity.setContentView(R.layout.movie_details_layout);
        resources = activity.getResources();
        collapsingToolbarLayout = activity.findViewById(R.id.toolbar_layout);
        homePageBtn = activity.findViewById(R.id.homepage_btn);
        imdbBtn = activity.findViewById(R.id.imdb_btn);
        image = activity.findViewById(R.id.image);
        tagLine = activity.findViewById(R.id.tagline);
        overview = activity.findViewById(R.id.overview);
        rating = activity.findViewById(R.id.rating);
        year = activity.findViewById(R.id.year);
        genres = activity.findViewById(R.id.genres);
        duration = activity.findViewById(R.id.duration);
        glideRequests = GlideApp.with(activity);


        activity.setSupportActionBar(activity.findViewById(R.id.toolbar));
        if (activity.getSupportActionBar() == null){
            return;
        }
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void bindPresenter(Mvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void bindActivityCallback(MvpViewActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }

    @Override
    public void unBind() {
        this.presenter = null;
        this.activityCallback = null;
        this.resources = null;
    }

    @Override
    public void drawMovieDetails(Movie movie) {
        //note: Depending on design requirements, we can hide/remove some of components based on existence of it's values
        if (presenter == null){
            //Do not let user pass further if there is no Presenter bounded.
            throw new IllegalStateException("You have to bind this instance of Mvp.View with an instance of a Mvp.Presenter");
        }
        glideRequests
                .load(movie.getHorizontalImageUrl())
                .placeholder(R.drawable.movie_list_item_placeholder)
                .error(R.drawable.movie_list_item_transparent)
                .fallback(R.drawable.movie_list_item_transparent)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        tagLine.setText(movie.getTagLine());
        overview.setText(movie.getOverview());
        rating.setText(String.valueOf(movie.getVoteAverage()));
        year.setText(composeReleaseDate(movie));
        genres.setText(composeGenresString(movie));
        duration.setText(composeDurationTime(movie));


        if (isValid(movie.getHomepage())){
            homePageBtn.setOnClickListener(view -> presenter.openHomePage(movie.getHomepage()));
        } else {
            homePageBtn.setEnabled(false);
        }
        if (isValid(movie.getImdbId())){
            imdbBtn.setOnClickListener(view -> presenter.openImdbPage(movie.getImdbId()));
        } else {
            imdbBtn.setEnabled(false);
        }
    }

    @Override
    public void showError() {
        if (activityCallback == null){
            throw new IllegalStateException("Ypu have to provide instance of the MvpViewActivityCallback!");
        }
        activityCallback.showToast(R.string.details_error_toast, CLOSE_ACTIVITY);
    }

    @NonNull
    private String composeDurationTime(Movie movie) {
        int duration = movie.getDuration();
        int minutes  = duration % 60;
        int hours    = duration / 60;

        return String.format(Locale.GERMANY, resources.getString(R.string.details_duration_format), hours, minutes);
    }

    @NonNull
    private String composeReleaseDate(Movie movie) {
        return new SimpleDateFormat(resources.getString(R.string.details_release_date_format), Locale.GERMAN).format(movie.getReleaseDate());
    }

    @NonNull
    private String composeGenresString(Movie movie) {
        StringBuilder buffer = new StringBuilder();
        for (Genre genre : movie.getGenres()) {
            buffer.append(genre.getGenreName());
            buffer.append(" ");
        }
        return buffer.toString();
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

}
