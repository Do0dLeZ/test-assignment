package com.example.kolomiiets.technicalassignment.model;

import android.os.AsyncTask;

import java.util.List;

public interface ILoadData {
    void loadData(String filePath, LoadComplete complete);
    interface LoadComplete {
        void callBack(List<Person> list);
    }
}
