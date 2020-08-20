package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.SliderItem;
import com.example.fyp.Model.cartData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class BuynowActivity extends AppCompatActivity {
    EditText address;
    EditText phoneNo;
    EditText Name;
    Toolbar toolbar;
    TextView subtotaltext;
    TextView subtotalprize;
    TextView total;
    FirebaseAuth firebaseAuth;
    Order orderD;

    LinearLayout bottomsheetlayout;
    BottomSheetBehavior bottomSheetBehavior;
    Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buynow);
        firebaseAuth = FirebaseAuth.getInstance();
        sliderView = findViewById(R.id.imageSlider);
        INit();
     ZeenaysLovelySlider();
     getORderIntent();
        findViewById(R.id.continuebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validtions();
            }
        });
    /*    final LottieAnimationView lottieAnimationView=findViewById(R.id.animationView);
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!db.isFav(ProductData.getIdC() + ProductData.getIdP())) {
                    lottieAnimationView.setSpeed(3);
                    lottieAnimationView.playAnimation();
                    db.addToFavorite(ProductData.getIdC() + ProductData.getIdP());
                    addtofav();}
                else {
                    lottieAnimationView.setSpeed(-3);
                    lottieAnimationView.playAnimation();
                    db.removeFromFavroties(ProductData.getIdC() + ProductData.getIdP());
                    removeFav();
                }
            }
        });*/
    }
    SliderAdapterExample adapter;
    SliderView sliderView;
    public void ZeenaysLovelySlider()
    {
        adapter = new SliderAdapterExample(getApplicationContext());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        List<SliderItem> sliderItems=new ArrayList<>();
        SliderItem sliderItem = new SliderItem();
        //sliderItems.add(new SliderItem(R.drawable.backd));
        //sliderItems.add(new SliderItem(R.drawable.oh_look_at_this));
     sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/gif%2F22148-shopping-lady.gif?alt=media&token=b5624106-782e-4bb4-9f0f-4b57fe75ed6b"));
//        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fmens.jpg?alt=media&token=69cb06ef-1408-4208-945a-0e0c72c567d5"));

        adapter.renewItems(sliderItems);
//        renewItems(v);
    }
    public void getORderIntent() {

        if (getIntent() != null) {
            orderD = (Order) getIntent().getSerializableExtra("cartd");
            subtotalprize.setText("Rs. " + orderD.getTotalAmount() + "/-");
            if (orderD.getProductlist().size() == 0) {
                DatabaseHelperclass db = new DatabaseHelperclass(this, firebaseAuth.getUid());
                subtotaltext.setText("Subtotal (" + db.getcartData("0") + " items)");
             //   total.setText("Rs. " +( db.getcartData("1") + 100) + "/-");
                total.setText("Rs. " +(Integer.parseInt(MyCart.TotalPRize) + 100) + "/-");
            } else
            {
                subtotaltext.setText("Subtotal (" + orderD.getProductlist().size() + " items)");
            total.setText("Rs. " + (Integer.parseInt(orderD.getTotalAmount()) + 100) + "/-");
        }
            orderD.setTotalAmount(""+(Integer.parseInt(orderD.getTotalAmount()) + 100));
        }
    }

    public void Validtions() {
        String Address = address.getText().toString().trim();
        if (TextUtils.isEmpty(Name.getText().toString())) {
            Name.setError("Name Required");
            Name.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Name Required", Toast.LENGTH_SHORT).show();
            //  builder.show();
        } else if (TextUtils.isEmpty(phoneNo.getText().toString())) {
            phoneNo.setError("PhoneNO Required");
            phoneNo.setFocusable(true);
            Toast.makeText(getApplicationContext(), "phone no Required", Toast.LENGTH_SHORT).show();
            //builder.show();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(getApplicationContext(), "address Required", Toast.LENGTH_SHORT).show();
            address.setError("Address Required");
            address.setFocusable(true);
            //builder.show();
        } else {
            selectPaymntMehtod(address.getText().toString().trim(), phoneNo.getText().toString().trim(), Name.getText().toString().trim());
        }

    }

    public void INit() {
        address = findViewById(R.id.address);
        phoneNo = findViewById(R.id.PhoneNo);
        Name = findViewById(R.id.Name);
        subtotalprize = findViewById(R.id.subtotalprize);
        subtotaltext = findViewById(R.id.subtotaltext);
        total = findViewById(R.id.totalprice);
        orderD = new Order();
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        bottomsheetlayout = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetlayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    public void addToMyORders(String id, String address, String name, String Number, String timeStamp) {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyOrder");
        //List<cartData> orderLists = new ArrayList<>();
        //orderLists.add(CartD);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, orderD.getTotalAmount() + "", 0 + "", timeStamp, orderD.getProductlist());
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(i);
        View contextView = ItemDetailActivity.CL;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("MyCart").child(firebaseAuth.getUid()).removeValue();
        DatabaseHelperclass db = new DatabaseHelperclass(this, firebaseAuth.getUid());
        db.DeleteCartDatabase();
        this.finish();
      //  startActivity(new Intent(this,Main2Activity.class));
        Snackbar snackbar = Snackbar
                .make(contextView, "Your order is placed", Snackbar.LENGTH_LONG);
        snackbar.show();//Toast.makeText(this, "Your order is placed", Toast.LENGTH_SHORT).show();
    }

    public void addToserver(String address, String name, String Number) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyServerOrder");
        String id = firebaseDatabase.push().getKey();
        //      List<cartData> orderLists = new ArrayList<>();
//        orderLists.add(CartD);
//        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, CartD.getPrice() + "", 0+"", timeStamp, orderLists);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, orderD.getTotalAmount() + "", 0 + "", timeStamp, orderD.getProductlist());
        firebaseDatabase.child(id).setValue(i);
        addToMyORders(id, address, name, Number, timeStamp);
    }

    public void selectPaymntMehtod(final String address, final String phoneNo, final String Name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" One Last Step !");

        View Submit_ordr_Layout = LayoutInflater.from(this).inflate(R.layout.paymentmethod, null);
        //views to set in dailog
        final TextView UserInfo = Submit_ordr_Layout.findViewById(R.id.userinfo);
        final RadioButton R1 = Submit_ordr_Layout.findViewById(R.id.radio1);
        final RadioButton R2 = Submit_ordr_Layout.findViewById(R.id.radio2);

        UserInfo.setText("Address : " + address + "\nName : " + Name + "\nPhone No : " + phoneNo + "\n Total Amount Rs." + orderD.getTotalAmount() + "/-");
        builder.setView(Submit_ordr_Layout);

        //button recover
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String Address = address;
                if(R1.isChecked())
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else
                addToserver(address, phoneNo, Name);



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
    }


    public void buyNow(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                if (TextUtils.isEmpty(Name.getText().toString())) {
                    Name.setError("Name Required");
                    Name.setFocusable(true);
                    Toast.makeText(getApplicationContext(), "Name Required", Toast.LENGTH_SHORT).show();
                    //  builder.show();
                } else if (TextUtils.isEmpty(phoneNo.getText().toString())) {
                    phoneNo.setError("PhoneNO Required");
                    phoneNo.setFocusable(true);
                    Toast.makeText(getApplicationContext(), "phone no Required", Toast.LENGTH_SHORT).show();
                    //builder.show();
                } else if (TextUtils.isEmpty(address.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "address Required", Toast.LENGTH_SHORT).show();
                    address.setError("Address Required");
                    address.setFocusable(true);
                    //builder.show();
                } else {
                    selectPaymntMehtod(address.getText().toString().trim(), phoneNo.getText().toString().trim(), Name.getText().toString().trim());
                }
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}