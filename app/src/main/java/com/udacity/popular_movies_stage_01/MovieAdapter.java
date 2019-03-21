package com.udacity.popular_movies_stage_01;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.udacity.popular_movies_stage_01.model.Movie;
import com.udacity.popular_movies_stage_01.utilities.NetworkUtils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final ArrayList<Movie> mDataset;
    private OnBottomReachedListener onBottomReachedListener;
    private View.OnClickListener onClickListener;

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener, View.OnClickListener onClickListener) {
        this.onBottomReachedListener = onBottomReachedListener;
        this.onClickListener = onClickListener;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private MovieViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            imageView = linearLayout.findViewById(R.id.image_iv);
        }
    }

    public MovieAdapter(ArrayList<Movie> myDataset) {
        mDataset = myDataset;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster, parent, false);

        linearLayout.setOnClickListener(onClickListener);

        return new MovieViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if (position == mDataset.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }

        Log.v("onBindViewHolder", "Position: " + Integer.toString(position));

        Picasso.get()
                .load(NetworkUtils.buildImageUrl(mDataset.get(position).getThumbnail()))
                .fit()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}