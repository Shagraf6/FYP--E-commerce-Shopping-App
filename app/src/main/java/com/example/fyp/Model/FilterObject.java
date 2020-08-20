package com.example.fyp.Model;

public class FilterObject {
    public String selectedOption;
    public String value;
    public int ID;

    public FilterObject(String selectedOption, String value) {
        this.selectedOption = selectedOption;
        this.value = value;
    }

    public FilterObject() {
    }

    public FilterObject(String selectedOption, String value, int ID) {
        this.selectedOption = selectedOption;
        this.value = value;
        this.ID = ID;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
