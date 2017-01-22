package com.zizhengwu.popular_movies_stage_1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zizhengwu.popular_movies_stage_1", appContext.getPackageName());
    }

    @Test
    public void popularApi() throws IOException, JSONException {
        Gson gson = new Gson();
        Log.d("API", gson.toJson(MovieDB.getPopular()));
    }

    @Test
    public void topRatedApi() throws IOException, JSONException {
        Gson gson = new Gson();
        Log.d("API", gson.toJson(MovieDB.getTopRated()));
    }

    @Test
    public void movieTrailerApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(ApiAdapter.buildMovieTrailerGsonConverter())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
        Call<MovieTrailer[]> call = service.findTrailerByID("127380", getInstrumentation().getTargetContext().getResources().getString(R.string.MovieDBApiKey));
        try {
            Response<MovieTrailer[]> response = call.execute();
            MovieTrailer[] trailers = response.body();
            Log.d("API", new Gson().toJson(trailers));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
