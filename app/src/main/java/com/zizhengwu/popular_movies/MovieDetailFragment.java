package com.zizhengwu.popular_movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zizhengwu.popular_movies.Adapter.DetailAdapter;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.Model.MovieReview;
import com.zizhengwu.popular_movies.Model.MovieTrailer;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetailFragment extends Fragment {

    private RecyclerView mRecycleView;
    private DetailAdapter mDetailAdapter;
    private Movie mMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if (savedInstanceState == null) {
            mMovie = new Movie("", -1, "", "", "", "");
        }
        else {
            mMovie = savedInstanceState.getParcelable("movie");
        }

        mDetailAdapter = new DetailAdapter(view.getContext(), mMovie, new ArrayList<MovieTrailer>(0), new ArrayList<MovieReview>(0));
        mDetailAdapter.setMovie(mMovie);

        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_view_movie_detail);
        mRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecycleView.setAdapter(mDetailAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movie", mMovie);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();

        if (args != null) {
            mDetailAdapter.setMovie((Movie) args.getParcelable("movie"));
        }

    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        mDetailAdapter.setMovie(movie);
    }
}
