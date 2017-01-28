package com.zizhengwu.popular_movies_stage_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zizhengwu.popular_movies_stage_1.Adapter.DetailAdapter;
import com.zizhengwu.popular_movies_stage_1.Model.Movie;
import com.zizhengwu.popular_movies_stage_1.Model.MovieTrailer;
import com.zizhengwu.popular_movies_stage_1.Network.ApiHelper;

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

        final DetailAdapter trailersAdapter = new DetailAdapter(this, movie, new ArrayList<MovieTrailer>(0));
        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvTrailers.setAdapter(trailersAdapter);


        ApiHelper.fetchMovieTrailers(movie.getId(), getResources().getString(R.string.MovieDBApiKey))
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
