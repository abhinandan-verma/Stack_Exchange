package com.example.stackexchange;

import com.google.gson.annotations.SerializedName;

public class QuestionWithBody {
    @SerializedName("title")
    private final String title;

    @SerializedName("question_id")
    private  final String id;

    @SerializedName("body")
    private final String body;

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public QuestionWithBody(String title, String id, String body) {
        this.title = title;
        this.id = id;
        this.body = body;
    }


}
