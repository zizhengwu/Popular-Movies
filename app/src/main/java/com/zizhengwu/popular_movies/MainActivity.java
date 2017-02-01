package com.zizhengwu.popular_movies;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zizhengwu.popular_movies.Data.MovieContract;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.Model.SortBy;
import com.zizhengwu.popular_movies.Network.MovieDB;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity implements GridFragment.OnItemSelectedListener {
    private SortBy sortBy;
    private Subject<SortBy, SortBy> sortObservable = PublishSubject.create();
    private GridFragment mGridFragment;
    private MovieDetailFragment mMovieDetailFragment;

    private void changeSortMetric(SortBy sortBy) {
        this.sortBy = sortBy;
        Toast.makeText(this, this.sortBy.toString(), Toast.LENGTH_SHORT).show();
        sortObservable.onNext(this.sortBy);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mGridFragment = new GridFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, mGridFragment).commit();

            if (findViewById(R.id.frame_detail) != null) {
                mMovieDetailFragment = new MovieDetailFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.frame_detail, mMovieDetailFragment).commit();
            }
        }
        else {
            mGridFragment = (GridFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (findViewById(R.id.frame_detail) != null) {
                mMovieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_detail);
            }
        }

        setUpObservables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                changeSortMetric(SortBy.POPULAR);
                break;
            case R.id.action_sort_top_rated:
                changeSortMetric(SortBy.TOP_RATED);
                break;
            case R.id.action_sort_favourite:
                changeSortMetric(SortBy.FAVOURITE);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    void setUpObservables() {
        sortObservable.flatMap(new Func1<SortBy, Observable<Movie[]>>() {

            @Override
            public Observable<Movie[]> call(SortBy sortBy) {
                switch (sortBy) {
                    case POPULAR:
                        return Observable.fromCallable(new Callable<Movie[]>() {
                            @Override
                            public Movie[] call() throws Exception {
                                return MovieDB.getPopular();
                            }
                        }).subscribeOn(Schedulers.io());
                        // lambda one-liner return Observable.fromCallable(() -> ).subscribeOn(Schedulers.io());
                    case TOP_RATED:
                        return Observable.fromCallable(new Callable<Movie[]>() {
                            @Override
                            public Movie[] call() throws Exception {
                                return MovieDB.getTopRated();
                            }
                        }).subscribeOn(Schedulers.io());
                        // lambda one-liner return Observable.fromCallable(() -> MovieDB.getTopRated()).subscribeOn(Schedulers.io());
                    case FAVOURITE:
                        return Observable.fromCallable(new Callable<Movie[]>() {
                            @Override
                            public Movie[] call() throws Exception {
                                List<Movie> movies = new LinkedList<Movie>();
                                Cursor movieCursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
                                while (movieCursor.moveToNext()) {
                                    Movie movie = new Movie("", movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)), movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)), "", "", "");
                                    movies.add(movie);
                                }
                                return movies.toArray(new Movie[movies.size()]);
                            }
                        }).subscribeOn(Schedulers.io());
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie[]>() {
                    @Override
                    public void call(Movie[] movies) {
                        mGridFragment.loadMovies(movies);
                    }
                });
    }

    @Override
    public void onMovieSelected(Movie movie) {
        if (mMovieDetailFragment != null) {
            mMovieDetailFragment.setMovie(movie);
        }
        else {
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }
}
