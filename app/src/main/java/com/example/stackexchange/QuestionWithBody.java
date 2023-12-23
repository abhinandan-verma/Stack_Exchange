package com.example.stackexchange;

import com.google.gson.annotations.SerializedName;

public record QuestionWithBody(@SerializedName("title") String title,
                               @SerializedName("question_id") String id,
                               @SerializedName("body") String body) {
    @Override
    public String body() {
        return body;
    }

    @Override
    public String id() {
        return id;
    }
}
