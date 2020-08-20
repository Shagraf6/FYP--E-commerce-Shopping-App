package com.example.fyp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Adapter.MyItemGroupAdapter;
import com.example.fyp.Adapter.RecyclerViewAdapter;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.SliderItem;
import com.example.fyp.Model.cardCover;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.example.fyp.Model.cartData;
import com.nex3z.notificationbadge.NotificationBadge;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import dmax.dialog.SpotsDialog;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.fyp.R.id.ImageSlidr_container;
import static com.example.fyp.R.string.open;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IFirebaseLoadListener {
    TextView tv;
    static AppBarLayout appBarLayout;
    static MaterialSearchView searchView;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    MenuItem item;
    NavigationView navigationView;
    //SearchView searchView;
    private MenuItem Searchitem;
    private MenuItem Cartitem;
    RecyclerView myrv,R2,R3,R4;
    AlertDialog dialog;
    private Menu menu;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view1, rc4,AssersoriesRv,AssersoriesRv2;
    DatabaseReference myData;
    List<String> titleList;
    List<ItemData> ItemData2;
    List<ItemData> ProductData2;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    CollapsingToolbarLayout collapsingToolbar;
   static FloatingActionButton fab;
  static public   FrameLayout contentMain,F1, F2;
static  FragmentTransaction fg;
static  int id=0;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    SharedPreferences sp;
    NotificationBadge badge;
    DatabaseHelperclass db;
    static int mNotifCount=0;
SharedPreferences.Editor editor;
private static final String TOPIC_NOTIFICATION="Notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        callNoificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar));
        }

        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseHelperclass db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        db.createCartDatabase();
        db.createFavDatabase();
        setNotifCount(db.getcartData("0"));
        init();

        IntializeAssersoriesRv();
        iFirebaseLoadListener = this;
    }
    public void onclickM(View v)
    {
        Intent intent = new Intent(this, MultiRecyclerViewActivity.class);
        intent.putExtra("Category", "Men");
        intent.putExtra("Type","W2");
        intent.putExtra("Name","All Cloths");
        startActivity(intent);
    }
    public void onclickW(View v)
    {
        Intent intent = new Intent(this, MultiRecyclerViewActivity.class);
        intent.putExtra("Category", "Women");
        intent.putExtra("Type","W2");
        intent.putExtra("Name","All Cloths");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog = new SpotsDialog.Builder().setContext(this).build();
        getFirebaseData(my_recycler_view1,"Women","All Cloths");
        getFirebaseData(R2,"Women","New Arrival");
        getFirebaseData(R3,"Men","All Cloths");
        getFirebaseData(R4,"Men","All Seasons");
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        setNotifCount(db.getcartData("0"));
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
                    menu.findItem(R.id.Tcart).setVisible(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    isShow = true;
//                     toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                } else if (isShow) {
                    collapsingToolbar.setTitle("S2zfyp");
                   // menu.findItem(R.id.Tsearch).setVisible(true);
                    menu.findItem(R.id.Tcart).setVisible(false);
                    isShow = false;
                    // toolbar.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });
    }



public void setCollapsingToolbarheight(int i) {
    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
    if (i == 0) {
        collapsingToolbar.setLayoutParams(p);
        p.height = CollapsingToolbarLayout.LayoutParams.MATCH_PARENT; // LayoutParams: android.view.ViewGroup.LayoutParams
        collapsingToolbar.setLayoutParams(p);
        collapsingToolbar.requestLayout();//It is necesary to refresh the screen
    }
        if(i==1)
        {
            p.setScrollFlags(0);
            final float scale = getResources().getDisplayMetrics().density;
            int dpHeightInPx = (int) (350 * scale);
            p.height = dpHeightInPx; // LayoutParams: android.view.ViewGroup.LayoutParams
            collapsingToolbar.setLayoutParams(p);
            collapsingToolbar.requestLayout();//It is necesary to refresh the screen

        }

}

    //firebase
    public void getFirebaseData(final RecyclerView rv, String Category, String Catname)
    {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv.setNestedScrollingEnabled(false);//Important

        //rv.setLayoutManager(new LinearLayoutManager(this));
        ItemData2=new ArrayList<>();
        dialog.show();
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyCat").child("category").child(Category).child(Catname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeadertitle(groupSnapshot.child("headertitle").getValue(true).toString());
                    GenericTypeIndicator<List<ItemData>> t = new GenericTypeIndicator<List<ItemData>>() {
                    };
                    itemGroup.setListitems(groupSnapshot.child("listitems").getValue(t));
                    ItemData2 = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                }
               iFirebaseLoadListener.onFirebaseLoadSuccess2(rv,ItemData2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }
    private void getFirebaseData2(RecyclerView rv) {
        dialog.show();
rv.setHasFixedSize(true);
        ItemData2=new ArrayList<>();
        myData=FirebaseDatabase.getInstance().getReference();
        myData.child("MyProducts").child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                    //Toast.makeText(Main2Activity.this, "getfirebase2", Toast.LENGTH_SHORT).show();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //    Toast.makeText(Main2Activity.this, "getfirebase2", Toast.LENGTH_SHORT).show();
                      //      Log.i("getoreas3", "*************");
                            ItemData i = snapshot.getValue(ItemData.class);
                            ProductData2.add(i);
                        }
                 }
