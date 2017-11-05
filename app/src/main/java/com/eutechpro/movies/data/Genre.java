package com.eutechpro.movies.data;


import com.google.gson.annotations.SerializedName;

/**
 * Genre of a movie.
 */
@SuppressWarnings("unused")
public class Genre {
    @SerializedName("id")
    private int    genreId;
    @SerializedName("name")
    private String genreName;


    public int getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }
}
