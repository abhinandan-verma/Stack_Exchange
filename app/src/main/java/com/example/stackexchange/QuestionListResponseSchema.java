package com.example.stackexchange;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record QuestionListResponseSchema(@SerializedName("items") List<Question> questions) {

    @Override
    public List<Question> questions() {
        return questions;
    }
}
