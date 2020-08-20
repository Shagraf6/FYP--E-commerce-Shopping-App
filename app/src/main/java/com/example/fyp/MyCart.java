package com.example.fyp;

import com.example.fyp.Adapter.ColorPickerAdapter;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.cartData;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyCartAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity implements IFirebaseLoadListener {

   public static String TotalPRize;
    RecyclerView Rview;
    MyCartAdapter myAdapter;
    static public List<cartData> cartDataList;
    FirebaseAuth firebaseAuth;
    DatabaseReference CartData;
    public static Toolbar toolbar;
    LinearLayout tv;
    CardView bottomsheet;
    static public String title = "";
    static public AlertDialog.Builder builder;
    static public CoordinatorLayout framelayout;
    public static TextView price;
    public static int counter=0;
    DatabaseHelperclass db;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
    //    MyCartAdapter.db=db;
        CartData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        init();
        Rview.setLayoutManager(new LinearLayoutManager(this));//   Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        FirebaseRecyclerOptions<cartData> options =
                new FirebaseRecyclerOptions.Builder<cartData>()
                        .setQuery(CartData.child("MyCart").child(firebaseAuth.getUid()), cartData.class)
                        .build();
        myAdapter = new MyCartAdapter(options,this);
       Rview.setAdapter(myAdapter);
  counter=db.getcartData("0");
        toolbar.setTitle("My Cart (" +counter+")");
        viewSetter(db.getcartData("0"));

//        myAdapter.notifyDataSetChanged();
    }

    public void viewSetter(int counter) {
            bottomsheet.setVisibility(View.VISIBLE);
            tv.setVisibility(View.INVISIBLE);
            toolbar.setTitle("My Cart (" + counter+ ")");
        if (counter==0) {
            bottomsheet.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.VISIBLE);
           // toolbar.setTitle("My Cart");
        }
    }
    public void onclickW(View v)
    {
        Intent intent = new Intent(this, extraActivity.class);
        startActivity(intent);
    }

    public void init() {
        tv = findViewById(R.id.tv);
        Rview = findViewById(R.id.Cartrecyclerview_id);

        price = findViewById(R.id.totalprice);
        cartDataList = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        bottomsheet = findViewById(R.id.bottomSheet);
        framelayout = findViewById(R.id.frameLayout);
        //TotalPRize=db.getcartData("1")+"";
        price.setText("Rs. "+db.getcartData("1")+"/-");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void getCartData() {
        //Rview.setHasFixedSize(true);
        //cartDataList.clear();
        //Rview.invalidate();
       // recreate();
        cartDataList=new ArrayList<>();
        CartData = FirebaseDatabase.getInstance().getReference();
        CartData.child("MyCart").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    cartData cart = new cartData();
                    cart = cartSnapshot.getValue(cartData.class);
                    cartDataList.add(cart);
                    Log.i("incart", "$$$$");
                }

                //onFirebaseLoadSuccess3(cartDataList);
              //viewSetter(db.getcartData("0"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv, List<ItemData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {
        FirebaseRecyclerOptions<cartData> options =
                new FirebaseRecyclerOptions.Builder<cartData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MyCart").child(firebaseAuth.getUid()), cartData.class)
                        .build();
  myAdapter = new MyCartAdapter(options,this);

        Rview.setHasFixedSize(true);
        Rview.setLayoutManager(new LinearLayoutManager(this));//   Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        Rview.setAdapter(myAdapter);
    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }

    public void addToMyORders(String id,String address, String name, String Number,String timeStamp) {
       DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyOrder");
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, price.getText().toString(),0+"",timeStamp, cartDataList);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(i);
        View contextView = findViewById(R.id.frameLayout);

        Snackbar snackbar = Snackbar
                .make(contextView, "Your order is placed", Snackbar.LENGTH_LONG);

        snackbar.show();//Toast.makeText(this, "Your order is placed", Toast.LENGTH_SHORT).show();
    }
    public void addToserver(String address, String name, String Number) {
        String timeStamp=String.valueOf(System.currentTimeMillis());
     DatabaseReference   firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyServerOrder");
        String id = firebaseDatabase.push().getKey();
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, price.getText().toString(),0+"",timeStamp, cartDataList);
        firebaseDatabase.child(id).setValue(i);
        addToMyORders(id,address,name,Number,timeStamp);
    }

    public void buyNow(View v) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Place Your Order");

        View Submit_ordr_Layout = LayoutInflater.from(this).inflate(R.layout.activity_buy_now, null);
        //views to set in dailog
        final EditText address = Submit_ordr_Layout.findViewById(R.id.address);
        final EditText phoneNo = Submit_ordr_Layout.findViewById(R.id.PhoneNo);
        final EditText Name = Submit_ordr_Layout.findViewById(R.id.Name);

        builder.setView(Submit_ordr_Layout);

        //button recover
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String Address = address.getText().toString().trim();
                addToserver(address.getText().toString().trim(), phoneNo.getText().toString().trim(), Name.getText().toString().trim());


            }
        });
        //button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();

            }
        });
        builder.create().show();

*/
        List<cartData> orderLists = new ArrayList<>();
       // orderLists.add(CartD);
        getCartData();
        Log.i("cartorderlist",cartDataList.size()+"");
        String timeStamp=String.valueOf(System.currentTimeMillis());
        order=new Order();
        order.setTimeStamp(timeStamp);
        order.setProductlist(cartDataList);

        order.setTotalAmount(""+db.getcartData("1"));

        Intent i = new Intent(this, cartbuynow.class);
        i.putExtra("cartd", order);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    viewSetter(db.getcartData("0"));
        TotalPRize=db.getcartData("1")+"";
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myAdapter.startListening();
        viewSetter(db.getcartData("0"));
    }
}
