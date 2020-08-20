package com.example.fyp;
import com.airbnb.lottie.LottieAnimationView;
import com.example.fyp.Adapter.ColorPickerAdapter;
import com.example.fyp.Adapter.RecyclerViewAdapter;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.cartData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Interface.IFirebaseLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.$Gson$Preconditions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {
    static public TextView imagedetail, imagePriza, imageColor, imageSize;
    static public String color, Size;
    static public ImageView Imageview;
    public Button addtocart_Btn, buynow;
    static public cartData CartD;
    boolean mprocessProduct = false;
    public FirebaseAuth firebaseAuth;
    public FloatingActionButton fab;
    public DatabaseReference firebaseDatabase;
    CheckBox checkBox;
    CircleImageView Color1, color2, color3;
    TextView tv1, tv2, tv3,noOfLikes;
    ItemData ProductData;
    IFirebaseLoadListener iFirebaseLoadListener;
    private boolean isShowingCardHeaderShadow;
    //   List<com.example.fyp.Model> modelList = new ArrayList<>();
   static public CoordinatorLayout CL;
   static public ImageView FL;
    Toolbar toolbar;
    RecyclerView myColorlist;
    DatabaseHelperclass db;
    NotificationBadge badge;
    static int mNotifCount=0;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        init_product();
        onClick1();
     //   onClickFab();
        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        AdvancedNestedScrollView advancedNestedScrollView = (AdvancedNestedScrollView) findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && oldScrollY > 0) {
                    // rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showOrhideView(View view, boolean isShow) {
        view.animate().alpha(isShow ? 1f : 0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    public void init_product() {
        if (getIntent() != null) {
            ProductData = (ItemData) getIntent().getSerializableExtra("ProductDetail");
            setColor();
            if(ProductData.getMaterial().trim().equals("0")||ProductData.getMaterial().trim().equals("0"))
                imagedetail.setText(ProductData.getName() + "\n\nMaterial : _ \nStyle : _" );
            else
                imagedetail.setText(ProductData.getName() + "\n\nMaterial : " + ProductData.getMaterial() + "\nStyle : " + ProductData.getStyle());

            imagePriza.setText("Rs. " + ProductData.getPrice() + "/-");
            imageColor.setText("Product Color : " + ProductData.getProductcolorlist().get(0).getColorname());
            noOfLikes.setText(ProductData.getLike()+"");
            setFav();
            Picasso.get().load(ProductData.getImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    FL.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            CartD.setQty("1");
            CartD.setImage(ProductData.getImage());
            CartD.setName(ProductData.getName());
            CartD.setId(ProductData.getIdC() + ProductData.getIdP());
            CartD.setPrice(ProductData.getPrice());
            CartD.setColor(ProductData.getProductcolorlist().get(0).getColorname());
            CartD.setSize("M");
            CartD.setColor(ProductData.getProductcolorlist().get(0).getColorname());
        }
    }

    public void setFav() {
        if(db.isFav(ProductData.getIdC() + ProductData.getIdP()))
            fab.setImageResource(R.drawable.fav);
            else
                fab.setImageResource(R.drawable.nfav);
    }

    public void onClick1() {
    }

    public void setImagebackground(int id) {
        //  findViewById(R.id.Fcolor1).setBackgroundResource(R.color.white);
        //findViewById(R.id.Fcolor2).setBackgroundResource(R.color.white);
        //findViewById(R.id.Fcolor3).setBackgroundResource(R.color.white);
        //findViewById(id).setBackgroundResource(R.drawable.border2);

    }


    public void setColor() {
        myColorlist.setHasFixedSize(true);
        myColorlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ColorPickerAdapter adapter = new ColorPickerAdapter(this, ProductData.getProductcolorlist());
        myColorlist.setAdapter(adapter);
    }

    public void init() {
        CartD = new cartData();
        toolbar = findViewById(R.id.toolbar);
        CL = findViewById(R.id.CL);
        FL = findViewById(R.id.frameLayout);
        imagedetail = findViewById(R.id.card_title);
        imagePriza = findViewById(R.id.card_sub_title);
        imageColor = findViewById(R.id.pcolor);
        imageSize = findViewById(R.id.Psize);
        buynow = findViewById(R.id.buyNow);
noOfLikes=findViewById(R.id.noOflikes);
        fab = findViewById(R.id.fab);
        myColorlist = findViewById(R.id.myColorList);
    }

    public void removeFav() {
        fab.setImageResource(R.drawable.nfav);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyFav");
        String id = ProductData.getIdC() + ProductData.getIdP();
       // ProductData.setLike(0);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).removeValue();

        db.removeFromFavroties(ProductData.getIdC() + ProductData.getIdP());

        //chnges in the json
       // if (!ProductData.getHeadertitle().equals("Family"))
            updateJsonCategory(-(1));
        updateJsonProduct(-(1));
        Toast.makeText(this, " Removed from wishlist", Toast.LENGTH_SHORT).show();
    }

    public void addtofav() {
        fab.setImageResource(R.drawable.fav);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyFav");
        String id = ProductData.getIdC() + ProductData.getIdP();
       // ProductData.setLike(1);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(ProductData);
        if (!db.isFav(ProductData.getIdC() + ProductData.getIdP())) {
            db.addToFavorite(ProductData.getIdC() + ProductData.getIdP());
        }
        //chnges in the json
      //  if (!ProductData.getHeadertitle().equals("Family"))
            updateJsonCategory(1);
        updateJsonProduct(1);
        Toast.makeText(this, "added to wishlist", Toast.LENGTH_SHORT).show();
    }

    public void updateJsonProduct(int i) {
        ProductData.setLike(ProductData.getLike()+i);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        firebaseDatabase.child("Product")
                .child(ProductData.getIdP()).setValue(ProductData);
    }

    public void updateJsonCategory(int i) {
        ProductData.setLike(ProductData.getLike()+i);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyCat");
        firebaseDatabase.child("category")
                .child(ProductData.getCategory().toLowerCase())
                .child(ProductData.getHeadertitle())
                .child(ProductData.getIdC())
                .child("listitems")
                .child(ProductData.getIdS()).setValue(ProductData);
    }

    public void onClickFab(View v) {
        if (!db.isFav(ProductData.getIdC() + ProductData.getIdP())) {
            db.addToFavorite(ProductData.getIdC() + ProductData.getIdP());
            addtofav();}
        else {
            db.removeFromFavroties(ProductData.getIdC() + ProductData.getIdP());
            removeFav();
        }

    }

    public void addtocart(View v) {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyCart");
        firebaseDatabase.child(firebaseAuth.getUid()).child(ProductData.getIdC() + ProductData.getIdP()).setValue(CartD).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.setCartSize(1);
                db.setCarttotalAmount(ProductData.getPrice());
                db=new DatabaseHelperclass(getApplicationContext(),firebaseAuth.getUid());
                setNotifCount(db.getcartData("0"));
                Toast.makeText(getApplicationContext(), "added to the cart"+db.getcartData("1"), Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onClick4(View v) {
        Size = "S";
        imageSize.setText("Product Size : " + Size);
        //checkBox.setBackgroundResource(R.drawable.border);
        CartD.setSize(Size);
        setCheckBox(R.id.checkbox1);
    }

    public void onClick5(View v) {
        Size = "M";
        imageSize.setText("Product Size : " + Size);
        CartD.setSize(Size);
        setCheckBox(R.id.checkbox2);
    }

    public void onClick6(View v) {
        Size = "L";
        imageSize.setText("Product Size : " + Size);
        CartD.setSize(Size);
        setCheckBox(R.id.checkbox3);
    }

    public void onClick7(View v) {
        Size = "XL";
        imageSize.setText("Product Size : " + Size);
        CartD.setSize(Size);
        setCheckBox(R.id.checkbox4);
    }

    public void setCheckBox(int id) {
        ((CheckedTextView) findViewById(R.id.checkbox1)).setChecked(false);
        ((CheckedTextView) findViewById(R.id.checkbox1)).setTextColor(getResources().getColor(R.color.black));
        ((CheckedTextView) findViewById(R.id.checkbox2)).setChecked(false);
        ((CheckedTextView) findViewById(R.id.checkbox2)).setTextColor(getResources().getColor(R.color.black));
        ((CheckedTextView) findViewById(R.id.checkbox3)).setChecked(false);
        ((CheckedTextView) findViewById(R.id.checkbox3)).setTextColor(getResources().getColor(R.color.black));
        ((CheckedTextView) findViewById(R.id.checkbox4)).setChecked(false);
        ((CheckedTextView) findViewById(R.id.checkbox4)).setTextColor(getResources().getColor(R.color.black));

        boolean isChecked = ((CheckedTextView) findViewById(id)).isChecked();
        if (isChecked) {
            ((CheckedTextView) findViewById(id)).setChecked(false);
        } else {
            ((CheckedTextView) findViewById(id)).setChecked(true);
            ((CheckedTextView) findViewById(id)).setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void updateproductLikes(final int check) {
        mprocessProduct = true;
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("MyCart");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mprocessProduct) {
                    String likes = "" + dataSnapshot.child("likes").getValue();
                    int newLikeVal;
                    if (check == 1)
                        newLikeVal = Integer.parseInt(likes) + 1;
                    else
                        newLikeVal = Integer.parseInt(likes) - 1;
                    ref.child("likes").setValue("" + newLikeVal);
                    mprocessProduct = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // Don't ignore errors

            }
        });


    }

    public void addToMyORders(String id ,String address, String name, String Number, String timeStamp) {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyOrder");
        List<cartData> orderLists = new ArrayList<>();
        orderLists.add(CartD);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, CartD.getPrice() + "", 0+"", timeStamp, orderLists);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(i);
        View contextView = findViewById(R.id.frameLayout);
        Snackbar snackbar = Snackbar
                .make(contextView, "Your order is placed", Snackbar.LENGTH_LONG);
        snackbar.show();//Toast.makeText(this, "Your order is placed", Toast.LENGTH_SHORT).show();
    }

    public void addToserver(String address, String name, String Number) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyServerOrder");
        String id = firebaseDatabase.push().getKey();
        List<cartData> orderLists = new ArrayList<>();
        orderLists.add(CartD);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, CartD.getPrice() + "", 0+"", timeStamp, orderLists);
        firebaseDatabase.child(id).setValue(i);
        addToMyORders(id,address, name, Number, timeStamp);
    }

    public void selectPaymntMehtod(final String address, final String phoneNo, final String Name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");

        View Submit_ordr_Layout = LayoutInflater.from(this).inflate(R.layout.paymentmethod, null);
        //views to set in dailog
        final TextView UserInfo = Submit_ordr_Layout.findViewById(R.id.userinfo);
        final RadioButton R1 = Submit_ordr_Layout.findViewById(R.id.radio1);
        final RadioButton R2 = Submit_ordr_Layout.findViewById(R.id.radio2);

        UserInfo.setText("Address : " + address + "\nName : " + Name + "\nPhone No : " + phoneNo);
        builder.setView(Submit_ordr_Layout);

        //button recover
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String Address = address;
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
/*        final   AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                if(TextUtils.isEmpty( Name.getText().toString()))
                {
                    Name.setError("Name Required");
                    Name.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"Name Required",Toast.LENGTH_SHORT).show();
                  //  builder.show();
                }
                else
                if(TextUtils.isEmpty( phoneNo.getText().toString())) {
                    phoneNo.setError("PhoneNO Required");
                    phoneNo.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"phone no Required",Toast.LENGTH_SHORT).show();
                    //builder.show();
                     }
                else
                if(TextUtils.isEmpty( address.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"address Required",Toast.LENGTH_SHORT).show();
                    address.setError("Address Required");
                    address.setFocusable(true);
                    //builder.show();
                     }
                else {
                    selectPaymntMehtod(address.getText().toString().trim(), Name.getText().toString().trim(), phoneNo.getText().toString().trim());
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
  */
        List<cartData> orderLists = new ArrayList<>();
        orderLists.add(CartD);
        order=new Order();
order.setProductlist(orderLists);
order.setTotalAmount(CartD.getPrice()+"");
        Intent i = new Intent(this, BuynowActivity.class);
        i.putExtra("cartd", order);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//      this.finish();
      startActivity(i);

       // Toast.makeText(context, itemDataList.get(position).getName(), Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onStart() {
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        setNotifCount(db.getcartData("0"));
        // subscribe();
        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchtoolbar, menu);
        MenuItem item = menu.findItem(R.id.Tcart);
        //  searchView.setMenuItem(item);
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        View v=menu.findItem(R.id.Tcart).getActionView();

        badge=(NotificationBadge) menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.badge);
        menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailActivity.this, MyCart.class);
                finish();
                startActivity(intent);
            }
        });
        if(mNotifCount!=0)
            badge.setText(mNotifCount+"");
        return true;
    }
    private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
    }  @Override
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

