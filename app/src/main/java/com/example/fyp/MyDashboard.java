package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dashboard);

        // temp();
    }
    public void Onclick1(View v)
    {
        Intent intent = new Intent(this, extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","All Cloths");
        startActivity(intent);

    }
    public void Onclick2(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","New Arrival");
        startActivity(intent);

    }
    public void Onclick3(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Sales");
        startActivity(intent);

    }
    public void Onclick4(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 2);
        intent.putExtra("Itemtype","All Seasons");
        startActivity(intent);

    }
    public void Onclick5(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Shirts and T-Shirts");
        startActivity(intent);

    }
    public void Onclick6(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Trousers");
        startActivity(intent);

    }
    public void Onclick7(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Jeans and Pants");
        startActivity(intent);

    }
    public void Onclick8(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Festivals");
        startActivity(intent);

    }
    public void Onclick9(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 2);
        intent.putExtra("Itemtype","Accessories");
        startActivity(intent);

    }
    public void Onclick10(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","JumpSuits");
        startActivity(intent);

    }
    public void Onclick11(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","NightWear");
        startActivity(intent);

    }
    public void Onclick12(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 2);
        intent.putExtra("Itemtype","WomenFootwear");
        startActivity(intent);

    }
    public void Onclick13(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Unstitched fabric");
        startActivity(intent);

    }
    public void Onclick14(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Rain Wear");
        startActivity(intent);

    }
    public void Onclick15(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Coats and Jackets");
        startActivity(intent);

    }
    public void Onclick16(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 2);
        intent.putExtra("Itemtype","Occassion");
        startActivity(intent);

    }
    public void Onclick17(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Kurti and Shalwar Kameez");
        startActivity(intent);

    }
    public void Onclick18(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Hijab and Abaya");
        startActivity(intent);

    }
    public void Onclick19(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 1);
        intent.putExtra("Itemtype","Scarfs");
        startActivity(intent);

    }
    public void Onclick20(View v)
    {
        Intent intent = new Intent(this,extraActivity.class);
        intent.putExtra("check", 2);
        intent.putExtra("Itemtype","Activity");
        startActivity(intent);

    }
}
