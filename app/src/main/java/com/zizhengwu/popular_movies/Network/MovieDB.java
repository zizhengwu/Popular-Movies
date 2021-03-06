package com.zizhengwu.popular_movies.Network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zizhengwu.popular_movies.Model.Movie;
import com.zizhengwu.popular_movies.R;
import com.zizhengwu.popular_movies.Singleton;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDB {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static Movie[] getPopular() throws IOException, JSONException {
        return getApi("popular");
    }

    public static Movie[] getTopRated() throws IOException, JSONException {
        return getApi("top_rated");
    }

    public static Movie[] getApi(String type) {
        List<String> urls = new ArrayList<String>();

        String response = "{\"results\":[]}";
        try {
            response = new MovieDB().run("http://api.themoviedb.org/3/movie/" + type + "?api_key=" + Singleton.getInstance().getContext().getResources().getString(R.string.MovieDBApiKey));
        } catch (IOException e) {
            Log.d("com/zizhengwu/popular_movies_stage_1/Network", "no network!");
        }
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(response, new TypeToken<Map<String, Object>>(){}.getType());
        Movie[] movies = gson.fromJson(gson.toJson(map.get("results")), Movie[].class);

        return movies;
    }

}