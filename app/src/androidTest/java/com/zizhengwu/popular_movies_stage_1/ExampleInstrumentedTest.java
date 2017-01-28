package com.zizhengwu.popular_movies_stage_1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.zizhengwu.popular_movies_stage_1.Model.MovieTrailer;
import com.zizhengwu.popular_movies_stage_1.Network.ApiHelper;
import com.zizhengwu.popular_movies_stage_1.Network.MovieDB;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import rx.observers.TestSubscriber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

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
        TestSubscriber<MovieTrailer[]> testSubscriber = new TestSubscriber<>();
        ApiHelper.fetchMovieTrailers("127380", getInstrumentation().getTargetContext().getResources().getString(R.string.MovieDBApiKey))
                .toBlocking()
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }
}
