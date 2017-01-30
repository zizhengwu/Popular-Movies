package com.zizhengwu.popular_movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.zizhengwu.popular_movies.Data.DatabaseHelper;
import com.zizhengwu.popular_movies.Data.MovieContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProviderTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        dbHelper.getWritableDatabase().delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
    }

    @Test
    public void insertAndQuery() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_ID, 328111);
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME, "The Secret Life of Pets");
        Log.d("DATA", mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues).toString());

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(MovieContract.MovieEntry.COLUMN_ID, 297761);
        contentValues1.put(MovieContract.MovieEntry.COLUMN_NAME, "Suicide Squad");
        Log.d("DATA", mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues1).toString());

        Cursor movieCursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        while (movieCursor.moveToNext()) {
            Log.d("DATA", String.format("Movie id: %d, %s", movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)), movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME))));
        }

    }
}
