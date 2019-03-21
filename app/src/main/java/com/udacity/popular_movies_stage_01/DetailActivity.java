package com.udacity.popular_movies_stage_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.popular_movies_stage_01.utilities.NetworkUtils;

@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        setTitle("Details");

        ImageView imageView = findViewById(R.id.thumbnail_details);

        Picasso.get()
                .load(NetworkUtils.buildImageUrl(intent.getStringExtra(MainActivity.MOVIE_THUMBNAIL)))
                .into(imageView);

        TextView overview = findViewById(R.id.overview_details);
        overview.setText(intent.getStringExtra(MainActivity.MOVIE_OVERVIEW));

        TextView originalTitle = findViewById(R.id.original_title_details);
        originalTitle.setText(intent.getStringExtra(MainActivity.MOVIE_ORIGINAL_TITLE));

        TextView voteAverage = findViewById(R.id.vote_average_details);
        voteAverage.setText(intent.getStringExtra(MainActivity.MOVIE_AVERAGE_RATING));
        voteAverage.append(getString(R.string.rating_out_of));

        TextView releaseDate = findViewById(R.id.release_date_details);
        releaseDate.append(intent.getStringExtra(MainActivity.MOVIE_RELEASE_DATE).substring(0, 4));
    }

    private void closeOnError() {
        finish();
    }

}