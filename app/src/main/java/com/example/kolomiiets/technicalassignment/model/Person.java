package com.example.kolomiiets.technicalassignment.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String province;
    private String postal;
    private String phoneOne;
    private String phoneTwo;
    private String email;
    private String webSite;

    private static Map<String, Integer> paramsIndex = new HashMap<>();

    static {
        paramsIndex.put("first_name", 0);
        paramsIndex.put("last_name", 0);
        paramsIndex.put("company_name", 0);
        paramsIndex.put("address", 0);
        paramsIndex.put("city", 0);
        paramsIndex.put("province", 0);
        paramsIndex.put("postal", 0);
        paramsIndex.put("phone1", 0);
        paramsIndex.put("phone2", 0);
        paramsIndex.put("email", 0);
        paramsIndex.put("web", 0);
    }

    //Constructor
    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.company = builder.company;
        this.address = builder.address;
        this.city = builder.city;
        this.province = builder.province;
        this.postal = builder.postal;
        this.phoneOne = builder.phoneOne;
        this.phoneTwo = builder.phoneTwo;
        this.email = builder.email;
        this.webSite = builder.webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(this.email, person.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    /**
     * NO params for input.
     * Email validation pattern initialized as static parameter class.ValidationPatterns
     *
     * @return - true if valid, false if empty, null or not require emails standard.
     */
    public boolean isEmailValid() {
        if (null != email && !email.isEmpty()) {
            Pattern pattern = Pattern.compile(ValidationPatterns.EMAIL);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    //Compare
    public int hasRelation(Person anotherPerson) {
        if (this.equals(anotherPerson)) return 0;
        if (    this.firstName.equals(anotherPerson.getFirstName()) ||
                this.lastName.equals(anotherPerson.getLastName())   ||
                this.province.equals(anotherPerson.getProvince())   ||
                this.postal.equals(anotherPerson.getPostal())       ||
                this.city.equals(anotherPerson.getCity())           ||
                this.address.equals(anotherPerson.getAddress())     ||
                this.company.equals(anotherPerson.getCompany())     ||
                this.webSite.equals(anotherPerson.getWebSite())     ||
                this.phoneOne.equals(anotherPerson.getPhoneOne())   ||
                this.phoneTwo.equals(anotherPerson.getPhoneTwo())
           ) return 1;
        else return -1;
    }

    // *****************************   Getters  ***********************************************


    public static Map<String, Integer> getParamsIndex() {
        return paramsIndex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostal() {
        return postal;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public String getEmail() {
        return email;
    }

    public String getWebSite() {
        return webSite;
    }

    // ************************************* Getters Ended *****************************************

    //Builder
    public static class Builder {
        //required
        //*****************

        //optional
        private String firstName;
        private String lastName;
        private String company;
        private String address;
        private String city;
        private String province;
        private String postal;
        private String email;
        private String phoneOne;
        private String phoneTwo;
        private String webSite;

        public Builder(/* required param */) {
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setCompany(String company) {
            this.company = company;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setPostal(String postal) {
            this.postal = postal;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneOne(String phoneOne) {
            this.phoneOne = phoneOne;
            return this;
        }

        public Builder setPhoneTwo(String phoneTwo) {
            this.phoneTwo = phoneTwo;
            return this;
        }

        public Builder setWebSite(String webSite) {
            this.webSite = webSite;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
