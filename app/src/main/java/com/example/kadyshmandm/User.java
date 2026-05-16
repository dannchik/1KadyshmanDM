package com.example.kadyshmandm;

public class User {
    private String name;
    private int age;
    private String email;
    private String city;
    private double rating;

    public User() {}

    public User(String name, int age, String email, String city, double rating) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.city = city;
        this.rating = rating;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return name + " (" + age + " лет), " + email + ", " + city + ", рейтинг: " + rating;
    }
}