package com.example.fyp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.Model.FilterObject;
import com.example.fyp.Model.FilterRvdata;
import com.example.fyp.Model.ImageColor;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.Model.catList;
import com.example.fyp.MultiRecyclerViewActivity;
import com.example.fyp.R;
import com.example.fyp.Tab1Fragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import static com.example.fyp.MyChoiceActivity.SelectedOptionslist;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context mContext;
    private List<catList> mData;
    DatabaseReference myData;
    static public String prePos="View All";
AlertDialog alertDialog;

public CategoriesAdapter(Context mContext, List<catList> mData) {
        this.mContext = mContext;
        this.mData = mData;
        myData=FirebaseDatabase.getInstance().getReference();
        alertDialog = new SpotsDialog.Builder().setContext(this.mContext).build();

    }
  /* public DashboardAdapter(Context mContext, List<ItemData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.catlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
holder.tv_title.setText(mData.get(position).getText());
//mData.set(position,new catList(holder.view,mData.get(position).getText()));
holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Log.i("ba",prePos);
       prePos=mData.get(position).getText();
       notifyDataSetChanged();
   getFirebaseData2(prePos,mData.get(position).getCategory());
   MultiRecyclerViewActivity.chosentext.setText(prePos);
    }
});

        if (mData.get(position).getText().equals(prePos))
            holder.view.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorAccent));
else
            holder.view.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));

    }

    @Override
    public int getItemCount() {
        //System.out.println((mData!=null? mData.size():0)+"=  getitemcount****");
        //return (mData!=null? mData.size():0);
        return mData.size();
    }
    private void getFirebaseData2(String name, final String cat) {
//        dialog.show();
        Log.i("getoreas", "*******^^^^^^^^(((******");
        //alertDialog = new SpotsDialog.Builder().setContext(this.mContext).build();
        //alertDialog.show();
        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("MyCat").child("category").child(cat.trim()).child(name.trim()).addValueEventListener(new ValueEventListener() {
            //    myData.child("My").child("Category").child("Women").child("W1").child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                   //Toast.makeText(mContext, "getfirebase2$$$$$      "+cat, Toast.LENGTH_SHORT).show();
                    itemGroup.setHeadertitle(groupSnapshot.child("headertitle").getValue(true).toString());
                    //GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator=new GenericTypeIndicator<>();
                    //Log.i("getoreas", "*************" + groupSnapshot.child("headertitle").getValue(true).toString());
                    GenericTypeIndicator<List<ItemData>> t = new GenericTypeIndicator<List<ItemData>>() {
                    };
                    //List<Object> routes = dataSnapshot.getValue(t);
                    itemGroup.setListitems(groupSnapshot.child("listitems").getValue(t));
                    GenericTypeIndicator<List<ImageColor>> I = new GenericTypeIndicator<List<ImageColor>>() {
                    };
                    itemGroup.setCoverimagelist(groupSnapshot.child("coverimagelist").getValue(I));    // ItemData2 = groupSnapshot.child("listitems").getValue(t);
                    itemGroups.add(itemGroup);
                }
//                iFirebaseLoadListener.onFirebaseLoadSuccess2(ItemData2);
                MultiRecyclerViewActivity.iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
          // alertDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            //alertDialog.dismiss();
           MultiRecyclerViewActivity.iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        LinearLayout linearLayout;
View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.myCategory);
         view=itemView.findViewById(R.id.myView);
         linearLayout=itemView.findViewById(R.id.categorylistlayout);
        }

    }
}

