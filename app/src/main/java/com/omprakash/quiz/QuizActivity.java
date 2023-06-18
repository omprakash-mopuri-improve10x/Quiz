package com.omprakash.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
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
    private Integer[] answerOptionsIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getQuizzes();
        setupAdapter();
        setupRv();
        handleNext();
        handlePrevious();
        setRadioGroup();
        handleSubmit();
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
                    answerOptionsIndexes = new Integer[quizzes.get(0).getQuestions().size()];
                    questions = quizzes.get(0).getQuestions();
                    showData(questions.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapter() {
        questionNumbersAdapter = new QuestionNumbersAdapter();
        questionNumbersAdapter.setQuestions(questions);
        questionNumbersAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemClicked(Question question) {
                showData(question);
            }
        });
    }

    private void setupRv() {
        binding.questionsNumberRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.questionsNumberRv.setAdapter(questionNumbersAdapter);
    }

    private void showData(Question question) {
        showQuestion(question);
        setQuestionNumberColor();
        setNextAndSubmitBtnsVisibility();
        setPreviousBtnVisibility();
    }

    private void showQuestion(Question question) {
        currentQuestionNumber = question.getNumber() - 1;
        binding.radioGroup.clearCheck();
        binding.questionTxt.setText(question.getQuestion());
        binding.answer1Rb.setText(question.getAnswers().get(0));
        binding.answer2Rb.setText(question.getAnswers().get(1));
        binding.answer3Rb.setText(question.getAnswers().get(2));
        binding.answer4Rb.setText(question.getAnswers().get(3));
        if (answerOptionsIndexes[currentQuestionNumber] != null) {
            if (answerOptionsIndexes[currentQuestionNumber] == 0) {
                binding.answer1Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionNumber] == 1) {
                binding.answer2Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionNumber] == 2) {
                binding.answer3Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionNumber] == 3) {
                binding.answer4Rb.setChecked(true);
            }
        }
    }

    private void setQuestionNumberColor() {
        questionNumbersAdapter.currentQuestionPosition = currentQuestionNumber;
        questionNumbersAdapter.notifyDataSetChanged();
    }

    private void handleNext() {
        binding.nextBtn.setOnClickListener(v -> {
            currentQuestionNumber++;
            Question question = questions.get(currentQuestionNumber);
            showData(question);
        });
    }

    private void handlePrevious() {
        binding.previousBtn.setOnClickListener(v -> {
            currentQuestionNumber--;
            Question question = questions.get(currentQuestionNumber);
            showData(question);
        });
    }

    private void setNextAndSubmitBtnsVisibility() {
        if (currentQuestionNumber == questions.size() - 1) {
            binding.submitBtn.setVisibility(View.VISIBLE);
            binding.nextBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.nextBtn.setVisibility(View.VISIBLE);
            binding.submitBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void setPreviousBtnVisibility() {
        if (currentQuestionNumber == 0) {
            binding.previousBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.previousBtn.setVisibility(View.VISIBLE);
        }
    }

    private void setRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.answer1Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionNumber] = 0;
                } else if (binding.answer2Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionNumber] = 1;
                } else if (binding.answer3Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionNumber] = 2;
                } else if (binding.answer4Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionNumber] = 3;
                }
            }
        });
    }

    private int[] getQuizResult() {
        int[] quizResults = new int[2];
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer() == answerOptionsIndexes[i]) {
                quizResults[0]++;
            } else {
                quizResults[1]++;
            }
        }
        return quizResults;
    }

    private void handleSubmit() {
        binding.submitBtn.setOnClickListener(v -> {
            int[] quizResults = getQuizResult();
            Intent intent = new Intent(this, QuizResultsActivity.class);
            intent.putExtra("Correct Answers", quizResults[0]);
            intent.putExtra("Wrong Answers", quizResults[1]);
            startActivity(intent);
        });
    }
}