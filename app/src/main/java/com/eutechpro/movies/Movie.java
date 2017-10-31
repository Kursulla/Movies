package com.eutechpro.movies;


import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
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

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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