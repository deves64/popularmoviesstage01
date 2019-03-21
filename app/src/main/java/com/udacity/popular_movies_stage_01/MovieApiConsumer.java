package com.udacity.popular_movies_stage_01;

import com.udacity.popular_movies_stage_01.model.Movie;

import java.util.ArrayList;

interface MovieApiConsumer {
    ArrayList<Movie> fetchMovies(String queryPath);

    void addMovies(ArrayList<Movie> movies);
}
