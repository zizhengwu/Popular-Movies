package com.zizhengwu.popular_movies_stage_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = (Movie) getIntent().getExtras().getParcelable("movie");
        setUpViews();
    }

    void setUpViews() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView release_date = (TextView) findViewById(R.id.release_date);
        TextView vote_average = (TextView) findViewById(R.id.vote_average);
        TextView overview = (TextView) findViewById(R.id.overview);
        ImageView poster = (ImageView) findViewById(R.id.poster);

        title.setText(movie.title);
        release_date.setText(movie.release_date);
        overview.setText(movie.overview);
        vote_average.setText(movie.vote_average);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/"+movie.poster_path).into(poster);

    }
}
