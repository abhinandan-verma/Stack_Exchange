package com.example.stackexchange.questionlist;

import com.example.stackexchange.Question;

import java.util.List;

// works as an observer
public interface QuestionListViewMVC extends ObservableMVC<QuestionListViewMVC.Listener>{
    void bindQuestion(List<Question> questionList);
     interface Listener {
         void onQuestionClicked(Question question);
    }
}
