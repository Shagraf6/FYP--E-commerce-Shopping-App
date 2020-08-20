package com.example.fyp.Model;

import java.util.Locale;

import androidx.cardview.widget.CardView;

public class cardCover {

        private String Title;
        private int Thumbnail ;
    private String Title2;
    private int Thumbnail2 ;
private String Category;

        public cardCover(String title, int thumbnail) {
            Title = title;
            Thumbnail= thumbnail;
        }

    public cardCover(String title, int thumbnail, String title2, int thumbnail2, String category) {
        Title = title;
        Thumbnail = thumbnail;
        this.Category=category;
        Title2 = title2;
        Thumbnail2 = thumbnail2;
    }

    public cardCover(String category) {
        Category = category;
    }


    /*    public String getCategory() {
            return Category;
        }

        public String getDescription() {
            return Description;
        }




        public void setCategory(String category) {
            Category = category;
        }

        public void setDescription(String description) {
            Description = description;
        }
*/
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public void setThumbnail(int thumbnail) {
            Thumbnail = thumbnail;
        }
    public int getThumbnail() {
        return this.Thumbnail;
    }

    public String getTitle2() {
        return Title2;
    }

    public void setTitle2(String title2) {
        Title2 = title2;
    }

    public int getThumbnail2() {
        return Thumbnail2;
    }

    public void setThumbnail2(int thumbnail2) {
        Thumbnail2 = thumbnail2;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
