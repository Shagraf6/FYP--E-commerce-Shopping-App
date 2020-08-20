package com.example.fyp;

import com.example.fyp.Adapter.CategoriesAdapter;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Model.ImageColor;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.SliderItem;
import com.example.fyp.Model.SliderItem2;
import com.example.fyp.Model.cartData;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyItemGroupAdapter;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.catList;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class MultiRecyclerViewActivity extends AppCompatActivity implements IFirebaseLoadListener {
    AlertDialog dialog;
    private ViewPager2 viewPager2;
    static public IFirebaseLoadListener iFirebaseLoadListener;
    static public RecyclerView my_recycler_view;
    static public RecyclerView myCategories;
    static public DatabaseReference myData;
    List<String> titleList;
    static public List<ItemData> ItemData2;
   public static TextView chosentext, title2;
    Toolbar toolbar;
    String CATEGORY;
    String TYPE;
    String NAME;
    private Handler sliderHandler = new Handler();
CollapsingToolbarLayout collapsingToolbar;
AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_recycler_view);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        toolbar = findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //   getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.));
        }

        init();
        ZeenaysLovelySlider2();
        dialog = new SpotsDialog.Builder().setContext(this).build();
        iFirebaseLoadListener = this;
        CategoriesAdapter.prePos = NAME;
        InitializeCat();
        getFirebaseData2(NAME);
        title2.setText("Category");
        imageSlider();

      toolbar.setTitle("Category");

//init();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void toolbar_behaviorListener() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        appBarLayout.setExpanded(true);
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    //         toolbar.setBackgroundColor(getResources().getColor(R.color.black));
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    //menu.findItem(R.id.Tsearch).setVisible(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    isShow = true;
//                     toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                } else if (isShow) {
                    // menu.findItem(R.id.Tsearch).setVisible(true);
                    isShow = false;
                    // toolbar.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });
    }


    SliderView sliderView2;

    public void ZeenaysLovelySlider2()
    {
        SliderAdapterExample adapter = new SliderAdapterExample(getApplicationContext());
        sliderView2.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        // sliderView2.startAutoCycle();
        sliderView2.setIndicatorAnimation(IndicatorAnimations.DROP);
        sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView2.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView2.setIndicatorSelectedColor(Color.WHITE);
        sliderView2.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView2.setScrollTimeInSec(4);
        sliderView2.startAutoCycle();
        List<SliderItem> sliderItems=new ArrayList<>();
        SliderItem sliderItem = new SliderItem();
        //sliderItems.add(new SliderItem(R.drawable.backd));
        //sliderItems.add(new SliderItem(R.drawable.oh_look_at_this)

        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FBanner%2FMen%20athlet.jpg?alt=media&token=4c008d7c-f7b6-47cf-9596-ed3d70003cd3"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/HeelsGIF%2F5G3j.gif?alt=media&token=51181382-d919-4194-af5e-45f8f8d4af70"));
sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FBanner%2FFootwear-Banner-1024x307.jpg?alt=media&token=e310aa7b-61bd-41c3-85ef-4f300fa36dad"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/HeelsGIF%2FDaily%20dose%20of%20inspiration.gif?alt=media&token=4c94f017-b599-40f6-b3e5-10798b49ed66"));
        adapter.renewItems(sliderItems);
