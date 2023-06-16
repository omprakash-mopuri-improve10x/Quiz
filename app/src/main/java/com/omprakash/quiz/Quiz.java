package com.omprakash.quiz;

import com.google.gson.annotations.SerializedName;

public class Quiz {

    @SerializedName("_id")
    private String id;
    private Module module;
    private Question question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
