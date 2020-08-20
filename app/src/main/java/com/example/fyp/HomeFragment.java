package com.example.fyp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fyp.Adapter.SliderAdapter;
import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Model.SliderItem;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment {
   // ImageSlider imageSlider;
   // SliderAdapter Sa;
   //private Handler sliderHandler= new Handler();
   // private ViewPager2 viewPager2;

    SliderAdapterExample adapter;
    SliderView sliderView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_home,container,false);
     // imageSlider = v.findViewById(R.id.slider);
       // slider();
        sliderView = v.findViewById(R.id.imageSlider);
        ZeenaysLovelySlider();
return v;    }

    public void slider() {
        ////ImageSlider imageSlider2=findViewById(R.id.slider2);
        //ImageSlider imageSlider3=findViewById(R.id.slider3);

        List<SlideModel> slideModels = new ArrayList<>();
       // imageSlider.setImageList(slideModels, true);
        //imageSlider2.setImageList(slideModels,true);
        // imageSlider3.setImageList(slideModels,true);

    }
    public void ZeenaysLovelySlider()
    {
        adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.SWAP);
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
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Summer%20Sale%20flyer%20template%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=4411c11e-9363-447e-b4b0-00182a4c581d"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Bottle%20Green%20Bold%20Typeface%20Sale%20Flyer%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=9d6101dd-fac0-4c19-8804-9beff8266d29"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20mens%20clothing%20sale%20portrait%20poster%20template%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=77b4bf51-3f9d-4340-8610-35f58297ea71"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Spring%20sale%20advertisement%20instagram%20post%20temp%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=d8c5dc59-dd71-4763-a6c8-3b2c35ed5f88"));
        //sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Fheels.jpg?alt=media&token=d232be7d-7e89-43ed-b8b3-b32b153a2d24"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Ffashion%20heels.jpg?alt=media&token=880efd83-1db8-4cee-882b-9c918b709235"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20sale%20usletter%20template%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=3dfe9ee7-6720-4cda-bbf5-76daa422853f"));


        sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Ffashion%20heels.jpg?alt=media&token=880efd83-1db8-4cee-882b-9c918b709235");
     sliderItems.add(sliderItem);
        adapter.renewItems(sliderItems);
//        renewItems(v);
    }

}
