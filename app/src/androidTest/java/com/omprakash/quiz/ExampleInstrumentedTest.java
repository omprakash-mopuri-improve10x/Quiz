package com.omprakash.quiz;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.omprakash.quiz.model.Quiz;
import com.omprakash.quiz.network.QuizApi;
import com.omprakash.quiz.network.QuizApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.omprakash.quiz", appContext.getPackageName());
    }

    @Test
    public void getQuizzes() throws IOException {
        QuizApi quizApi = new QuizApi();
        QuizApiService quizApiService = quizApi.createQuizApiService();
        Call<List<Quiz>> call = quizApiService.fetchQuizzes();
        List<Quiz> quizzes = call.execute().body();
        assertNotNull(quizzes);
        assertFalse(quizzes.isEmpty());
        System.out.println(new Gson().toJson(quizzes));
    }
}