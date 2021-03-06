package com.zizhengwu.popular_movies.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.zizhengwu.popular_movies.Model.MovieReview;
import com.zizhengwu.popular_movies.Model.MovieTrailer;

import java.lang.reflect.Type;

import retrofit2.converter.gson.GsonConverterFactory;

public class ApiJsonConverter {
    static class MovieTrailerDeserializer implements JsonDeserializer<MovieTrailer[]> {

        @Override
        public MovieTrailer[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement content = json.getAsJsonObject().get("results");

            return new Gson().fromJson(content, MovieTrailer[].class);
        }
    }

    static GsonConverterFactory buildMovieTrailerGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // adding custom deserializers
        gsonBuilder.registerTypeAdapter(MovieTrailer[].class, new MovieTrailerDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);

    }

    static class MovieReviewDeserializer implements JsonDeserializer<MovieReview[]> {

        @Override
        public MovieReview[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement content = json.getAsJsonObject().get("results");

            return new Gson().fromJson(content, MovieReview[].class);
        }
    }

    static GsonConverterFactory buildMovieReviewGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // adding custom deserializers
        gsonBuilder.registerTypeAdapter(MovieReview[].class, new MovieReviewDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);

    }
}
