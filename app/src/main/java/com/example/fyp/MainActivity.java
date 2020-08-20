package com.example.fyp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.fyp.Adapter.SliderAdapterExample;
import com.example.fyp.Model.SliderItem;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    SliderAdapterExample adapter;
    SliderView sliderView;
    //FFF99584
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar));
        }
        sliderView = findViewById(R.id.imageSlider);
        ZeenaysLovelySlider();
        CoordinatorLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(getApplicationContext(),LoginOrRegisterActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                finish();
                startActivity(intent, bundle);
            }
        },8000);
    }
    public void ZeenaysLovelySlider() {
        adapter = new SliderAdapterExample(getApplicationContext());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        // sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
 //       sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        //sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(5);
        sliderView.startAutoCycle();

        List<SliderItem> sliderItems = new ArrayList<>();
        SliderItem sliderItem = new SliderItem();
        //sliderItems.add(new SliderItem(R.drawable.backd));
        //sliderItems.add(new SliderItem(R.drawable.oh_look_at_this));
        //   sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/GIF-Folder%2Fanigif.gif?alt=media&token=a62bd391-f35c-421c-afaf-4d5922e8ae21"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/GIF-Folder%2Fanigif.gif?alt=media&token=a62bd391-f35c-421c-afaf-4d5922e8ae21"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/category%2FLogo%2F82148807_269321107842851_2430626092031600601_n.jpg?alt=media&token=f4378255-d02e-45b2-9c0f-fad791b10f7e"));
        sliderItems.add(new SliderItem("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/category%2FLogo%2F82148807_269321107842851_2430626092031600601_n.jpg?alt=media&token=f4378255-d02e-45b2-9c0f-fad791b10f7e"));

        sliderItems.add(sliderItem);
        adapter.renewItems(sliderItems);
//        renewItems(v);
    }

}
