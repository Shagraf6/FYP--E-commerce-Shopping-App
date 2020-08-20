package com.example.fyp.Model;

public class Dashboard_item {
    private String Name;
            private int Image;
private int check;
String Category;


    public Dashboard_item(String name, int image, int check) {
        Name = name;
        Image = image;
        this.check = check;
    }

    public Dashboard_item() {
    }

    public Dashboard_item(String name, int image, int check, String category) {
        Name = name;
        Image = image;
        this.check = check;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
