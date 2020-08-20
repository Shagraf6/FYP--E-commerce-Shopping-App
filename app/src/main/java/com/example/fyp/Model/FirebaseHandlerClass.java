package com.example.fyp.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Adapter.MyItemGroupAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Main2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.fyp.MultiRecyclerViewActivity.iFirebaseLoadListener;

public class FirebaseHandlerClass implements IFirebaseLoadListener {
    List<ItemData> ItemDataList;
    List<staffmenber> StaffList;
    List<cartData> CartDataList;
    AlertDialog dialog;
    DatabaseReference myData;
    Context context;

    public FirebaseHandlerClass(Context context)
 {
this.context=context;
 }
public List<staffmenber> getStaffList() {
    StaffList = new ArrayList<>();
    ItemDataList = new ArrayList<>();
    myData = FirebaseDatabase.getInstance().getReference();
myData.child("MyStaff").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                staffmenber i = snapshot.getValue(staffmenber.class);
                Log.i("staffList",""+StaffList.size() );
                             StaffList.add(i);
               // GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                //StaffList=snapshot.getValue(t);
            }
        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        throw databaseError.toException(); //Don't ignore errors

    }
});
Log.i("staffList",""+StaffList.size() );
return StaffList;
}
    public List<ItemData> getFirebaseData(final RecyclerView rv, String Category, String Catname)
    {//rv.setLayoutManager(new LinearLayoutManager(this));
         ItemDataList=new ArrayList<>();
        dialog.show();
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyCat").child("category").child(Category).child(Catname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeadertitle(groupSnapshot.child("headertitle").getValue(true).toString());
                    GenericTypeIndicator<List<ItemData>> t = new GenericTypeIndicator<List<ItemData>>() {
                    };
                    itemGroup.setListitems(groupSnapshot.child("listitems").getValue(t));
                    ItemDataList = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                }
                iFirebaseLoadListener.onFirebaseLoadSuccess2(rv,ItemDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
                throw databaseError.toException(); //Don't ignore errors
            }
        });
        return ItemDataList;
    }
    private List<ItemData> getFirebaseData(RecyclerView rv) {
        dialog.show();
        rv.setHasFixedSize(true);
        ItemDataList=new ArrayList<>();
        myData=FirebaseDatabase.getInstance().getReference();
        myData.child("MyProducts").child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ItemData i = snapshot.getValue(ItemData.class);
                        ItemDataList.add(i);
                    }
                }
                //                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
 return ItemDataList;   }




    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv, List<ItemData> itemGroupList) {
        MyItemAdapter adapter = new MyItemAdapter(context, itemGroupList);
        rv.setAdapter(adapter);
        dialog.dismiss();

    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
      //  MyItemGroupAdapter myAdapter = new MyItemGroupAdapter(context, itemGroupList);
        //my_recycler_view1.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        //my_recycler_view1.setAdapter(myAdapter);

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}
