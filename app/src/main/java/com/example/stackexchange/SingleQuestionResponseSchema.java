package com.example.stackexchange;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleQuestionResponseSchema {
    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;

    public SingleQuestionResponseSchema(List<QuestionWithBody> mQuestions) {
        this.mQuestions = mQuestions;
    }

    public QuestionWithBody getQuestions() {
        return mQuestions.get(0);
    }
}
