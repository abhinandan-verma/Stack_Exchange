package com.example.stackexchange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackOverFlowAPI {
    @GET("questions?page=1&order=desc&sort=activity&site=stackoverflow")
    Call<QuestionListResponseSchema> lastActiveQuestions(@Query("pagesize") Integer pageSize);

    @GET("/questions/{questionId}?site=stackoverflow&filter=withbody")
    Call<SingleQuestionResponseSchema> questionDetails(@Path("questionId") String questionId);


}
