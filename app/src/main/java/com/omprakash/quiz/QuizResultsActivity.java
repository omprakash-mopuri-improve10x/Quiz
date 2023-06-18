package com.omprakash.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.omprakash.quiz.databinding.ActivityQuizResultsBinding;

public class QuizResultsActivity extends AppCompatActivity {

    private ActivityQuizResultsBinding binding;
    private int correctAnswers;
    private int wrongAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Quiz Results");
        if (getIntent().hasExtra("Correct Answers")) {
            correctAnswers = (int) getIntent().getIntExtra("Correct Answers", 0);
        }
        if (getIntent().hasExtra("Wrong Answers")) {
            wrongAnswers = (int) getIntent().getIntExtra("Wrong Answers", 0);
        }
        showData();
        handleRestart();
    }

    private void showData() {
        binding.correctAnswersTxt.setText("Correct answers : " + correctAnswers);
        binding.wrongAnswersTxt.setText("Wrong answers : " + wrongAnswers);
        binding.finalScoreTxt.setText("Final Score : " + correctAnswers);
    }

    private void handleRestart() {
        binding.restartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });
    }
}