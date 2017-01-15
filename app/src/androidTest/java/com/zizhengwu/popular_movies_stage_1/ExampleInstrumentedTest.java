package com.zizhengwu.popular_movies_stage_1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

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
        Log.d("API", String.valueOf(MovieDB.getPopular()));
    }

    @Test
    public void topRatedApi() throws IOException, JSONException {
        Log.d("API", String.valueOf(MovieDB.getTopRated()));
    }
}
