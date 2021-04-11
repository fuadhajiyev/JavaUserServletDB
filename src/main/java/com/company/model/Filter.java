package com.company.model;

public class Filter {
    private String firstname;
    private String age;

    public Filter(String firstname, String age) {
        this.firstname = firstname;
        this.age = age;
    }

    public Filter() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "firstname='" + firstname + '\'' +
                ", age=" + age +
                '}';
    }
}
