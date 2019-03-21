package com.udacity.popular_movies_stage_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.popular_movies_stage_01.model.Movie;
import com.udacity.popular_movies_stage_01.utilities.JsonUtils;
import com.udacity.popular_movies_stage_01.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InternetConsumer, MovieApiConsumer, View.OnClickListener {
    public static final String MOVIE_THUMBNAIL = "MOVIE_THUMBNAIL";
    public static final String MOVIE_ORIGINAL_TITLE = "MOVIE_ORIGINAL_TITLE";
    public static final String MOVIE_OVERVIEW = "MOVIE_OVERVIEW";
    public static final String MOVIE_AVERAGE_RATING = "MOVIE_AVERAGE_RATING";
    public static final String MOVIE_RELEASE_DATE = "MOVIE_RELEASE_DATE";

    private final String SORT_BY_POPULARITY = "popular";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private final ArrayList<Movie> mMovieList = new ArrayList<>();
    private String mSortBy = SORT_BY_POPULARITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.movie_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter(mMovieList);

        ((MovieAdapter) mAdapter).setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int Position) {
                new InternetCheckTask(MainActivity.this);
            }
        }, this);

        mRecyclerView.setAdapter(mAdapter);
        setAndModifyTitle(R.string.sort_most_popular_title);

        new InternetCheckTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final String SORT_BY_RATING = "top_rated";
        switch (item.getItemId()) {
            case R.id.sort_most_popular:
                setAndModifyTitle(R.string.sort_most_popular_title);
                mSortBy = SORT_BY_POPULARITY;
                mMovieList.clear();
                break;
            case R.id.sort_highest_rated:
                setAndModifyTitle(R.string.sort_highest_rated_title);
                mSortBy = SORT_BY_RATING;
                mMovieList.clear();
                break;
        }

        new InternetCheckTask(this);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void accept(Boolean internet) {
        if(internet) {
            new FetchMoviesTask(this).execute(mSortBy);
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public ArrayList<Movie> fetchMovies(String queryPath) {
        int currentPage = 0;
        int nextPage;

        if (mMovieList.size() != 0) {
            currentPage = mMovieList.size() / 20;
        }

        nextPage = ++currentPage;

        try {
            URL url = NetworkUtils.buildUrl(queryPath, nextPage);

            String response = NetworkUtils.getResponseFromHttpUrl(url);

            return JsonUtils.parseMoviesJson(response);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addMovies(ArrayList<Movie> movies) {
        mMovieList.addAll(movies);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        launchDetailActivity(mMovieList.get(itemPosition));
    }

    private void launchDetailActivity(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MOVIE_THUMBNAIL, movie.getThumbnail());
        intent.putExtra(MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        intent.putExtra(MOVIE_OVERVIEW, movie.getOverview());
        intent.putExtra(MOVIE_AVERAGE_RATING, movie.getAverageRating());
        intent.putExtra(MOVIE_RELEASE_DATE, movie.getReleaseDate());
        startActivity(intent);
    }

    private void setAndModifyTitle(int id) {
        String title = getString(id);
        setTitle("Sorted by: " + title);
    }
}