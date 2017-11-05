package com.eutechpro.movies.data;


import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Base building entity of the app.
 * <br/>
 * It represents one instance of the movie in supporting web API service.
 */

@SuppressWarnings("unused")
public class Movie {
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    @SerializedName("vote_count")
    private int     voteCount;
    @SerializedName("id")
    private int     id;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private double  voteAverage;
    @SerializedName("title")
    private String  title;
    @SerializedName("popularity")
    private double  popularity;
    @SerializedName("poster_path")
    private String  posterPath;
    @SerializedName("original_language")
    private String  originalLanguage;
    @SerializedName("original_title")
    private String  originalTitle;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = null;
    @SerializedName("genres")
    private List<Genre>   genres   = null;
    @SerializedName("backdrop_path")
    private String  backdropPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String  overview;
    @SerializedName("release_date")
    private Date    releaseDate;
    @SerializedName("tagline")
    private String  tagLine;
    @SerializedName("runtime")
    private int     duration;
    @SerializedName("homepage")
    private String  homepage;
    @SerializedName("imdb_id")
    private String  imdbId;

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getDuration() {
        return duration;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getHorizontalImageUrl() {
        if (backdropPath == null){
            return null;
        }
        return BASE_IMAGE_URL + backdropPath;
    }

    public String getVerticalImageUrl() {
        if (posterPath == null){
            return null;
        }
        return BASE_IMAGE_URL + posterPath;
    }
}