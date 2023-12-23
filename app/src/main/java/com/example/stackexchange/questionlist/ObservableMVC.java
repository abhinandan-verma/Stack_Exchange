package com.example.stackexchange.questionlist;

public interface ObservableMVC<ListenerType> extends ViewMVC {
    void registerListener(ListenerType listenerType);
    void unregisterListener(ListenerType listenerType);

}