//                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
                MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), ProductData2);
                my_recycler_view1.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                my_recycler_view1.setAdapter(myAdapter);
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }


    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList) {
       MyItemAdapter adapter = new MyItemAdapter(this, itemGroupList);
        //rv.setLayoutManager(new GridLayoutManager(Main2Activity.this, 3));
        rv.setAdapter(adapter);
if(dialog!=null)
    dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyItemGroupAdapter adapter = new MyItemGroupAdapter(this, itemGroupList);
    //    rv.setAdapter(adapter);
        if(dialog!=null)
            dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    public List<ItemGroup> getFirebaseFamilyData()
    {
         final List<ItemGroup> itemGroups2 = new ArrayList<>();
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyCat").child("category").child("Family").child("My Family").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeadertitle(groupSnapshot.child("headertitle").getValue(true).toString());
                    GenericTypeIndicator<List<ItemData>> t = new GenericTypeIndicator<List<ItemData>>() {
                    };
                    itemGroup.setListitems(groupSnapshot.child("listitems").getValue(t));
                    ItemData2 = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                    itemGroups2.add(itemGroup);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
        return itemGroups2;
    }
    public void IntializeAssersoriesRv()
    {
        List<cardCover>temp=new ArrayList<>();
        temp.add(new cardCover("Watches",R.drawable.watch,"Hair Accessories",R.drawable.haira,"Women"));
        temp.add(new cardCover("Bags",R.drawable.bag,"Belts",R.drawable.belts,"Women"));
        temp.add(new cardCover("Sunglasses",R.drawable.glases,"Jewellery",R.drawable.jewelry,"Women"));
        temp.add(new cardCover("Clutches",R.drawable.clutch,"Keyholders",R.drawable.keychain,"Women"));
        temp.add(new cardCover("Sports Accessories",R.drawable.sa,"Umbrella",R.drawable.umb,"Women"));
        temp.add(new cardCover("Brooches and Beauty",R.drawable.rings,"Jewellery",R.drawable.jewelry,"Women"));

       AssersoriesRv.setHasFixedSize(true);
        AssersoriesRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
     //   AssersoriesRv.setNestedScrollingEnabled(false);//Important
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,temp);
  //     AssersoriesRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        AssersoriesRv.setAdapter(adapter);


       temp=new ArrayList<>();
        temp.add(new cardCover("Watches",R.drawable.brownwatch,"Wallets",R.drawable.brownwallet,"Men"));
        temp.add(new cardCover("Bags",R.drawable.mbag,"Belts",R.drawable.brownbelt,"Men"));
        temp.add(new cardCover("Sunglasses",R.drawable.glassesm,"Ties and Bow-ties",R.drawable.tie,"Men"));
        temp.add(new cardCover("Hats and Gloves",R.drawable.hat,"Socks",R.drawable.graysock,"Men"));
        temp.add(new cardCover("Brooches",R.drawable.broches,"Cufflink",R.drawable.cuffling,"Men"));
        AssersoriesRv2.setHasFixedSize(true);
        AssersoriesRv2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        RecyclerViewAdapter adapter2=new RecyclerViewAdapter(this,temp);
        AssersoriesRv2.setAdapter(adapter2);

    }
    SliderAdapterExample adapter;
    SliderView sliderView;
    SliderView sliderView2;
    public void ZeenaysLovelySlider() {
        adapter = new SliderAdapterExample(getApplicationContext());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.DROP);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();
        List<SliderItem> sliderItems = new ArrayList<>();
        SliderItem sliderItem = new SliderItem();
        //sliderItems.add(new SliderItem(R.drawable.backd));
        //sliderItems.add(new SliderItem(R.drawable.oh_look_at_this));

        sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Ffashion%20heels.jpg?alt=media&token=880efd83-1db8-4cee-882b-9c918b709235");
        sliderItems.add(sliderItem);
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Spring%20season%20sales%20instagram%20post%20advertisement%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=eea80c2e-2ec6-448f-b823-db13089f4f41"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FBanner%2Fmen%20shoes.jpg?alt=media&token=1d08affe-c380-4b34-9dda-f7cbfe5c94b1"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FPoster%2FFashion%20Sale.jpg?alt=media&token=4c7f9720-7df2-4a19-80cd-08f77ce87a2b"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Bottle%20Green%20Bold%20Typeface%20Sale%20Flyer%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=9d6101dd-fac0-4c19-8804-9beff8266d29"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FBanner%2FMen%20shirts%20With%20Jeans.jpg?alt=media&token=e09fb527-41d2-42fd-9fc7-51b4e0be9ac5"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Spring%20sale%20flyer%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=1ddc6a3b-dab2-4990-a6d5-666080a74089"));


        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20mens%20clothing%20sale%20portrait%20poster%20template%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=77b4bf51-3f9d-4340-8610-35f58297ea71"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Spring%20sale%20advertisement%20instagram%20post%20temp%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=d8c5dc59-dd71-4763-a6c8-3b2c35ed5f88"));
