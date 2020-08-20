package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.SliderItem2;
import com.example.fyp.Model.cartData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity implements IFirebaseLoadListener {


    AlertDialog dialog;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;
    Toolbar toolbar;
    LinearLayout noitem;
    List<ItemData> ProductData2;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    FirebaseAuth firebaseAuth;
    DatabaseHelperclass db;
    NotificationBadge badge;
    static int mNotifCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        init();
        firebaseAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelperclass(this, firebaseAuth.getUid());
        if (db.isempty()) {
            my_recycler_view.setVisibility(View.INVISIBLE);
            noitem.setVisibility(View.VISIBLE);
        }
        //  imageSlider();
        //   myData = FirebaseDatabase.getInstance().getReference("MyData");
        myData = FirebaseDatabase.getInstance().getReference();
        dialog = new SpotsDialog.Builder().setContext(this).build();
        iFirebaseLoadListener = this;
     getFirebaseData2();
    }
    public void onclickW(View v)
    {
        Intent intent = new Intent(this, extraActivity.class);
        startActivity(intent);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Wish List");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        noitem = findViewById(R.id.tv);
        my_recycler_view = findViewById(R.id.favrecyclerview_id);
      //  my_recycler_view.setHasFixedSize(true);
       // my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
  //      my_recycler_view = findViewById(R.id.favrecyclerview_id);
    //  my_recycler_view.removeAllViews();
     // my_recycler_view.removeAllViewsInLayout();
      //ProductData2.clear();
        }

    private void getFirebaseData2() {
        dialog.show();
        ProductData2 = new ArrayList<>();
        ProductData2.clear();
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyFav").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.i("getoreas3", "*************");
                        ItemData i = snapshot.getValue(ItemData.class);
                        ProductData2.add(i);
                    }
                }
                iFirebaseLoadListener.onFirebaseLoadSuccess2(my_recycler_view,ProductData2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv, List<ItemData> itemGroupList) {
        MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), itemGroupList);
        my_recycler_view.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rv.setAdapter(myAdapter);
        dialog.dismiss();

    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelperclass(this, firebaseAuth.getUid());
        setNotifCount(db.getcartData("0"));
        if (db.isempty()) {
            noitem.setVisibility(View.VISIBLE);
        my_recycler_view.setVisibility(View.INVISIBLE);
        }
        // subscribe();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchtoolbar, menu);
        MenuItem item = menu.findItem(R.id.Tcart);
        //  searchView.setMenuItem(item);
        db = new DatabaseHelperclass(this, firebaseAuth.getUid());
        View v = menu.findItem(R.id.Tcart).getActionView();
        badge = (NotificationBadge) menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.badge);
        menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavActivity.this, MyCart.class);
               finish();
               startActivity(intent);
            }
        });
        if (mNotifCount != 0)
            badge.setText(mNotifCount + "");
        return true;
    }

    private void setNotifCount(int count) {
        mNotifCount = count;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Tcart:
                Intent intent = new Intent(this, MyCart.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

