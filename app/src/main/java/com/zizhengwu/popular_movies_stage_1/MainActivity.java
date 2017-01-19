package com.zizhengwu.popular_movies_stage_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortBy = SortBy.POPULAR;
        setUpGridView();
        setUpObservables();
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
                    sortBy = SortBy.TOP_RATED;
                    break;
                case TOP_RATED:
                    sortBy = SortBy.POPULAR;
                    break;
            }
            Toast.makeText(this, sortBy.toString(), Toast.LENGTH_SHORT).show();
            sortObservable.onNext(sortBy);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setUpGridView() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
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
                        return Observable.fromCallable(() -> MovieDB.getPopular()).subscribeOn(Schedulers.io());
                    case TOP_RATED:
                        return Observable.fromCallable(() -> MovieDB.getTopRated()).subscribeOn(Schedulers.io());
                    default:
                        return Observable.fromCallable(() -> new Movie[0]).subscribeOn(Schedulers.io());
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
