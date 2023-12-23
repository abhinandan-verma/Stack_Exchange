package com.example.stackexchange.detailsquestion;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stackexchange.QuestionWithBody;
import com.example.stackexchange.R;
import com.example.stackexchange.questionlist.BaseViewMVC;

public class QuestionDetailsViewMVCImpl extends BaseViewMVC<QuestionDetailsViewMVC.Listener>
implements QuestionDetailsViewMVC{

    private final TextView textQuestionBody;

    public QuestionDetailsViewMVCImpl(LayoutInflater inflater, ViewGroup container){
        setRootView(inflater.inflate(R.layout.activity_question_details,container,false));
        textQuestionBody = findViewById(R.id.text_question_body);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.body();
        textQuestionBody.setText(question.id() +" <-- Question Id\n\n"
                + Html.fromHtml(questionBody,Html.FROM_HTML_MODE_LEGACY).toString().trim());
    }

    @Override
    public void unregisterListener(Listener listener) {

    }
}
