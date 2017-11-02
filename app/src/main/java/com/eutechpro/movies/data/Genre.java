package com.eutechpro.movies.data;


import com.google.gson.annotations.SerializedName;

/**
 * Genre of a movie.
 */
public class Genre {
    @SerializedName("id")
    private int    genreId;
    @SerializedName("name")
    private String genreName;


    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
