package com.zizhengwu.popular_movies_stage_1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {
    @GET("3/movie/{id}/videos")
    Call<MovieTrailer[]> findTrailerByID(@Path("id") String movieID, @Query("api_key") String apiKey);
}
