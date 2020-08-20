package com.example.fyp.Model;

import java.io.Serializable;

public class cartData implements Serializable {
 private    String image;
 private     String name;
 private    String id;
 private  String Color;
 private String qty="1";
 private String Size;
 private int price;

 public cartData()
 {}

    public cartData(String image, String name, String id, String color, String qty, String size, int price) {
        this.image = image;
        this.name = name;
        this.id = id;
        Color = color;
        this.qty = qty;
        Size = size;
        this.price = price;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
