package com.example.stackexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QuestionListActivity extends AppCompatActivity
        implements Callback<QuestionListResponseSchema> {
    private QuestionAdapter questionAdapter;
private StackOverFlowAPI stackOverFlowAPI;
private Call<QuestionListResponseSchema> schemaCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       questionAdapter = new QuestionAdapter(new OnQuestionClickListener() {
           @Override
           public void onQuestionClicked(Question question) {
               QuestionDetailsActivity.start(QuestionListActivity.this,question.getQuestion_id());
           }
       });
        recyclerView.setAdapter(questionAdapter);

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
        schemaCall = stackOverFlowAPI.lastActiveQuestions(20);
        schemaCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(schemaCall != null){
            schemaCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionListResponseSchema> call, Response<QuestionListResponseSchema> response) {
        QuestionListResponseSchema responseSchema;
        if(response.isSuccessful() && (responseSchema = response.body()) != null) {
            questionAdapter.bindData(responseSchema.questions());
        }else{
            onFailure(call,null);
        }
    }

    @Override
    public void onFailure(Call<QuestionListResponseSchema> call, Throwable t) {

        QuestionListResponseSchema responseSchema;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(),null)
                .commitAllowingStateLoss();
    }


    private interface OnQuestionClickListener{
        void onQuestionClicked(Question question);
    }

    private static class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder>{
        private  final OnQuestionClickListener mOnQuestionClickListener;
        private List<Question> questionList = new ArrayList<>();



        public static class QuestionHolder extends RecyclerView.ViewHolder{
            public  TextView title;
            public QuestionHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.text_title);
            }
        }
        public QuestionAdapter(OnQuestionClickListener onQuestionClickListener){
            mOnQuestionClickListener = onQuestionClickListener;
        }

        // Binding Data
        @SuppressLint("NotifyDataSetChanged")
        public  void bindData(List<Question> questions){
            questionList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_question_item,parent,false);
        return new QuestionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            holder.title.setText(questionList.get(position).getTitle());
            holder.itemView.setOnClickListener(v -> {
                mOnQuestionClickListener.onQuestionClicked(questionList.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }
    }
}