//        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Ffashion%20heels.jpg?alt=media&token=880efd83-1db8-4cee-882b-9c918b709235"));

        adapter.renewItems(sliderItems);
//        renewItems(v);
    }
    public void ZeenaysLovelySlider2()
    {
   SliderAdapterExample     adapter = new SliderAdapterExample(getApplicationContext());
        sliderView2.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
       // sliderView2.startAutoCycle();
        sliderView2.setIndicatorAnimation(IndicatorAnimations.DROP);
        sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView2.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView2.setIndicatorSelectedColor(Color.WHITE);
        sliderView2.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView2.setScrollTimeInSec(10);
        sliderView2.startAutoCycle();
        List<SliderItem> sliderItems=new ArrayList<>();
        SliderItem sliderItem = new SliderItem();
        //sliderItems.add(new SliderItem(R.drawable.backd));
        //sliderItems.add(new SliderItem(R.drawable.oh_look_at_this));


        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/HeelsGIF%2F561b08a0-b9ab-4448-9dcd-a92bd380e7ec.gif?alt=media&token=b834b119-8ccf-4d63-9b1e-96a94ee727b9"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/category%2FLogo%2F82148807_269321107842851_2430626092031600601_n.jpg?alt=media&token=f4378255-d02e-45b2-9c0f-fad791b10f7e"));

        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/gif%20men%20converted%20video%20lol%2Fwomen-video-2.gif?alt=media&token=07cdc5a2-c89d-44f5-a1da-767b4196697f"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/category%2FLogo%2F82148807_269321107842851_2430626092031600601_n.jpg?alt=media&token=f4378255-d02e-45b2-9c0f-fad791b10f7e"));


     sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/gif%20men%20converted%20video%20lol%2FMen-video.gif?alt=media&token=c9ffeec2-da8a-4ef1-89cc-d5b0a14964cb"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/category%2FLogo%2F82148807_269321107842851_2430626092031600601_n.jpg?alt=media&token=f4378255-d02e-45b2-9c0f-fad791b10f7e"));
       adapter.renewItems(sliderItems);
