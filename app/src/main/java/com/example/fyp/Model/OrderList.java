package com.example.fyp.Model;

public class OrderList {
    String id;
    String image;
    String name;
    String color;
    String size;

    public OrderList(String id, String image, String name, String color, String size) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.color = color;
        this.size = size;
    }

    public OrderList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
