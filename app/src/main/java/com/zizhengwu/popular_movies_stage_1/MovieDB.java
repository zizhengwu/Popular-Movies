package com.zizhengwu.popular_movies_stage_1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getPopular() throws IOException, JSONException {
        return getApi("popular");
    }

    public static List<String> getTopRated() throws IOException, JSONException {
        return getApi("top_rated");
    }

    public static List<String> getApi(String type) throws IOException, JSONException {
        List<String> urls = new ArrayList<String>();

        MovieDB example = new MovieDB();
        String response = example.run("http://api.themoviedb.org/3/movie/" + type + "?api_key=412e9780d02673b7599233b1636a0f0e");
        JSONObject obj = new JSONObject(response);
        JSONArray arr = obj.getJSONArray("results");
        for (int i = 0; i < arr.length(); i++) {
            urls.add(arr.getJSONObject(i).getString("poster_path"));
        }
        return urls;
    }

}