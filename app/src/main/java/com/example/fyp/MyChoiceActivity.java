package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Adapter.FilterAdapter;
import com.example.fyp.Adapter.MyCartAdapter;
import com.example.fyp.Adapter.MyItemAdapter;
import com.example.fyp.Interface.IFirebaseLoadListener;
import com.example.fyp.Model.FilterObject;
import com.example.fyp.Model.FilterRvdata;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.cartData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyChoiceActivity extends AppCompatActivity implements IFirebaseLoadListener {
    //RecyclerView
    static public List<FilterObject> SelectedOptionslist;
   static int check=0;
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView dropDownText;
    private TextInputLayout textInputLayout2;
    private AutoCompleteTextView dropDownText2;
    static String TotalPRize;
    RecyclerView Rview;
    Toolbar toolbar;
//    CoordinatorLayout frame
    Handler handler;
    FirebaseAuth firebaseAuth;
    List<ItemData> FilterDataList;
    DatabaseReference FilterData;
    public static TextView price;
    FilterAdapter myAdapter;
    List<FilterRvdata> F_RV;
  android.app.AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View Submit_ordr_Layout;
    String[] items;
    String[] items2;
    String filtertec="";
    TextView fitertext;
    String selectedCat;
    public static int filterchecker=0;
    public static String chosenColor="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_choice);
        init();
//        dialog = new SpotsDialog.Builder().setContext(this).build();
        alertDialog = new SpotsDialog.Builder().setContext(this).build();

        initializeCatList();
        initializeSortingList();
        dropDownText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    dropDownText.setText("",false);
                    dropDownText2.setText("",false);
                }
            }
        });
        dropDownText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    dropDownText2.setText("",false);
                }
            }
        });
        onclick();
        dropDownText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    dropDownText2.setText("",false);
                }
                SelectedOptionslist.clear();
                SelectedOptionslist.add(0, new FilterObject("Category", items[position], 0));
                selectedCat = items[position];
                  getFilteredData();
                dropDownText.clearFocus();

            }
        });
        dropDownText2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    sortFirebaseData("price");
                if(position==1)
                    sortFirebaseData("name");
                dropDownText2.clearFocus();
            }
        });

    }
    public void onclick()
    {
        if (dropDownText.isPopupShowing())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                dropDownText.setText("",false);
                dropDownText2.setText("",false);
            }
        }
        if (dropDownText2.isPopupShowing())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                dropDownText2.setText("",false);
            }
        }
        Log.i("ppopup","");
    }
    public void initializeSortingList() {

        textInputLayout2 = findViewById(R.id.text_input_layout2);
        dropDownText2 = findViewById(R.id.dropdown_text2);

        items2 = new String[]{
                "By Price",
                "By Title"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_item,
                items2
        );

        dropDownText2.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        filterchecker=0;
        super.onPause();
    }
    @Override
    public void onStop()
    {
        filterchecker=0;
        super.onStop();
    }

    public void initializeCatList() {

        textInputLayout = findViewById(R.id.text_input_layout);
        dropDownText = findViewById(R.id.dropdown_text);

        items = new String[]{
                "View All",
                "All Cloths",
                "New Arrival",
                "Sales",
                "All Seasons",
                "Shirts and T-Shirts",
                "Trousers and Jumpsuits",
                "Jeans and Pants",
                "Accessories",
                "Nightwear",
                "Women Footwear",
                "Unstitched Fabric",
                "Coats and Jackets",
                "Occasions",
                "Kurti and ShalwarKameez",
                "Hijabs and Abayas",
                "Training and Gym"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_item,
                items
        );

        dropDownText.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dropDownText.setText(items[0], false);
            SelectedOptionslist.add(0, new FilterObject("Category", "View All", 0));
            selectedCat = items[0];
            getFilteredData();
        }
    }

    public void onClick(View v) {
        SelectedOptionslist.clear();
        SelectedOptionslist.add(0, new FilterObject("Category", selectedCat, 0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dropDownText2.setText("",false);
        }


        builder = new AlertDialog.Builder(MyChoiceActivity.this);
        builder.setTitle("Filter");

        Submit_ordr_Layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_activity, null);
        //views to set in dailog
        final RecyclerView RV = Submit_ordr_Layout.findViewById(R.id.filter_rv);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        final Button filter_btn = Submit_ordr_Layout.findViewById(R.id.fiterdone_btn);
        // RV.setLayoutManager(new GridLayoutManager(getContext(), 3));
        initializeFilterLists();
        RV.setAdapter(myAdapter);

        builder.setView(Submit_ordr_Layout);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedOptionslist.clear();
                filterchecker=0;
                SelectedOptionslist.add(0, new FilterObject("Category", selectedCat, 0));
                myAdapter = new FilterAdapter(getApplicationContext(), F_RV);
                RV.setAdapter(myAdapter);
//        builder.setView(Submit_ordr_Layout);

            }
        });

        //button recover
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Log.i("length", SelectedOptionslist.size() + "");
                filterchecker=1;
                getFilteredData();

            }
        });
        //button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //recreate();
                filterchecker=0;
                dialog.dismiss();

            }
        });
        builder.create().show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void sortFirebaseData(String queryString) {
        FilterDataList = new ArrayList<>();
        alertDialog.show();
        FilterData = FirebaseDatabase.getInstance().getReference();
        //     Toast.makeText(Main2Activity.this, "getfirebase222", Toast.LENGTH_SHORT).show();
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").child("Product")
                .orderByChild(queryString);
        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
     //       Log.i("getoreas1", "*************");
///            Toast.makeText(Main2Activity.this, "getfirebase2", Toast.LENGTH_SHORT).show();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemData i = snapshot.getValue(ItemData.class);
                    if (iterator(i))
                        FilterDataList.add(i);
                  }
                onFirebaseLoadSuccess2(Rview,FilterDataList);
           alertDialog.dismiss();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
