package com.udacity.popular_movies_stage_01.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3";

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w185";

    private static final String MOVIE_PATH = "movie";

    private static final String API_KEY_PARAM = "api_key";

    private static final String LANGUAGE_PARAM = "language";

    private static final String PAGE_PARAM = "page";

    private static final String default_language_value = "en-US";

    public static URL buildUrl(String queryPath, Integer pageValue)
    {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(MOVIE_PATH)
                .appendEncodedPath(queryPath)
                //Todo: Please enter API Key here
                .appendQueryParameter(API_KEY_PARAM, "")
                .appendQueryParameter(LANGUAGE_PARAM, default_language_value)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(pageValue))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.v("buildUrl", "URL: " + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String buildImageUrl(String imagePath)
    {
        Uri builtUri = Uri.parse(IMG_BASE_URL).buildUpon()
                .appendEncodedPath(imagePath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.v("buildImageUrl", "URL: " + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            return url.toString();
        }

        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
