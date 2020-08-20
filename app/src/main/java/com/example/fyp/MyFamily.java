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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.ImageColor;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.SliderItem;
import com.example.fyp.Model.SliderItem2;
import com.example.fyp.Model.cartData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyFamily extends AppCompatActivity implements IFirebaseLoadListener {
    AlertDialog dialog;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;
    ItemGroup ProductData;
    Toolbar toolbar;
    String CATEGORY;
    String TYPE;
    String NAME;
    TextView title;
    List<ItemData>  itemData;
    List<ItemData> ItemData2;
    private ViewPager2 viewPager2;
    private Handler sliderHandler= new Handler();
    List<SliderItem2> sliderItems;
    List<ImageColor> coverImagelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        //setContentView(R.layout.activity_single_recyler_view);
         init();
        myData=FirebaseDatabase.getInstance().getReference();
        dialog = new SpotsDialog.Builder().setContext(this).build();
        iFirebaseLoadListener=this;
        toolbar=findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title= findViewById(R.id.mycatname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         getFirebaseData2();
        toolbar.setTitle("My Family");
        title.setText("My Family");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

//init();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

        public void imageSlider()
        {
            viewPager2=findViewById(R.id.viewPagerImageSlider);
            //Here, i'm yani men zeenat:p preparing list of images from drawable,
            //you can get it from API as well looooooool.
            sliderItems=new ArrayList<>();
            for (int i=0;i<coverImagelist.size();i++)
                sliderItems.add(new SliderItem2(coverImagelist.get(i).getColorname(),coverImagelist.get(i).getImageurl()));
            viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(3);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(30));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float r=1-Math.abs(position);
                    page.setScaleY(0.95f + r * 0.4f);
                }
            });
            viewPager2.setPageTransformer(compositePageTransformer);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable,3000);
                }
            });
        }
        private Runnable sliderRunnable=new Runnable() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        };
        @Override
        protected void onPause() {
            super.onPause();
            sliderHandler.removeCallbacks(sliderRunnable);
        }
        @Override
        protected void onResume() {
            super.onResume();
            sliderHandler.postDelayed(sliderRunnable,3000);
        }

        private void init()
        {
            ProductData=new ItemGroup();
            my_recycler_view=findViewById(R.id.my_Recycler_view222);
            my_recycler_view.setHasFixedSize(true);
            my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
           /* if(getIntent()!=null)
            {
                ProductData = (ItemGroup) getIntent().getSerializableExtra("ProductDetail");
                coverImagelist=new ArrayList<>();
                coverImagelist= ProductData.getCoverimagelist();
                CATEGORY=getIntent().getStringExtra("Category");
                NAME=getIntent().getStringExtra("Name");
                TYPE =getIntent().getStringExtra("Type");
                Log.i("coveriagelist***","count=>"+ProductData.getCoverimagelist().size());
                imageSlider();
                toolbar.setTitle(""+ProductData.getHeadertitle());
                //      MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), ProductData.getListitems());
                //    my_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                //  my_recycler_view.setAdapter(myAdapter);
            }*/
        }
        private void getFirebaseData() {
            dialog.show();
            my_recycler_view.setHasFixedSize(true);
            my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
coverImagelist=new ArrayList<>();
            ItemData2=new ArrayList<>();
            myData.child("MyCat").child("category").child("Family").child("My Family").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<ItemGroup> itemGroups = new ArrayList<>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Toast.makeText(getApplicationContext(), "getfirebase2", Toast.LENGTH_SHORT).show();
                            Log.i("getoreas3", "*************");
                            ItemData i = snapshot.getValue(ItemData.class);
                            Log.i("incart", "$$$$");
                            ItemData2.add(i);
                        }
                    }        //List<Object> routes = dataSnapshot.getValue(t);

                    //  iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
                    MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), ItemData2);
                    my_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    my_recycler_view.setAdapter(myAdapter);
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
                }
            });
        }



    private void getFirebaseData2() {
        dialog.show();
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        myData = FirebaseDatabase.getInstance().getReference();
        coverImagelist=new ArrayList<>();
        ItemData2=new ArrayList<>();
        myData.child("MyCat").child("category").child("Family").child("My Family").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeadertitle(groupSnapshot.child("headertitle").getValue(true).toString());
                    //GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator=new GenericTypeIndicator<>();
                    Log.i("getoreas", "*************" + groupSnapshot.child("headertitle").getValue(true).toString());
                    GenericTypeIndicator<List<ItemData>> t = new GenericTypeIndicator<List<ItemData>>() {
                    };
                    //List<Object> routes = dataSnapshot.getValue(t);
                    itemGroup.setListitems(groupSnapshot.child("listitems").getValue(t));
                    GenericTypeIndicator<List<ImageColor>> I = new GenericTypeIndicator<List<ImageColor>>() {
                    };
                    itemGroup.setCoverimagelist(groupSnapshot.child("coverimagelist").getValue(I));
coverImagelist=groupSnapshot.child("coverimagelist").getValue(I);
                    ItemData2 = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                }
                MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), ItemData2);
                my_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                my_recycler_view.setAdapter(myAdapter);
                if(coverImagelist.size()>0)
                    imageSlider();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

        @Override
        public void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList) {
            MyItemAdapter adapter =new MyItemAdapter(this,itemGroupList);
            my_recycler_view.setAdapter(adapter);
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
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }

    }


