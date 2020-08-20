package com.example.fyp.Model;

import android.util.Log;
import android.widget.Switch;

import com.example.fyp.MyChoiceActivity;

import java.io.Serializable;
import java.util.List;

public class ItemData implements Serializable {
    private String Name;
    private String Image;
    private String IdC;
    private String IdP;
    private String IdS;
    private String Search;
  private String style;
    private String material;
    private int Price;
    private String Like;
private     String Category;
private     String headertitle;
    private List<ImageColor> productcolorlist;

    public ItemData() {

    }
    //  String Type;
    public Boolean isColorAvail(String value)
    {
        Boolean chk =false;
        for(int i=0;i<productcolorlist.size();i++)
        {
            if(value.equals(productcolorlist.get(i).colorname)) {
                chk = true;
            break;
            }
        }
        Log.d("checker", "isColorAvail: "+value+" chk-"+chk);
        return chk;
    }
    public Boolean chkFilterData(String option, String Value) {
        switch (option) {
            case "Price":
                if(Value.equals("Low Price")&& (this.Price>=100||this.Price<5000))
                  return true;
                else if(Value.equals("High Price")&& (this.Price>5000))
                    return true;
                break;
                case "Color":
                if (isColorAvail( Value)) {
                    MyChoiceActivity.chosenColor=Value;
                    return true;
                }break;
            case "Style":
                if (Value != null)
                    if (this.style.equals(Value))
                        return true;
                break;
            case "Material":
                if (this.material.equals(Value))
                    return true;
                break;
            case "Category":
                if(Value.equals("View All"))
                    return true;
                else if(this.headertitle.equals(Value))
                    return true;
                break;
        }
        return false;
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

 /*   public String getColor1() {
        return Color1;
    }

    public void setColor1(String color1) {
        Color1 = color1;
    }

    public String getColorurl1() {
        return Colorurl1;
    }

    public void setColorurl1(String colorurl1) {
        Colorurl1 = colorurl1;
    }

    public String getColor2() {
        return Color2;
    }

    public void setColor2(String color2) {
        Color2 = color2;
    }

    public String getColorurl2() {
        return Colorurl2;
    }

    public void setColorurl2(String colorurl2) {
        Colorurl2 = colorurl2;
    }

    public String getColor3() {
        return Color3;
    }

    public void setColor3(String color3) {
        Color3 = color3;
    }

    public String getColorurl3() {
        return Colorurl3;
    }

    public void setColorurl3(String colorurl3) {
        Colorurl3 = colorurl3;
    }
*/
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

    public void setLike(String like) {
        Like = like;
    }

    public String getLike() {
        return Like;
    }
}