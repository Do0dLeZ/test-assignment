package com.example.kolomiiets.technicalassignment.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MatchPersons implements IMatch {

    @Override
    public void match(List<Person> list, Person chosen, MatchResults matchResults) {
        AsyncMatch asyncMatch = new AsyncMatch(list, chosen, matchResults);
        asyncMatch.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncMatch extends AsyncTask<Void, Void, Map<Integer, List<Person>>> {

        private final List<Person> allPersons;
        private final Person chosen;
        private final MatchResults complete;

        public AsyncMatch(List<Person> allPersons1, Person chosen, MatchResults complete) {
            this.allPersons = allPersons1;
            this.chosen = chosen;
            this.complete = complete;
        }

        @Override
        protected Map<Integer, List<Person>> doInBackground(Void... voids) {
            @SuppressLint("UseSparseArrays") HashMap<Integer, List<Person>> result = new HashMap<>();
            result.put(0, new ArrayList<Person>());
            result.put(1, new ArrayList<Person>());
            result.put(-1, new ArrayList<Person>());
            for (Person person : allPersons) {
                switch (person.hasRelation(chosen)) {
                    case 0:
                        result.get(0).add(person);
                        Log.d("Person", "This is You =) Congratulations =)");
                        break;
                    case 1:
                        result.get(1).add(person);
                        //checkWithPool(result, person, 1);
                        Log.d("Person", person.toString() + " was added to level 1");
                        break;
                    case -1:
                        Log.d("Person", person.toString() + " was sent to pool!");
//                        checkInLevels(result, person);
                        result.get(-1).add(person);
                        break;
                }
            }
            Log.d("Start", "Start checking from pool");
            checkFromPool(result);
            return result;
        }

        //Should be optimized =) for a little bit =)
        private void checkFromPool(HashMap<Integer, List<Person>> result) {
            List<Person> personsInPool = result.get(-1);
            if (personsInPool == null || personsInPool.isEmpty()) return;

            List<Person> temp = new ArrayList<>();
            int index = 1;
            boolean wasModified = true;
            while (wasModified && personsInPool.size() > 0) {
                wasModified = false;
                Log.d("Before", "Before start checking pool size is: " + personsInPool.size());
                for (Person person : personsInPool) {
                    for (Person checkPerson : result.get(index)) {
                        if (checkPerson.hasRelation(person) == 1) {
                            wasModified = true;
                            if (result.get(index + 1) == null) {
                                result.put(index + 1, new ArrayList<Person>());
                                Log.d("Main", "Was added: " + result.get(index + 1).size());
                            }
                            result.get(index + 1).add(person);
                            temp.add(person);
                            break;
                        }
                    }
                    Log.d("Step", "Current temp size: " + temp.size());
                }

                Log.d("Pool", "Was in pool: " + personsInPool.size()
                        + " | index: " + index + " |");
                personsInPool.removeAll(temp);
                Log.d("Removed", temp.size() + " | now in pool: " + personsInPool.size());
                temp.clear();
                index++;
            }
        }

//        private void checkInLevels(HashMap<Integer, List<Person>> result, Person person) {
//            for (int index = 1; index <= result.size() - 2; index++) {
//                for (Person noRelPerson : Objects.requireNonNull(result.get(index))) {
//                    if (person.hasRelation(noRelPerson) == 1) {
//                        if (result.get(index + 1) == null)
//                            result.put(index + 1, new ArrayList<Person>()).add(noRelPerson);
//                        return;
//                    }
//                }
//            }
//            result.get(-1).add(person);
//        }
//
//        private void checkWithPool(HashMap<Integer, List<Person>> result, Person person, Integer index) {
//            if (Objects.requireNonNull(result.get(index)).isEmpty()) return;
//
//            for (Person personInPool : Objects.requireNonNull(result.get(-1))) {
//                if (person.hasRelation(personInPool) == 1) {
//
//                    if (result.get(index + 1) == null)
//                        result.put(index + 1, new ArrayList<Person>());
//
//                    result.get(index + 1).add(personInPool);
//
//                    Log.d("Removing", personInPool + " ------ " + result.get(-1).size());
//                    Objects.requireNonNull(result.get(-1)).remove(personInPool);
//                    Log.d("Removed", personInPool + " ------ " + result.get(-1).size());
//                }
//            }
//        }

        @Override
        protected void onPostExecute(Map<Integer, List<Person>> results) {
            complete.callBack(results);
        }
    }
}