//        renewItems(v);
    }

    //initialization
    public void init() {
        fab = findViewById(R.id.fab);
        tv = findViewById(R.id.tv);
        my_recycler_view1 = findViewById(R.id.RC1);
        R2 = findViewById(R.id.myRC2);
        R3 = findViewById(R.id.myRC3);
        R4 = findViewById(R.id.myRC4);
        sliderView = findViewById(R.id.imageSlider);
        sliderView2=findViewById(R.id.main_gif);
        ZeenaysLovelySlider();
        ZeenaysLovelySlider2();
        AssersoriesRv=findViewById(R.id.myAssersoriesRv);
        AssersoriesRv2=findViewById(R.id.myAssersoriesRv2);

        ItemData2 = new ArrayList<>();
        ProductData2 = new ArrayList<>();
  //      F1=findViewById(ImageSlidr_container);
       contentMain=findViewById(R.id.frameLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
      //  searchView = (MaterialSearchView) findViewById(R.id.search_view);
        toolbar = findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.myDrawer);

        navigationView = findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(this);
//TextView id=        navigationView.findViewById(R.id.emailid);
//        id.setText(firebaseAuth.getCurrentUser().getEmail());
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        getSupportActionBar().setTitle("");


if (getSupportFragmentManager().getBackStackEntryCount() !=0)
    getSupportFragmentManager().popBackStack();
   // getSupportFragmentManager().beginTransaction().replace(ImageSlidr_container, new HomeFragment()).addToBackStack(null).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch ((item.getItemId())) {
                case R.id.nav_home:
                 //   startActivity(new Intent(Main2Activity.this, SearchActivity.class));
                    break;
                case R.id.search:
                    startActivity(new Intent(Main2Activity.this, SearchActivity.class));
                    break;
                case R.id.nav_favorites:
                    startActivity(new Intent(Main2Activity.this, FavActivity.class));
                    break;
                case R.id.nav_cat:
                    //        selectedFragment = new SearchFragment();
                    startActivity(new Intent(Main2Activity.this, extraActivity.class));
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
        super.onDestroy();
    }
    @Override
    public void onStop()
    {
        if (dialog != null) { dialog.dismiss(); dialog = null; }
    super.onStop();
    }

    public void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        tv.clearFocus();
        if (user != null) {
            tv.setFocusable(true);
            tv.setText(user.getEmail());

        }
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        Log.i("@@@@@@" ,""+getSupportFragmentManager().getBackStackEntryCount());
       /* if (getSupportFragmentManager().getBackStackEntryCount() !=0&& id == 1) {
            getSupportFragmentManager().popBackStack();
           Toast.makeText(getApplicationContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
            if (id == 1) {
               // searchView.closeSearch();
                appBarLayout.setExpanded(true);
                contentMain.setVisibility(View.VISIBLE);
                setCollapsingToolbarheight(1);
                getSupportFragmentManager().beginTransaction().replace(ImageSlidr_container, new HomeFragment()).addToBackStack(null).commit();
                fab.setVisibility(View.VISIBLE);
                id=0;
            }
          /*  if(searchView.isSearchOpen()) {
                searchView.closeSearch();
            }
        }
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }             // or just go back to main activity
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0 && id != 1)
         finish();

*/
super.onBackPressed();
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
       // Toast.makeText(Main2Activity.this, "Logging Out", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.Famiy:
                Intent i = new Intent(Main2Activity.this, MyFamily.class);
                startActivity(i);
                break;
            case R.id.mychoice:
              intent = new Intent(Main2Activity.this, MyChoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.myorders:
                intent = new Intent(Main2Activity.this, MyOrdersActivity.class);
                startActivity(intent);
                break;
            case R.id.ShareApp:
                String message="fyp link";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
break;
            case R.id.category:
                 intent = new Intent(Main2Activity.this, extraActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                intent = new Intent(Main2Activity.this, SettingActivity.class);
                startActivity(intent);
//showsettinglayout();
break;
 case R.id.LogOut:
                firebaseAuth.signOut();
                checkUserStatus();
                Toast.makeText(Main2Activity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
public void onclick(View v)
{
    Intent intent = new Intent(Main2Activity.this, MyCart.class);
    startActivity(intent);

}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Tcart:
                Intent intent = new Intent(Main2Activity.this, MyCart.class);
                startActivity(intent);
                return true;
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    
//settings part
    public void setSwitchState(SwitchCompat Sc) {
        sp = getSharedPreferences("Notification_sp", MODE_PRIVATE);
        boolean isNotificationEnabled = sp.getBoolean("" + TOPIC_NOTIFICATION, false);
        if (isNotificationEnabled)
            Sc.setChecked(true);
        else
            Sc.setChecked(false);
    }private void showsettinglayout()
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Setting");

        View Submit_ordr_Layout = LayoutInflater.from(this).inflate(R.layout.settings, null);
        //views to set in dailog
        final TextView chagePassword = Submit_ordr_Layout.findViewById(R.id.changepass);
        final SwitchCompat notificationSwitch = Submit_ordr_Layout.findViewById(R.id.notificationSwitch);
    chagePassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showRecoverPasswordDialog();
        }
    });
     setSwitchState(notificationSwitch);
        builder.setView(Submit_ordr_Layout);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               editor=sp.edit();
               editor.putBoolean(""+TOPIC_NOTIFICATION,isChecked);
               editor.apply();
                if(isChecked)
                {
                    subscribe();
                }
                else
                {
                    unsubscribeNotification();
                }
            }
        });
        //button recover
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
            }
        });
        //button cancel
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();

            }
        });
        builder.create().show();
    }
