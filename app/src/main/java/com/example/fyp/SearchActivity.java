package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fyp.Adapter.DashboardAdapter;
import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.Dashboard_item;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.cartData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import static com.example.fyp.Main2Activity.contentMain;
import static net.skoumal.fragmentback.BackFragment.NORMAL_BACK_PRIORITY;

public class SearchActivity extends AppCompatActivity implements IFirebaseLoadListener{
MaterialSearchView searchView;
    RecyclerView myrv;
    ImageView frameLayout;
    List<ItemData> ProductData2;
    IFirebaseLoadListener iFirebaseLoadListener;
    AlertDialog dialog;
   Toolbar toolbar;
   LottieAnimationView lottieAnimationView;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
INIT();
        searchView.setHint(" Search....");
        dialog = new SpotsDialog.Builder().setContext(this).build();
        SearchListner();
    }
    public void INIT()
    {
        myrv = (RecyclerView) findViewById(R.id.RC1);
       // frameLayout=findViewById(R.id.frameLayout);
        searchView=findViewById(R.id.search_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("S");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
lottieAnimationView=findViewById(R.id.animationView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchtoolbar2, menu);
        MenuItem item = menu.findItem(R.id.Tsearch);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.myysearch);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        menu.findItem(R.id.Tsearch).setIcon(R.drawable.myysearch);
        searchView.setMenuItem(item);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // lottieAnimationView.setVisibility(View.VISIBLE);
    }
    public void SearchListner() {
       searchView.closeSearch();
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
       searchView.setHint(" Search....");
       searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                lottieAnimationView.setVisibility(View.INVISIBLE);
               dialog.show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFirebaseData(query);
                    }
                },5000);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

    }
    //firebase
    public void getFirebaseData(String queryString) {
        //dialog.show();
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(new LinearLayoutManager(this));
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
                onFirebaseLoadSuccess2(myrv,ProductData2);
                dialog.dismiss();
            }
            else
            {
                dialog.dismiss();
                myrv.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                Toast.makeText(SearchActivity.this, "No Results Found", Toast.LENGTH_SHORT).show();

            }

           }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
    };

    public int getBackPriority() {
        return NORMAL_BACK_PRIORITY;
    }



    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList) {
        Log.i("insuccess","*******************");

        MyItemAdapter adapter =new MyItemAdapter(getApplicationContext(),itemGroupList);
        myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        myrv.setAdapter(adapter);
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
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        lottieAnimationView.setVisibility(View.VISIBLE);

    }



}
