package com.example.stackexchange;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.stackexchange.detailsquestion.QuestionDetailsActivity;
import com.example.stackexchange.questionlist.QuestionListViewMVC;
import com.example.stackexchange.questionlist.QuestionListViewMVCImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QuestionListActivity extends AppCompatActivity
        implements Callback<QuestionListResponseSchema>, QuestionListViewMVC.Listener {

private StackOverFlowAPI stackOverFlowAPI;
private Call<QuestionListResponseSchema> schemaCall;
private QuestionListViewMVC mViewMVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMVC = new QuestionListViewMVCImpl(LayoutInflater.from(this),null);
        setContentView(mViewMVC.getRootView());

        /*  Retrofit Configuration */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        stackOverFlowAPI = retrofit.create(StackOverFlowAPI.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        schemaCall = stackOverFlowAPI.lastActiveQuestions(30);
        schemaCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        if(schemaCall != null){
            schemaCall.cancel();
        }
    }

    @Override
    public void onResponse(@NonNull Call<QuestionListResponseSchema> call, Response<QuestionListResponseSchema> response) {
        QuestionListResponseSchema responseSchema;
        if(response.isSuccessful() && (responseSchema = response.body()) != null) {
           mViewMVC.bindQuestion(responseSchema.questions());
        }else{
            onFailure(call,null);
        }
    }

    @Override
    public void onFailure(@NonNull Call<QuestionListResponseSchema> call, @NonNull Throwable t) {

        QuestionListResponseSchema responseSchema;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(),null)
                .commitAllowingStateLoss();
    }


    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionListActivity.this, question.getQuestion_id());
    }
}