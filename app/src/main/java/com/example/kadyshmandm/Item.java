package com.example.kadyshmandm;

public class Item {
    private int id;
    private String title;
    private String description;
    private double value;
    private String category;

    public Item(String title, String description, double value, String category) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.category = category;
    }

    public Item(int id, String title, String description, double value, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title + " — " + value + "₽ (" + category + ")";
    }
}