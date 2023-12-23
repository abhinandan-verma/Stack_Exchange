package com.example.stackexchange;

import static android.content.Intent.EXTRA_COMPONENT_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetailsActivity extends AppCompatActivity implements Callback<SingleQuestionResponseSchema> {

    public static void start(Context context, String questionId){
        Intent i = new Intent(context, QuestionDetailsActivity.class);
        i.putExtra(EXTRA_COMPONENT_NAME,questionId);
        context.startActivity(i);
    }

    public final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private TextView textQuestionBody;
    private StackOverFlowAPI api;
    private String questionId;
    private Call<SingleQuestionResponseSchema> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        textQuestionBody = findViewById(R.id.text_question_body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(StackOverFlowAPI.class);
        questionId  = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_QUESTION_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        call = api.questionDetails(questionId);
        call.enqueue(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(call != null){
            call.cancel();
        }
    }

    @Override
    public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;

        if(response.isSuccessful() && (questionResponseSchema = response.body()) != null){
            String questionBody = questionResponseSchema.getQuestions().getBody();
            textQuestionBody.setText(Html.fromHtml(questionBody,Html.FROM_HTML_MODE_LEGACY));
        }else {
            onFailure(call,null);
        }
    }

    @Override
    public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(),null)
                .commitAllowingStateLoss();
    }
}

