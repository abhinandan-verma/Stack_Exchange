package com.example.stackexchange.detailsquestion;

import static android.content.Intent.EXTRA_COMPONENT_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.stackexchange.Constants;
import com.example.stackexchange.ServerErrorDialogFragment;
import com.example.stackexchange.SingleQuestionResponseSchema;
import com.example.stackexchange.StackOverFlowAPI;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetailsActivity extends AppCompatActivity
        implements Callback<SingleQuestionResponseSchema>, QuestionDetailsViewMVC.Listener {

    public static void start(Context context, String questionId){
        Intent i = new Intent(context, QuestionDetailsActivity.class);
        i.putExtra(EXTRA_COMPONENT_NAME,questionId);
        context.startActivity(i);
    }

    public final String EXTRA_QUESTION_ID = EXTRA_COMPONENT_NAME;

    private StackOverFlowAPI stackOverFlowAPI;
    private String questionId;
    private Call<SingleQuestionResponseSchema> call;
    private QuestionDetailsViewMVC detailsViewMVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       detailsViewMVC = new QuestionDetailsViewMVCImpl(LayoutInflater.from(this),null);
        setContentView(detailsViewMVC.getRootView());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stackOverFlowAPI = retrofit.create(StackOverFlowAPI.class);
        questionId  = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_QUESTION_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
       detailsViewMVC.registerListener(this);
        call = stackOverFlowAPI.questionDetails(questionId);
        call.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        detailsViewMVC.unregisterListener(this);
        if(call != null){
            call.cancel();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponse(@NonNull Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;

        if(response.isSuccessful() && (questionResponseSchema = response.body()) != null){
            String questionBody = questionResponseSchema.getQuestions().body();
          detailsViewMVC.bindQuestion  (questionResponseSchema.getQuestions());
        }else {
            onFailure(call,null);
        }
    }

    @Override
    public void onFailure(@NonNull Call<SingleQuestionResponseSchema> call, Throwable t) {
//        Toast.makeText(this, "Request Failed "+ call.request(), Toast.LENGTH_SHORT).show();
//        Log.d("Responce Failed",call.request().toString());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(),null)
                .commitAllowingStateLoss();


    }
}

