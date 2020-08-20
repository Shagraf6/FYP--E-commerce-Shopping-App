package com.example.fyp.Model;

import java.util.ArrayList;

public class FilterRvdata {
    String option;
    ArrayList<String> optionLits;

    public FilterRvdata() {
    }

    public FilterRvdata(String option, ArrayList<String> optionLits) {
        this.option = option;
        this.optionLits = optionLits;
    }

    public String getOption() {
        return option;
    }

    public void setOptions(String options) {
        this.option = options;
    }

    public ArrayList<String> getOptionLits() {
        return optionLits;
    }

    public void setOptionLits(ArrayList<String> optionLits) {
        this.optionLits = optionLits;
    }
}
