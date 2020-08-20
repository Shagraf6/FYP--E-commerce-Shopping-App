package com.example.fyp.Model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private String Name;
    private String Image;
    private String IdC;
    private String IdP;
    private String IdS;
    private String Search;
    private String style;
    private String material;
    private int Price;
    private int Like;
    private     String Category;
    private     String headertitle;
    private List<ImageColor> productcolorlist;

    public Product()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIdC() {
        return IdC;
    }

    public void setIdC(String idC) {
        IdC = idC;
    }

    public String getIdP() {
        return IdP;
    }

    public void setIdP(String idP) {
        IdP = idP;
    }

    public String getIdS() {
        return IdS;
    }

    public void setIdS(String idS) {
        IdS = idS;
    }

    public String getSearch() {
        return Search;
    }

    public void setSearch(String search) {
        Search = search;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getHeadertitle() {
        return headertitle;
    }

    public void setHeadertitle(String headertitle) {
        this.headertitle = headertitle;
    }

    public List<ImageColor> getProductcolorlist() {
        return productcolorlist;
    }

    public void setProductcolorlist(List<ImageColor> productcolorlist) {
        this.productcolorlist = productcolorlist;
    }
}
