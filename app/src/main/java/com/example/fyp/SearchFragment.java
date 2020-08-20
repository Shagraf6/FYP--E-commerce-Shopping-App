package com.example.fyp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fyp.Adapter.DashboardAdapter;
import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Adapter.MyItemAdapter3;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Interface.IOnBackPressed;
import com.example.fyp.Model.Dashboard_item;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.Product;
import com.example.fyp.Model.cartData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.skoumal.fragmentback.BackFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import static com.example.fyp.Main2Activity.contentMain;

public class SearchFragment extends Fragment implements IFirebaseLoadListener, BackFragment {
    RecyclerView myrv;
    ImageView frameLayout;
    List<ItemData> ProductData2;
    IFirebaseLoadListener iFirebaseLoadListener;
AlertDialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_search, container, false);
    ////    getActivity().onBackPressed();
        myrv = (RecyclerView) v.findViewById(R.id.RC1);
       frameLayout=v.findViewById(R.id.frameLayout);
        contentMain.setVisibility(View.GONE);
        Main2Activity.searchView.setHint(" Search....");
        Main2Activity.searchView.showSearch();
        iFirebaseLoadListener = this;
      //temp();
 dialog = new SpotsDialog.Builder().setContext(getContext()).build();
        SearchListner();
//        onFirebaseLoadSuccess2(myrv,ProductData2);
        return v;
    }
    public void SearchListner() {
        Main2Activity.searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        Main2Activity.searchView.setHint(" Search....");
        Main2Activity.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFirebaseData(query);
        //Toast.makeText(Main2Activity.this, "cart", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

    }
    public void temp() {
        List<Dashboard_item> MyCategories;
        MyCategories = new ArrayList<>();
        MyCategories.add(new Dashboard_item("All Cloths", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("New Arrival", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("Sales", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("All Seasons", R.drawable.hediedwith,2));
        MyCategories.add(new Dashboard_item("Shirts & T-Shirts", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("Trousers", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("Jeans & Pants", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("Festivals", R.drawable.hediedwith,1));
        MyCategories.add(new Dashboard_item("Accessories", R.drawable.hediedwith,2));
        MyCategories.add(new Dashboard_item("Jump Suit", R.drawable.hediedwith,1));

        DashboardAdapter myAdapter = new DashboardAdapter(getContext(), MyCategories);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        myrv.setAdapter(myAdapter);
    }
    //firebase
    public void getFirebaseData(String queryString) {
        dialog.show();
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductData2=new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").child("Product")
                .orderByChild("search")
                .startAt(queryString.toLowerCase().trim())
                .endAt(queryString.toLowerCase().trim() + "\uf8ff");
        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.i("getoreas1", "*************");
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i("getoreas3", "*************");
                    ItemData i = snapshot.getValue(ItemData.class);
            //        Product i = snapshot.getValue(Product.class);

                    ProductData2.add(i);
                }
            }
            MyItemAdapter adapter =new MyItemAdapter(getContext(),ProductData2);
            myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
            myrv.setAdapter(adapter);

            //onFirebaseLoadSuccess2(myrv,ProductData2);
            //            frameLayout.setVisibility(View.INVISIBLE);
      //      Main2Activity.searchView.showSearch();
            Main2Activity.searchView.clearFocus();
            dialog.dismiss();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public boolean onBackPressed() {

        // -- your code --
      //  Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();

        // return true if you want to consume back-pressed event
        return true;
    }

    public int getBackPriority() {
        return NORMAL_BACK_PRIORITY;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList) {
Log.i("insuccess","*******************");
        MyItemAdapter adapter =new MyItemAdapter(getContext(),itemGroupList);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        myrv.setAdapter(adapter);
       // dialog.dismiss();

    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

}
