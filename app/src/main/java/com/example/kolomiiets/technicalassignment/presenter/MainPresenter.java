package com.example.kolomiiets.technicalassignment.presenter;

import com.example.kolomiiets.technicalassignment.model.ILoadData;
import com.example.kolomiiets.technicalassignment.model.IMatch;
import com.example.kolomiiets.technicalassignment.model.LoadPersons;
import com.example.kolomiiets.technicalassignment.model.MatchPersons;
import com.example.kolomiiets.technicalassignment.model.Person;
import com.example.kolomiiets.technicalassignment.view.IMainView;

import java.util.List;
import java.util.Map;

public class MainPresenter {

    private IMainView view;
    private LoadPersons loadPersonsModule;
    private MatchPersons matchPersons;

    private List<Person> persons;

    public MainPresenter(IMainView view) {
        this.view = view;
        loadPersonsModule = new LoadPersons();
        matchPersons = new MatchPersons();
    }

    public void loadPersons(){
        loadPersonsModule.loadData(view.getFilePath(), new ILoadData.LoadComplete() {
            @Override
            public void callBack(List<Person> list) {
                persons = list;
                view.showInfo(list.size());
            }
        });
    }

    //Used static CHOSEN cuse didn't have anought time to complete =(
    //Better in view would has to be ListView + Adapter =(
    //With Dialog where we could choose person....
    public void matchPersons(Person person) {
        matchPersons.match(persons, persons.get(8), new IMatch.MatchResults() {
            @Override
            public void callBack(Map<Integer, List<Person>> results) {
                //Append would be better =)
                String result = "";
                for (Map.Entry<Integer, List<Person>> item: results.entrySet()){
                    result += "Relation level " + item.getKey() + ": ";
                    for (Person resPers : item.getValue()) {
                        result += resPers + " ";
                    }
                    result += "\n";
                }
                view.showResults(result);
            }
        });
    }
}