private void callNoificationChannel()
{
    NotificationChannel channel= null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        channel = new NotificationChannel(TOPIC_NOTIFICATION,TOPIC_NOTIFICATION, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager= null;
        manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}

    private void subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic(""+TOPIC_NOTIFICATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "You will recieve app Notifications";
                        if (!task.isSuccessful()) {
                            msg = "Subscription Failed";
                        }
                        Toast.makeText(Main2Activity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unsubscribeNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(""+TOPIC_NOTIFICATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "You will not recieve app Notifications";
                        if (!task.isSuccessful()) {
                            msg = "Un Subscription Failed";
                        }
                        Toast.makeText(Main2Activity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showRecoverPasswordDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        //set layout Linearlayout
        LinearLayout linearLayout = new LinearLayout(this);
//views to set in dailog
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);


        linearLayout.addView(emailEt);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        //button recover
        builder.setPositiveButton("Update Password ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);

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
    private void beginRecovery(String email) {

        dialog.setMessage("Sending email....");
        dialog.show();
      firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                //get and show proper error message
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
/*    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void doIncrease() {
        count++;
        invalidateOptionsMenu();
    }*/

    @Override
    protected void onStart() {
        checkUserStatus();
        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        setNotifCount(db.getcartData("0"));
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        navigationView = findViewById(R.id.navView);
        navigationView.setCheckedItem(R.id.home);
        // subscribe();
        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchtoolbar, menu);
        this.menu = menu;
        MenuItem item = menu.findItem(R.id.Tcart);
        //  searchView.setMenuItem(item);

        db=new DatabaseHelperclass(this,firebaseAuth.getUid());
        View v=menu.findItem(R.id.Tcart).getActionView();
        badge=(NotificationBadge) menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.badge);
        menu.findItem(R.id.Tcart).getActionView().findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MyCart.class);
                startActivity(intent);
            }
        });
        if(mNotifCount!=0)
            badge.setText(mNotifCount+"");
        Log.i("dbgetdtaa",db.getcartData(0+"")+"  mtok="+mNotifCount);
      //  toolbar_behaviorListener();
        return true;
    }
    private void setNotifCount(int count){
    mNotifCount = count;
    invalidateOptionsMenu();
}
}

