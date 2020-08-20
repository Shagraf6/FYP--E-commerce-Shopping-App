package com.example.fyp.Model;

public class SliderItem2 {
    private String description;
    private String imageUrl;

    public SliderItem2(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public SliderItem2() {
    }


    public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

