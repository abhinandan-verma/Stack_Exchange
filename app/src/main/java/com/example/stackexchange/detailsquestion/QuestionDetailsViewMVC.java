package com.example.stackexchange.detailsquestion;

import com.example.stackexchange.QuestionWithBody;
import com.example.stackexchange.questionlist.ObservableMVC;

public interface QuestionDetailsViewMVC extends ObservableMVC<QuestionDetailsViewMVC.Listener> {
    void bindQuestion(QuestionWithBody question);
    interface Listener{ }
}

