package com.example.stackexchange.questionlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackexchange.Question;
import com.example.stackexchange.R;

import java.util.ArrayList;
import java.util.List;


public class QuestionListViewMVCImpl extends BaseViewMVC<QuestionListViewMVC.Listener>
implements QuestionListViewMVC {

    private  QuestionAdapter questionAdapter;


    public QuestionListViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_question, container, false));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        questionAdapter = new QuestionAdapter(question -> {
//            for (Listener listener : getListeners()) {
//                listener.onQuestionClicked(question);
//            }
//
//        });
 //       questionAdapter = new QuestionAdapter(new OnQuestionClickListener() {
//            @Override
//            public void onQuestionClicked(Question question) {
//                onQuestionClicked(question);
//            }
//        });
        recyclerView.setAdapter(questionAdapter);
    }

    @Override
    public void registerListener(Listener listener) {

    }

    @Override
    public void unregisterListener(Listener listener) {

    }

    @Override
    public void bindQuestion(List<Question> questionList) {
        questionAdapter.bindData(questionList);
    }

    private interface OnQuestionClickListener {
        void onQuestionClicked(Question question);

    }

    private static class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {
        private final OnQuestionClickListener mOnQuestionClickListener;
        private List<Question> questionList = new ArrayList<>();


        public QuestionAdapter(OnQuestionClickListener onQuestionClickListener) {
            mOnQuestionClickListener = onQuestionClickListener;
        }

        // Binding Data
        @SuppressLint("NotifyDataSetChanged")
        public void bindData(List<Question> questions) {
            questionList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_question_item, parent, false);
            return new QuestionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            holder.title.setText(questionList.get(position).getTitle());
            holder.title.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "You clicked the question:"+questionList.get(position).getQuestion_id(), Toast.LENGTH_SHORT).show();
                mOnQuestionClickListener.onQuestionClicked(questionList.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        public static class QuestionHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public QuestionHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.text_title);
            }
        }
    }

}

// Toast.makeText(QuestionListActivity.this, question.getQuestion_id(), Toast.LENGTH_SHORT).show();
//         Log.d("TAG",question.getQuestion_id());
//         QuestionDetailsActivity.start(QuestionListActivity.this, question.getQuestion_id());