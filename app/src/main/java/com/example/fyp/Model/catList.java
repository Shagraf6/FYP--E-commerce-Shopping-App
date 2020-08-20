package com.example.fyp.Model;

import android.view.View;

public class catList {
    View view;
    String text;
String category;
    public catList(View view, String text,String category) {
        this.view = view;
        this.text = text;
        this.category=category;
    }
    public catList(String text,String category) {
        this.text = text;
        this.category=category;
    }

    public catList() {
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
