package com.zizhengwu.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zizhengwu.popular_movies.Adapter.ImageAdapter;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.Model.SortBy;
import com.zizhengwu.popular_movies.Network.MovieDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity {
    private ImageAdapter imageAdapter;
    private SortBy sortBy;
    private Subject<SortBy, SortBy> sortObservable = PublishSubject.create();

    private void changeSortMetric(SortBy sortBy) {
        this.sortBy = sortBy;
        Toast.makeText(this, this.sortBy.toString(), Toast.LENGTH_SHORT).show();
        sortObservable.onNext(this.sortBy);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.getInstance().setContext(this);
        setContentView(R.layout.activity_main);
        setUpGridView(savedInstanceState);
        setUpObservables();
        changeSortMetric(SortBy.POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_sort) {
            switch (sortBy) {
                case POPULAR:
                    changeSortMetric(SortBy.TOP_RATED);
                    break;
                case TOP_RATED:
                    changeSortMetric(SortBy.POPULAR);
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie", new ArrayList<Movie>(Arrays.asList(imageAdapter.getMovies())));
    }

    void setUpGridView(Bundle savedInstanceState) {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);

        if (savedInstanceState != null) {
            List<Movie> items = savedInstanceState.getParcelableArrayList("movie");
            Movie[] movies = items.toArray(new Movie[items.size()]);
            imageAdapter.loadData(movies);
        }
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie", imageAdapter.getMovies()[position]);
                startActivity(intent);
            }
        });
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
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie[]>() {
                    @Override
                    public void call(Movie[] movies) {
                        imageAdapter.loadData(movies);
                    }
                });
    }
}
