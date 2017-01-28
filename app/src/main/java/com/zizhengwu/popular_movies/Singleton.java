package com.zizhengwu.popular_movies;

import android.content.Context;

public class Singleton {
    private static Singleton mInstance = null;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if(mInstance == null)
        {
            mInstance = new Singleton();
        }
        return mInstance;
    }

}