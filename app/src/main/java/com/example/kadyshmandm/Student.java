package com.example.kadyshmandm;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private String surname;
    private String group;
    private String age;
    private String grade;

    public Student(String name, String surname, String group, String age, String grade) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.age = age;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroup() {
        return group;
    }

    public String getAge() {
        return age;
    }

    public String getGrade() {
        return grade;
    }

    public String getFullInfo() {
        return "ФИО: " + surname + " " + name +
                "\nГруппа: " + group +
                "\nВозраст: " + age +
                "\nОценка: " + grade;
    }
}