alertDialog.dismiss();
        }
    };
    public void initializeFilterLists() {
        F_RV = new ArrayList<>();
        ArrayList<String> optionslist = new ArrayList<>();
        optionslist.add("Blue");
        optionslist.add("DarkBlue");
        optionslist.add("LightBlue");
        optionslist.add("Green");
        optionslist.add("LightGreen");
        optionslist.add("DarkGreen");
        optionslist.add("SeaGreen");
        optionslist.add("Pink");
        optionslist.add("LightPink");
        optionslist.add("DarkPink");
        optionslist.add("Black");
        optionslist.add("Gray");
        optionslist.add("Orange");
        optionslist.add("White");
        optionslist.add("OffWhite");
        optionslist.add("Brown");
        optionslist.add("LightBrown");
        optionslist.add("DarkBrown");
        optionslist.add("Golden");
        optionslist.add("Gold");
        optionslist.add("Silver");
        optionslist.add("Purple");
        optionslist.add("LightPurple");
        optionslist.add("DarkPurple");
        optionslist.add("Yellow");
        optionslist.add("Red");
        optionslist.add("Peach");
        optionslist.add("Magenta");
        optionslist.add("Lemon");
        F_RV.add(new FilterRvdata("Color", optionslist));
        optionslist = new ArrayList<>();
        optionslist.add("Fashion");
        optionslist.add("Casual");
        optionslist.add("Maxidress");
        optionslist.add("Long Sleeve Dress");
        optionslist.add("Sleeveless");
        optionslist.add("Short Sleeve");
        optionslist.add("Lace Dress");
        optionslist.add("Frock");
        optionslist.add("Long Shirt");
        optionslist.add("Short Shirt");
        F_RV.add(new FilterRvdata("Style", optionslist));

        optionslist = new ArrayList<>();
        optionslist.add("Cotton");
        optionslist.add("Silk");
        optionslist.add("Lawn");
        optionslist.add("Chiffon");
        optionslist.add("Nylon");
        optionslist.add("Satin");
        optionslist.add("velvet");
        F_RV.add(new FilterRvdata("Material", optionslist));
        optionslist = new ArrayList<>();
        optionslist.add("Low Price");
        optionslist.add("High Price");
        F_RV.add(new FilterRvdata("Price", optionslist));
        myAdapter = new FilterAdapter(getApplicationContext(), F_RV);
    }

    public void init() {
        Rview = findViewById(R.id.fILTERrecyclerview_id);
        Rview.setHasFixedSize(true);
        Rview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SelectedOptionslist = new ArrayList<>();
        toolbar = findViewById(R.id.toolbarid);
        fitertext=findViewById(R.id.fitertect);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //   price=findViewById(R.id.totalprice);
    }

    public void getFilteredData() {
        FilterDataList = new ArrayList<>();
  FilterDataList.clear();
        alertDialog.show();
        FilterData = FirebaseDatabase.getInstance().getReference();
        FilterData.child("Products").child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                        ItemData cart = new ItemData();
                        cart = cartSnapshot.getValue(ItemData.class);
                        if (iterator(cart))
                        {
                            check=1;
                            FilterDataList.add(cart);
                    }
                    }
                    if(check==0) {
                        View contextView = findViewById(R.id.frameLayout);
                        Snackbar snackbar = Snackbar
                                .make(contextView, "No Results found !", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Log.i("chexl=",check+"");
                        alertDialog.dismiss();
                  Log.i("chexl=",check+"");
//                        Toast.makeText(MyChoiceActivity.this, "No Results Found", Toast.LENGTH_LONG);
                      }
                    else
                        {
                        check=0;
                        onFirebaseLoadSuccess2(Rview, FilterDataList);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        }, 5000);
                    }

                }
                           }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
                Toast.makeText(MyChoiceActivity.this, "No Results Found", Toast.LENGTH_SHORT);
               }
        });
        setfiltertext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Rview.invalidate();
    //    getFilteredData();
    }
