package com.example.fyp.Model;

import java.io.Serializable;

public class ImageColor implements Serializable {
    String colorname;
    String imageurl;

    public ImageColor()
    {

    }

    public ImageColor(String colorname, String imageurl) {
        this.colorname = colorname;
        this.imageurl = imageurl;
    }

    public String getColorname() {
        return colorname;
    }

    public void setColorname(String colorname) {
        this.colorname = colorname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
