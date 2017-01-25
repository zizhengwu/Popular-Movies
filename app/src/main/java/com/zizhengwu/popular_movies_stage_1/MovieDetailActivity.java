package com.zizhengwu.popular_movies_stage_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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
        RecyclerView rvTrailers = (RecyclerView) findViewById(R.id.recycle_view_trailers);

        final TrailersAdapter trailersAdapter = new TrailersAdapter(this, movie, new ArrayList<MovieTrailer>(0));
        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvTrailers.setAdapter(trailersAdapter);


        ApiHelper.fetchMovieTrailers(movie.id, getResources().getString(R.string.MovieDBApiKey))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieTrailer[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieTrailer[] movieTrailers) {
                        trailersAdapter.setMovieTrailers(new ArrayList<MovieTrailer>(Arrays.asList(movieTrailers)));
                    }
                });
    }
}
