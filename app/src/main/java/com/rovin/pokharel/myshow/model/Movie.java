package com.rovin.pokharel.myshow.model;

/**
 * Created by Rovin on 8/7/2018.
 */

public class Movie {
    private String movieID;
    private String movieName;
    private String movieDescription;
    private String movieDate;
    private String movieImage;
    private String movieType;

    public Movie(){
    }

    public Movie(String movieName, String movieDate, String movieImage, String movieDescription){
        this.movieName = movieName;
        this.movieDate = movieDate;
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
    }

    public Movie(String movieName, String movieDate, String movieImage, String movieDescription, String movieType){
        this.movieName = movieName;
        this.movieDate = movieDate;
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
        this.movieType = movieType;
    }

    public Movie(String movieID, String movieName, String movieDate, String movieImage, String movieDescription, String movieType) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieDate = movieDate;
        this.movieImage = movieImage;
        this.movieType = movieType;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
}
