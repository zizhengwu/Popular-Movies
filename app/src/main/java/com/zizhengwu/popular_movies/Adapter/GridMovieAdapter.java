package com.zizhengwu.popular_movies.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.R;

public class GridMovieAdapter extends BaseAdapter {
    private Context mContext;

    public Movie[] getMovies() {
        return movies;
    }

    private void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    private Movie[] movies;

    final float scale;

    public GridMovieAdapter(Context c) {
        scale = c.getResources().getDisplayMetrics().density;
        movies = new Movie[0];
        mContext = c;
    }

    public int getCount() {
        return movies.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.album_grid_width), mContext.getResources().getDimensionPixelSize(R.dimen.album_grid_height)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movies[position].getPoster_path()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
        return imageView;
    }

    public void loadData(Movie[] movies) {
        setMovies(movies);
        notifyDataSetChanged();
    }
}