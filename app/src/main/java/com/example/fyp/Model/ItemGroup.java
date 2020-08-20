package com.example.fyp.Model;

import java.io.Serializable;
import java.util.List;

public class ItemGroup  implements Serializable {
    private String Headertitle;
    private List<ImageColor> Coverimagelist;
  private List<ItemData> Listitems;

    public ItemGroup(String headertitle, List<ImageColor> coverimagelist, List<ItemData> listitems) {
        Headertitle = headertitle;
        Coverimagelist = coverimagelist;
        Listitems = listitems;
    }

    public ItemGroup() {
    }

    public String getHeadertitle() {
        return Headertitle;
    }

    public void setHeadertitle(String headertitle) {
        Headertitle = headertitle;
    }

    public List<ImageColor> getCoverimagelist() {
        return Coverimagelist;
    }

    public void setCoverimagelist(List<ImageColor> coverimagelist) {
        Coverimagelist = coverimagelist;
    }

    public List<ItemData> getListitems() {
        return Listitems;
    }

    public void setListitems(List<ItemData> listitems) {
        Listitems = listitems;
    }
}
