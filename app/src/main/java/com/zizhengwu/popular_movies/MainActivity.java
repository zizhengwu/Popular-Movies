package com.zizhengwu.popular_movies;

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

    private void changeSortMetric(SortBy sortBy) {
        this.sortBy = sortBy;
        Toast.makeText(this, this.sortBy.toString(), Toast.LENGTH_SHORT).show();
        sortObservable.onNext(this.sortBy);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.frame_container) != null) {
            if (savedInstanceState == null) {
                mGridFragment = new GridFragment();

                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, mGridFragment).commit();
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
                        });
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
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_detail);

        if (movieDetailFragment != null) {
            // tablet
        }
        else {
            // one-pane layout and must swap frags

            movieDetailFragment = new MovieDetailFragment();
            Bundle args = new Bundle();

            args.putParcelable("movie", movie);
            movieDetailFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, movieDetailFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