//        renewItems(v);
    }


    public void imageSlider() {
        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        //Here, i'm yani men zeenat:p preparing list of images from drawable,
        //you can get it from API as well looooooool.
        List<SliderItem2> sliderItems = new ArrayList<>();
        if (CATEGORY.equals("Men")) {
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F1.jpg?alt=media&token=5a58ae56-ef59-4746-b89b-23388342dc6b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F2.jpg?alt=media&token=30a53a2c-183a-49dc-bc87-241a3dbe6bd5"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F3.png?alt=media&token=67ec1b0c-da73-4088-a21f-898a0e2875cc"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F4.jpg?alt=media&token=7b4e6f2f-7d7d-4731-b8c4-1e748a75d2a8"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F5%20(2).jpg?alt=media&token=288edd8b-1e6c-4bf7-b53d-de076576e032"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F5.jpg?alt=media&token=d072285d-f6ed-48f6-b2d2-5102dd1eecda"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F6.jpg?alt=media&token=bc5d3a37-8731-435c-bc84-14f045e7da2b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F7.png?alt=media&token=7e8822a9-a094-404a-9b90-d2eaf2fb6b04"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F8.png?alt=media&token=fa05dfb9-546a-42c2-a749-903aa73b3a24"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F9.png?alt=media&token=538ad27f-6360-4b52-9d49-7a89695757ab"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F10.jpg?alt=media&token=46f00db3-cb20-4522-9f27-f5a3fa187b31"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F11.png?alt=media&token=327fd748-732f-4c75-b9c5-e6ae86a05f28"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F12.png?alt=media&token=29a5cec4-eaea-4456-9978-cfb0ded72bdf"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F13.png?alt=media&token=031bc49c-77d1-4972-9181-11544f9fb0ae"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F14.jpg?alt=media&token=8812c50a-7842-443a-b8c7-22ea84f9772a"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F15.png?alt=media&token=1d3781fd-633b-41d6-ae34-3bab3b843a68"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F16.png?alt=media&token=23acdbd9-f4d4-4d5b-9553-b1e5f1c68b6f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F17.jpg?alt=media&token=e605d283-5343-4860-91f0-c09240a4ae7f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F18.png?alt=media&token=d6961165-64ab-449c-8aec-a14d17ea7a38"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F19.png?alt=media&token=58ed7f2a-9fc7-4e62-8995-25f98dceba8f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F20.jpg?alt=media&token=fe6cf84c-6271-45b4-a4cd-5b733d8aee6f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F20.png?alt=media&token=456ac1e4-9a2f-49b9-be74-32781b5015b4"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F21.png?alt=media&token=e28b5792-57f8-440a-8ecf-033407cc9f82"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F22.png?alt=media&token=3c6da519-fe91-4008-b162-2d4a9161ca2f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F23.jpg?alt=media&token=7130433d-2c1e-4181-9271-ad378fe37031"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F24.png?alt=media&token=2c28180e-a37e-40fc-870b-309583bf730b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F25.jpg?alt=media&token=bc5eab06-8e58-4956-a1da-782690f90169"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F26.png?alt=media&token=16e76984-9a37-454f-909a-0298eb851b50"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F27.png?alt=media&token=713ce540-ee87-4081-8595-aef5ff0c3d1f"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F28.jpg?alt=media&token=bea632a8-d9e6-4335-9424-74d6c296867b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F29.jpg?alt=media&token=f5bc87aa-584f-4c7d-97d4-246cea1424fa"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F30.jpg?alt=media&token=367d11a5-a299-40aa-b1b0-ee54fa0e63af"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F31.png?alt=media&token=c115dc6e-3809-4c28-9547-9ee651545207"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2F6white%20kameez%20shalwar.jpg?alt=media&token=cafd37dd-f749-4d93-926b-206fd0b0ff88"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2FT-shirt.png?alt=media&token=1363ec49-9f25-4920-9407-458d08107910"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2Fmen%2Fsummer1.jpg?alt=media&token=e6f685cd-78cc-4074-90d7-44cfefd54811"));

        } else {
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F1.jpg?alt=media&token=fde33103-5300-4d3c-b602-73b75566ea5d"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F4.jpg?alt=media&token=74af2432-55e1-4655-8b9d-cbd207906a01"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F5.jpg?alt=media&token=179af1b8-91b7-42be-a1e2-a925a7836b30"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F5.png?alt=media&token=e0975952-48e4-4f79-b7fa-8ba171a91226"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F6.jpg?alt=media&token=c2ecee98-8db8-4630-8df3-0b06b1640171"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F7.png?alt=media&token=012d4157-9d94-4ae4-ac83-ebcc58e5ce88"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F8.jpg?alt=media&token=191e61d5-1fbd-45ab-81fc-575abd667859"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F9.jpg?alt=media&token=28bef095-47fc-4609-a1b3-264995b491df"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F10.jpg?alt=media&token=280e4a4a-3f3b-4539-aab5-bb0b46b71c04"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F11.jpg?alt=media&token=28626285-4d0a-416a-bd2e-fc4935498c15"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F12.jpg?alt=media&token=c857947e-674b-465c-9f61-aa6ccbf25fe5"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F13.jpg?alt=media&token=77c7f504-6e71-43da-b890-218dac7540e4"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F14.jpg?alt=media&token=27601c28-b24f-4bc7-a674-33cdcd703831"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F15.jpg?alt=media&token=97abca7d-e986-4c15-acd2-f977b18da54b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F16.jpg?alt=media&token=afbb7a92-5748-4355-8417-8c31302a95f8"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F17.png?alt=media&token=bc28bcbb-9ee8-4434-a59a-1214ffddc9ed"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F18.jpg?alt=media&token=83eb4aca-53e9-4902-baf2-fda02c5993cf"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F19.png?alt=media&token=bf018c46-70de-41e2-b8eb-9c41b934920e"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F20.jpg?alt=media&token=2bbe6058-5cd7-4088-9bb0-11be873214a6"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F21.jpg?alt=media&token=ed2a7b56-f9d8-4b5f-b8b8-8b7e9b492640"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F22.jpg?alt=media&token=cb163552-c13e-4711-b2ac-4d92bec5e471"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F23.jpg?alt=media&token=3993a56b-76bb-41b1-85c8-9544ffc06d19"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F24.png?alt=media&token=becc35e8-e6a8-4504-878e-ab0e4f15a019"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F25.PNG?alt=media&token=12323163-f807-47e1-8972-fc37f728128b"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F26.jpg?alt=media&token=f420996f-f5fd-47fd-8086-8b37dc6ad9e4"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F27.jpg?alt=media&token=66b14579-8925-4d71-8521-8e908e2a44c0"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F28.jpg?alt=media&token=b97d06b6-1cdd-4250-9c69-1198b2d60c34"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F28.png?alt=media&token=3e2c8aab-e9a5-47c9-9870-f43f6dff3d76"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F29.jpg?alt=media&token=a4b098cc-9ef2-4bbd-af43-238d499f3847"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F30.jpg?alt=media&token=0370208d-cdf3-49f9-a6d8-529961d4bfd1"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F31.jpg?alt=media&token=cf54a6e8-813e-4b5d-b944-d726efab3525"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F32.jpg?alt=media&token=f18a6bcd-2077-419f-b635-ab616eb342ec"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F33.png?alt=media&token=354c1a20-31ac-41dc-abd4-2fa5de4c6f89"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F34.png?alt=media&token=1887645e-b18f-4a45-9e02-f4815fc55403"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F35.jpg?alt=media&token=af3af0e0-b7f6-4658-b798-f5dc0faf76da"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F36.jpg?alt=media&token=5408d35e-3958-4374-bc97-e5d791bebdc4"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F37.png?alt=media&token=9cffff79-fcca-4cac-bdc8-8483c12d6c43"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F38.png?alt=media&token=e2b15eaa-de11-4ea9-af79-aabac5fabed6"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F39.jpg?alt=media&token=ac9ea6fe-447a-4994-97e4-524954adfe51"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F40.jpg?alt=media&token=8512d64a-16df-443d-b395-89b7005bad65"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F41.jpg?alt=media&token=0eba8119-62ca-47af-952c-7dbfb1434c6a"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F42.jpg?alt=media&token=0b589e38-f88f-4e86-bedc-db495d524af5"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F43.jpg?alt=media&token=ee914ddc-1785-45f0-a670-7da75a1ce345"));
            sliderItems.add(new SliderItem2("", "https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Coverimages%20jo%20papa%20ne%20mangyyyy%2FWomen%2F44.jpg?alt=media&token=3e0e8bab-9247-4d26-bfc4-043f35c214e8"));

        }
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.5f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
    }
        private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
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
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private void init() {
        chosentext = findViewById(R.id.chosenText);
        title2 = findViewById(R.id.mycatname);

        if (getIntent() != null) {
            CATEGORY = getIntent().getStringExtra("Category");
            NAME = getIntent().getStringExtra("Name");
            TYPE = getIntent().getStringExtra("Type");
            chosentext.setText(NAME);

        }
        sliderView2=findViewById(R.id.main_gif);
        my_recycler_view = findViewById(R.id.my_Recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        myCategories = findViewById(R.id.myCategories);
   appBarLayout=findViewById(R.id.app_bar);
    }

    public void InitializeCat() {
        myCategories.setHasFixedSize(true);
        myCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<catList> MyCategories;
        //  View v2 = v;
        MyCategories = new ArrayList<>();
        if(CATEGORY.equals("Men"))
        {
            MyCategories.add(new catList("Accessories","Men"));
            MyCategories.add(new catList("All Cloths","Men"));
            MyCategories.add(new catList("All Seasons","Men"));
            MyCategories.add(new catList("Coats and Jackets","Men"));
            MyCategories.add(new catList("Jeans and Pants","Men"));
            MyCategories.add(new catList("Kurta and ShalwarKameez","Men"));
            MyCategories.add(new catList("Men Footwear","Men"));
            MyCategories.add(new catList("New Arrival","Men"));
            MyCategories.add(new catList("Nightwear","Men"));
            MyCategories.add(new catList("Occasions","Men"));
            MyCategories.add(new catList("Sales","Men"));
            MyCategories.add(new catList("Shirts and T-Shirts","Men"));
            MyCategories.add(new catList("Training and Gym","Men"));
            MyCategories.add(new catList("Trousers and Jumpsuits","Men"));
            MyCategories.add(new catList("Unstitched Fabric","Men"));

        }
        else
            MyCategories.add(new catList("Accessories","Women"));
        MyCategories.add(new catList("All Cloths","Women"));
        MyCategories.add(new catList("All Seasons","Women"));
        MyCategories.add(new catList("Coats and Jackets","Women"));
        MyCategories.add(new catList("Hijabs and Abayas","Women"));
        MyCategories.add(new catList("Jeans and Pants","Women"));
        MyCategories.add(new catList("Kurti and ShalwarKameez","Women"));
        MyCategories.add(new catList("New Arrival","Women"));
        MyCategories.add(new catList("Nightwear","Women"));
        MyCategories.add(new catList("Occasions","Women"));
        MyCategories.add(new catList("Sales","Women"));
        MyCategories.add(new catList("Shirts and T-Shirts","Women"));
        MyCategories.add(new catList("Training and Gym","Women"));
        MyCategories.add(new catList("Trousers and Jumpsuits","Women"));
        MyCategories.add(new catList("Unstitched Fabric","Women"));
        MyCategories.add(new catList("Women Footwear","Women"));
        CategoriesAdapter myAdapter = new CategoriesAdapter(getApplicationContext(), MyCategories);
        //DashboardAdapter2 myAdapter2 = new DashboardAdapter2(getContext(), MyCategories);
        //    myrv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        myCategories.setAdapter(myAdapter);

    }

  private void getFirebaseData2(String cat) {
        dialog.show();
        Log.i("getoreas", "*******^^^^^^^^(((******");
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyCat").child("category").child(CATEGORY).child(cat.trim()).addValueEventListener(new ValueEventListener() {
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

//                    ItemData2 = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                }
//                iFirebaseLoadListener.onFirebaseLoadSuccess2(ItemData2);
                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
dialog.dismiss();            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               dialog.dismiss();
               iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv, List<ItemData> itemGroupList) {
        Log.i("succes", "$$$$$");
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyItemGroupAdapter adapter = new MyItemGroupAdapter(getApplicationContext(), itemGroupList);
        my_recycler_view.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
