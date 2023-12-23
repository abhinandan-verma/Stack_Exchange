package com.example.stackexchange;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackOverFlowAPI {
    @GET("questions?page=1&order=desc&sort=activity&site=stackoverflow")
    Call<QuestionListResponseSchema> lastActiveQuestions(@Query("pagesize") Integer pageSize);

    @GET("questions/{questionId}?order=desc&sort=activity&site=stackoverflow")
    Call<SingleQuestionResponseSchema> questionDetails(@Path("questionId") @NonNull String questionId);

    // /questions/{questionId}?site=stackoverflow&filter=withbody
    // https://api.stackexchange.com/2.3/questions/64117227?order=desc&sort=activity&site=stackoverflow
}
