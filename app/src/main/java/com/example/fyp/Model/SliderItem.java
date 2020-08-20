package com.example.fyp.Model;

public class SliderItem {
    public int image;

    private String description;
    private String imageUrl;

  public   SliderItem(int image){
        this.image=image;

    }

    public SliderItem() {
    }

    public SliderItem(String imageUrl) {
      this.imageUrl=imageUrl;
    }
    public int getImage(){
        return image;
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

