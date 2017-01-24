package com.zizhengwu.popular_movies_stage_1;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieDBService {
    @GET("3/movie/{id}/videos")
    Observable<MovieTrailer[]> findTrailerByID(@Path("id") String movieID, @Query("api_key") String apiKey);
}
