package com.zizhengwu.popular_movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zizhengwu.popular_movies.Adapter.DetailAdapter;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.Model.MovieReview;
import com.zizhengwu.popular_movies.Model.MovieTrailer;
import com.zizhengwu.popular_movies.Network.ApiHelper;

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

        final DetailAdapter detailAdapter = new DetailAdapter(this, movie, new ArrayList<MovieTrailer>(0), new ArrayList<MovieReview>(0));
        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvTrailers.setAdapter(detailAdapter);


        ApiHelper.fetchMovieTrailers(String.valueOf(movie.getId()), getResources().getString(R.string.MovieDBApiKey))
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
                        detailAdapter.setMovieTrailers(new ArrayList<MovieTrailer>(Arrays.asList(movieTrailers)));
                    }
                });

        ApiHelper.fetchMovieReviews(String.valueOf(movie.getId()), getResources().getString(R.string.MovieDBApiKey))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieReview[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieReview[] movieReviews) {
                        detailAdapter.setMovieReviews(new ArrayList<MovieReview>(Arrays.asList(movieReviews)));
                    }
                });
    }
}
