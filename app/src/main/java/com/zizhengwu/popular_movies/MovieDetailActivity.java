package com.zizhengwu.popular_movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zizhengwu.popular_movies.Model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private MovieDetailFragment mMovieDetailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        mMovieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movie_detail);

        mMovieDetailFragment.setMovie((Movie) getIntent().getExtras().getParcelable("movie"));
    }
}
