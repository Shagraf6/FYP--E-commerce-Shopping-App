package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fyp.Adapter.MyORderdtaillAdapter;
import com.example.fyp.Adapter.MyOrdersAdapter;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView Rview;
    MyORderdtaillAdapter myAdapter;
    Toolbar toolbar;

    Order OrderdetailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

 Init();
        Rview.setLayoutManager(new LinearLayoutManager(this));//   Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        myAdapter = new MyORderdtaillAdapter(getApplicationContext(),OrderdetailList.getProductlist());
        Rview.setAdapter(myAdapter);
    }

    public void Init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Order Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        OrderdetailList=new Order();
        if (getIntent() != null) {
            OrderdetailList = (Order) getIntent().getSerializableExtra("OrderDetail");
        }
        Rview=findViewById(R.id.myOrderdetailrecyclerview_id);
    }
}
