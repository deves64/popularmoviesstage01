package com.udacity.popular_movies_stage_01;

import android.os.AsyncTask;

import com.udacity.popular_movies_stage_01.model.Movie;

import java.util.ArrayList;

class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final MovieApiConsumer movieApiConsumer;

    public FetchMoviesTask(MovieApiConsumer movieApiConsumer) {
        this.movieApiConsumer = movieApiConsumer;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        return movieApiConsumer.fetchMovies(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if (movies != null) {
            movieApiConsumer.addMovies(movies);
        }
    }
}