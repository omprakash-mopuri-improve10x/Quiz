package com.omprakash.quiz;

import com.google.gson.annotations.SerializedName;

public class Question {

    private Integer number;
    private String question;
    private String[] answers;
    @SerializedName("correct_answer")
    private Integer correctAnswer;
}
