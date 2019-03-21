package com.udacity.popular_movies_stage_01.utilities;

import com.udacity.popular_movies_stage_01.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static Movie parseMovieJson(JSONObject movie) {
        final String MOVIE_ORIGINAL_TITLE = "original_title";

        final String MOVIE_THUMBNAIL = "poster_path";

        final String MOVIE_OVERVIEW = "overview";

        final String MOVIE_AVERAGE_RATING = "vote_average";

        final String MOVIE_RELEASE_DATE = "release_date";

        Movie model = new Movie();

        try {
            String title = movie.optString(MOVIE_ORIGINAL_TITLE);
            String thumbnail = movie.optString(MOVIE_THUMBNAIL);
            String overview = movie.optString(MOVIE_OVERVIEW);
            double rating = movie.optDouble(MOVIE_AVERAGE_RATING);
            String releaseDate = movie.optString(MOVIE_RELEASE_DATE);

            model.setOriginalTitle(title);
            model.setThumbnail(thumbnail);
            model.setOverview(overview);
            model.setAverageRating(rating);
            model.setReleaseDate(releaseDate);
        } catch (Exception e) {
            return null;
        }

        return model;
    }

    public static ArrayList<Movie> parseMoviesJson(String json) {
        final String ROOT_OBJECT = "results";

        try {
            JSONObject response = new JSONObject(json);
            JSONArray results = response.optJSONArray(ROOT_OBJECT);

            if(results == null) {
                return null;
            }

            return JSONArrayToList(results);

        } catch (Exception e) {
            return null;
        }
    }

    private static ArrayList<Movie> JSONArrayToList(JSONArray JSONArray) throws Exception {
        ArrayList<Movie> list = new ArrayList<>();
        if (JSONArray != null) {
            for (int i=0; i < JSONArray.length(); i++){
                Movie movie = parseMovieJson(JSONArray.getJSONObject(i));
                list.add(movie);
            }
        }

        return list;
    }
}
