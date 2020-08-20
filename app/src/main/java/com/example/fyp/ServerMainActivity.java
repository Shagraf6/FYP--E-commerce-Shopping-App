package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyServerOrdersAdapter;
import com.example.fyp.Interface.IOnBackPressed;
import com.example.fyp.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Permission;

public class ServerMainActivity extends AppCompatActivity {
DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
RecyclerView recyclerView;
TextView textView;
MyServerOrdersAdapter myAdapter;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_main);
        Init();
    databaseReference = FirebaseDatabase.getInstance().getReference();
    firebaseAuth = FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//   Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
    FirebaseRecyclerOptions<Order> options =
            new FirebaseRecyclerOptions.Builder<Order>()
                    .setQuery(databaseReference.child("MyServerOrder"), Order.class)
                    .build();
    myAdapter = new MyServerOrdersAdapter(options,this);
        recyclerView.setAdapter(myAdapter);
    //   toolbar.setTitle("My Cart (" +counter+")");
checkForSmsPermission();
//            sendSMSMessage();
}
public void sendConfirmationSMS()
{
    for(int i=0;i<MyServerOrdersAdapter.PhoneNo.size();i++)
    {
        sendSms(MyServerOrdersAdapter.PhoneNo.get(i));
    }
}
    public void Init()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Orders");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        recyclerView=findViewById(R.id.Myserverorders);
    }

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1;
    private void sendSms(String phoneNo) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, MyServerOrdersAdapter.message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
           } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        else {
            // Permission already granted. Enable the SMS button.
            if(MyServerOrdersAdapter.check==1)
            {
                sendConfirmationSMS();
                // Toast.makeText(this, "Confirmation SMS sent.", Toast.LENGTH_LONG).show();
                MyServerOrdersAdapter.check=0;
            }}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Enable sms button.
                    if(MyServerOrdersAdapter.check==1)
                    {
                        sendConfirmationSMS();
                        // Toast.makeText(this, "Confirmation SMS sent.", Toast.LENGTH_LONG).show();
                        MyServerOrdersAdapter.check=0;
                    }
                Toast.makeText(this,"Permission granted",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Permission denied.
                    Toast.makeText(this,"Permission denied",
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
checkForSmsPermission();            // Toast.makeText(this, "Confirmation SMS sent.", Toast.LENGTH_LONG).show();
    }

    public void init()
    {
        textView=findViewById(R.id.tv);
        recyclerView=findViewById(R.id.Myserverorders);
    }

    public void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView=findViewById(R.id.tv);
        findViewById(R.id.tv).clearFocus();
        if (user != null) {
            findViewById(R.id.tv).setFocusable(true);
           textView.setText(user.getEmail());
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onStart() {
        checkUserStatus();
        myAdapter.startListening();
            checkForSmsPermission();
        // subscribe();
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
