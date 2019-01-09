package com.example.kolomiiets.technicalassignment.view;

import com.example.kolomiiets.technicalassignment.model.Person;

public interface IMainView {
    String getFilePath();
    Person getPerson();
    void showInfo(int successfullyLoaded);
    void showResults(String result);

}
