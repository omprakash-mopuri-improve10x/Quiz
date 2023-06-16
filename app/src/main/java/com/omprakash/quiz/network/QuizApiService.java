package com.omprakash.quiz.network;

import com.omprakash.quiz.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizApiService {

    @GET("renuTodo")
    Call<List<Quiz>> fetchQuizzes();
}
