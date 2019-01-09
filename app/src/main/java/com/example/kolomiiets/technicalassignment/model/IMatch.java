package com.example.kolomiiets.technicalassignment.model;

import java.util.List;
import java.util.Map;

public interface IMatch {
    void match(List<Person> list, Person chosen, MatchResults matchResults);
    interface MatchResults {
        void callBack(Map<Integer, List<Person>> results);
    }
}
