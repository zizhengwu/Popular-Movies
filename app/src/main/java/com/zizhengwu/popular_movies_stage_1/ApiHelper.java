package com.zizhengwu.popular_movies_stage_1;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiHelper {
    public static MovieTrailer[] fetchMovieTrailers(String id, String apiKey) {
        MovieTrailer[] trailers = new MovieTrailer[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(ApiAdapter.buildMovieTrailerGsonConverter())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
        Call<MovieTrailer[]> call = service.findTrailerByID(id, apiKey);
        try {
            Response<MovieTrailer[]> response = call.execute();
            trailers = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailers;
    }
}
