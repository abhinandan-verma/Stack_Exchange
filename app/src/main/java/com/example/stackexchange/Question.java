package com.example.stackexchange;

import com.google.gson.annotations.SerializedName;

public class Question {
    // retrofit to call the Question

    @SerializedName("title")
    private final String title;

    @SerializedName("question_id")
    private final String question_id;


    public Question(String title, String questionId) {
        this.title = title;
        this.question_id = questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion_id() {
        return question_id;
    }
}
