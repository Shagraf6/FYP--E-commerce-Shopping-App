package com.example.fyp.Interface;

import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.cartData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public interface IFirebaseLoadListener {
    void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList);
    void onFirebaseLoadSuccess3(List<cartData> itemGroupList);
    void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);

   // void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);
    void onFirebaseLoadFailed(String message);
}
