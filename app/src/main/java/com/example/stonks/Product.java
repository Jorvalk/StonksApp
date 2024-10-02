package com.example.stonks;

public class Product {
    private String name;
    private double price;
    private String date;
    private int id;

    // Constructor que incluye ID
    public Product(int id, String name, double price, String date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Setter para la fecha
    public void setDate(String date) {
        this.date = date;
    }
}
