package com.zizhengwu.popular_movies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.zizhengwu.popular_movies";
    public static final String PATH_DATATABLE = "movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATATABLE).build();

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String TABLE_NAME = "movies";
    }
}
