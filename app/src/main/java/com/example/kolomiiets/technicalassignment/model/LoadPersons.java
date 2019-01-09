package com.example.kolomiiets.technicalassignment.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class LoadPersons implements ILoadData {


    @Override
    public void loadData(String filePath, LoadComplete complete) {
        LoadPersonsAsync load = new LoadPersonsAsync(filePath, complete);
        load.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadPersonsAsync extends AsyncTask<Void, Person, List<Person>> {

        private final String filePath;
        private final LoadComplete complete;

        LoadPersonsAsync(String filePath, LoadComplete complete) {
            filePath = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
            this.filePath = "/storage/emulated/0/Download/" + filePath;
            this.complete = complete;
        }

        @Override
        protected List<Person> doInBackground(Void... voids) {
            List<Person> successfulAdded = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(
                                        new FileReader(
                                        new File(filePath)));
                String line = reader.readLine();
                validateParameters(line);
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    //Somehow I didn't have a time to remember why, but buffered reader add extra quotes
                    //That's why i made this string manipulations =)
                    if (line.charAt(0) == '\"')
                        line = line.substring(1, line.length() - 1)
                                .replace("\"\"", "\"");

                    String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    Person person = initPerson(values);
                    Log.d("Person name", person.toString());
                    if (person.isEmailValid())
                        successfulAdded.add(person);
                    else Log.d("Validation email", "For " + person
                            + " email :" + person.getEmail() + " is not valid, total:" + count++);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return successfulAdded;
        }

        @Override
        protected void onPostExecute(List<Person> people) {
            if (complete != null) {
                complete.callBack(people);
            }
        }

        private Person initPerson(String[] values) {
            return new Person.Builder()
                    .setFirstName(values[Person.getParamsIndex().get("first_name")])
                    .setLastName(values[Person.getParamsIndex().get("last_name")])
                    .setAddress(values[Person.getParamsIndex().get("address")])
                    .setCity(values[Person.getParamsIndex().get("city")])
                    .setCompany(values[Person.getParamsIndex().get("company_name")])
                    .setPostal(values[Person.getParamsIndex().get("postal")])
                    .setProvince(values[Person.getParamsIndex().get("province")])
                    .setEmail(values[Person.getParamsIndex().get("email")])
                    .setPhoneOne(values[Person.getParamsIndex().get("phone1")])
                    .setPhoneTwo(values[Person.getParamsIndex().get("phone2")])
                    .setWebSite(values[Person.getParamsIndex().get("web")])
                    .build();
        }

        //If some how some one replaced header, reading csv should "=)" be correct anyway
        private void validateParameters(String paramsLine) {
            String[] params = paramsLine.split(",");
            for (int i = 0; i < params.length; i++) {
                Person.getParamsIndex().put(params[i], i);
            }
        }
    }
}
