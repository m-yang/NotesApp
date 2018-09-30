package com.example.android.notesapp.presenter;

interface Presenter<V> {

    void attachView(V view);
    void detachView();
}
