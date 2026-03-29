package com.example.kadyshmandm;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getFullInfo() {
        return name + " — " + price + " ₽";
    }
}