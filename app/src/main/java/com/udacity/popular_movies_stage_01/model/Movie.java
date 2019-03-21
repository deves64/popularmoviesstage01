package com.udacity.popular_movies_stage_01.model;

public class Movie {
    private String originalTitle;

    private String thumbnail;

    private String overview;

    private String releaseDate;

    private String averageRating;

    public Movie() {

    }

    @SuppressWarnings("unused")
    public Movie(String originalTitle, String thumbnail, String overview, double rating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.thumbnail = thumbnail;
        this.overview = overview;
        this.averageRating = Double.toString(rating);
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = Double.toString(averageRating);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}