package com.example.fyp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fyp.Adapter.DashboardAdapter;
import com.example.fyp.Adapter.DashboardAdapter2;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.ItemData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.fyp.Model.Dashboard_item;
import com.example.fyp.Model.SliderItem;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AlertDialog dialog;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_rc1,my_rc2;
    DatabaseReference myData;
    List<String> titleList;
    List<ItemData> ItemData2;
    String Category,ItemName;
    //RecyclerView myrv;
    SliderAdapter Sa;
    SliderAdapterExample adapter;
    private ViewPager2 viewPager2;
    SliderView sliderView;
    private Handler sliderHandler= new Handler();
    View v=null;
    public Tab2Fragment() {
        // Required empty public constructor
    }
    public Tab2Fragment(String category ,String Itemname) {
        // Required empty public constructor
        Category=category;
        ItemName=Itemname;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ///    getActivity().getActionBar().hide();
        myData = FirebaseDatabase.getInstance().getReference();
        //myData.child("MyData");
        dialog = new SpotsDialog.Builder().setContext(getContext()).build();
        // iFirebaseLoadListener=this;
        // init();
        // getFirebaseData2();
    }
    public void temp() {
        List<Dashboard_item> MyCategories;
        MyCategories = new ArrayList<>();
        MyCategories.add(new Dashboard_item("All Cloths", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("New Arrival", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Sales", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("All Seasons", R.drawable.barbie,2,"Women"));
        MyCategories.add(new Dashboard_item("Shirts and T-Shirts", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Trousers and Jumpsuits", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Jeans and Pants", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Accessories", R.drawable.barbie,2,"Women"));

        DashboardAdapter myAdapter = new DashboardAdapter(getContext(), MyCategories);
        my_rc1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        my_rc1.setAdapter(myAdapter);

        MyCategories = new ArrayList<>();
        MyCategories.add(new Dashboard_item("Nightwear", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Women Footwear", R.drawable.barbie,2,"Women"));
        MyCategories.add(new Dashboard_item("Unstitched Fabric", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Coats and Jackets", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Occassions", R.drawable.barbie,2,"Women"));
        MyCategories.add(new Dashboard_item("Kurti and ShalwarKameez", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Hijabs and Abayas", R.drawable.barbie,1,"Women"));
        MyCategories.add(new Dashboard_item("Training and Gym", R.drawable.barbie,1,"Women"));

        DashboardAdapter myAdapter2 = new DashboardAdapter(getContext(), MyCategories);
        my_rc2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        my_rc2.setAdapter(myAdapter2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tab2, container, false);
        sliderView = v.findViewById(R.id.imageSlider);
        //  viewPager2=v.findViewById(R.id.viewPagerImageSlider);
        my_rc1 = (RecyclerView) v.findViewById(R.id.rc1);
        my_rc2 = (RecyclerView) v.findViewById(R.id.rc2);
        init();
        temp();
        ZeenaysLovelySlider();
        return v;
    }
    private void init()
    {
        my_rc1.setHasFixedSize(true);
        my_rc1.setLayoutManager(new LinearLayoutManager(getContext()));
        my_rc2.setHasFixedSize(true);
        my_rc2.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void ZeenaysLovelySlider()
    {
        adapter = new SliderAdapterExample(getContext());
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
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fheels.jpg?alt=media&token=d232be7d-7e89-43ed-b8b3-b32b153a2d24"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fmens.jpg?alt=media&token=69cb06ef-1408-4208-945a-0e0c72c567d5"));
sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/ALL%20POSTER%20and%20COVER%2FBanner%2FMen%20Fashion.jpg?alt=media&token=1e59fc95-4168-40ec-935c-2737f722f984") );
        adapter.renewItems(sliderItems);
//        renewItems(v);
    }


    private Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
}
