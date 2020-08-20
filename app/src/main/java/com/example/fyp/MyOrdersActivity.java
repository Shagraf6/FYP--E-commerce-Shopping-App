package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.fyp.Adapter.MyCartAdapter;
import com.example.fyp.Adapter.MyOrdersAdapter;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.cartData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrdersActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
RecyclerView Rview;
    MyOrdersAdapter myAdapter;
public static     FrameLayout frameLayout;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
      Init();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        Rview.setLayoutManager(new LinearLayoutManager(this));//   Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(databaseReference.child("MyOrder").child(firebaseAuth.getUid()), Order.class)
                        .build();
        myAdapter = new MyOrdersAdapter(options,this);
        Rview.setAdapter(myAdapter);
     //   toolbar.setTitle("My Cart (" +counter+")");

    }
    public void Init()
    {

        frameLayout=findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Orders");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Rview=findViewById(R.id.myOrderrecyclerview_id);
    }
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
