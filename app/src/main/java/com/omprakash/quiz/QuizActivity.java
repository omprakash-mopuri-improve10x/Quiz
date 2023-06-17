package com.omprakash.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.omprakash.quiz.databinding.ActivityQuizBinding;
import com.omprakash.quiz.model.Question;
import com.omprakash.quiz.model.Quiz;
import com.omprakash.quiz.network.QuizApi;
import com.omprakash.quiz.network.QuizApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private QuestionNumbersAdapter questionNumbersAdapter;
    private ArrayList<Question> questions = new ArrayList<>();
    private Integer currentQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getQuizzes();
        setupAdapter();
        setupRv();
    }

    private void setupAdapter() {
        questionNumbersAdapter = new QuestionNumbersAdapter();
        questionNumbersAdapter.setQuestions(questions);
    }

    private void setupRv() {
        binding.questionsNumberRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.questionsNumberRv.setAdapter(questionNumbersAdapter);
    }

    private void getQuizzes() {
        QuizApiService quizApiService = new QuizApi().createQuizApiService();
        Call<List<Quiz>> quizzes = quizApiService.fetchQuizzes();
        quizzes.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    List<Quiz> quizzes = response.body();
                    questionNumbersAdapter.setQuestions(quizzes.get(0).getQuestions());
                    questions = quizzes.get(0).getQuestions();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(Question question) {
        currentQuestionNumber = question.getNumber() - 1;
        binding.questionTxt.setText(question.getQuestion());
    }
}