public void setfiltertext() {
    for (int i = 0; i < SelectedOptionslist.size(); i++) {
        //Log.i("iterator", SelectedOptionslist.get(i).selectedOption + "->" + SelectedOptionslist.get(i).value);
        filtertec =  filtertec+ "  " + SelectedOptionslist.get(i).selectedOption + ":" + SelectedOptionslist.get(i).getValue();
        Log.i("iterator", SelectedOptionslist.size()+"="+SelectedOptionslist.get(i).selectedOption + "->" + SelectedOptionslist.get(i).value);
    }
    Log.i("iterator****", filtertec + "->"+"");
    fitertext.setText(filtertec);
    filtertec = "";
}
    public boolean iterator(ItemData data) {
        boolean checker = true;
        for (int i = 0; i < SelectedOptionslist.size(); i++) {
           //Log.i("iterator", SelectedOptionslist.get(i).selectedOption + "->" + SelectedOptionslist.get(i).value);
            if (!data.chkFilterData(SelectedOptionslist.get(i).selectedOption, SelectedOptionslist.get(i).value)) {
                checker = false;
                //          filterchecker=0;
                //        Toast.makeText(getApplicationContext(),"Item not available",Toast.LENGTH_SHORT).show();
                break;
            }
          }
        return checker;
    }

    @Override
    public void onFirebaseLoadSuccess2(RecyclerView rv,List<ItemData> itemGroupList) {
        MyItemAdapter myAdapter = new MyItemAdapter(getApplicationContext(), itemGroupList);
        Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        Rview.setAdapter(myAdapter);
     }
    @Override
    public void onFirebaseLoadSuccess3(List<cartData> itemGroupList) {
        //    MyCartAdapter myAdapter = new MyCartAdapter(getApplicationContext(), itemGroupList);
        //Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        FirebaseRecyclerOptions<cartData> options =
                new FirebaseRecyclerOptions.Builder<cartData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post"), cartData.class)
                        .build();
        MyCartAdapter            myAdapter = new MyCartAdapter(options,this);

        Rview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        Rview.setHasFixedSize(true);
        Rview.setAdapter(myAdapter);

    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }

}
