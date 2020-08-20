package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.fyp.Adapter.PageAdapter;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.SliderItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.nex3z.notificationbadge.NotificationBadge;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class extraActivity extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DatabaseHelperclass db;
    NotificationBadge badge;
    static int mNotifCount=0;
    //  private Object Toolbar;

    //   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        toolbar = findViewById(R.id.toolbar2);
firebaseAuth=FirebaseAuth.getInstance();
        //   toolbar.setTitle("Category");
        setSupportActionBar(toolbar);
        // toolbar.setLogo(R.drawable.login);
//        sliderView = findViewById(R.id.imageSlider);
  //     ZeenaysLovelySlider();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Category");
        // toolbar.setTextAlignment();
        FrameLayout f=findViewById(R.id.frameLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Men"));
        tabLayout.addTab(tabLayout.newTab().setText("Women"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);

        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

 //     adapter.check=getIntent().getExtras().getInt("check");
   //     adapter.Itemname=getIntent().getExtras().getString("Itemtype");
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
      // recreate();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        sliderView.setScrollTimeInSec(10);
        sliderView.startAutoCycle();

        List<SliderItem> sliderItems=new ArrayList<>();
        SliderItem sliderItem = new SliderItem();

        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fheels.jpg?alt=media&token=d232be7d-7e89-43ed-b8b3-b32b153a2d24"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fmens.jpg?alt=media&token=69cb06ef-1408-4208-945a-0e0c72c567d5"));

        adapter.renewItems(sliderItems);
//        renewItems(v);
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
                Intent intent = new Intent(extraActivity.this, MyCart.class);
               finish();
               startActivity(intent);
            }
        });
        if(mNotifCount!=0)
            badge.setText(mNotifCount+"");
        Log.i("dbgetdtaa",db.getcartData(0+"")+"  mtok="+mNotifCount);
        //  toolbar_behaviorListener();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.cart: {
     getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
       firebaseAuth=FirebaseAuth.getInstance();
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        setNotifCount(db.getcartData("0"));
        // subscribe();
        super.onStart();
    }
    private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
    }


}
