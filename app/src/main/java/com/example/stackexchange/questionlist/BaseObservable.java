package com.example.stackexchange.questionlist;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseObservable<LISTENER_CLASS> {

    // Thread-safe set of Listeners
    private final Set<LISTENER_CLASS> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<LISTENER_CLASS, Boolean>(1));


    public void registerListener(LISTENER_CLASS listener){
        mListeners.add(listener);
    }

    public final void unRegisterListener(LISTENER_CLASS listener){
        mListeners.remove(listener);
    }

    protected final Set<LISTENER_CLASS> getListeners(){
        return Collections.unmodifiableSet(mListeners);
    }
}
