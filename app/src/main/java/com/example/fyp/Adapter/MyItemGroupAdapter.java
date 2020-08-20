package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.ImageColor;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.ItemGroup;
import com.example.fyp.MultiRecyclerViewActivity;
import com.example.fyp.R;
import com.example.fyp.SingleRecylerViewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemGroupAdapter extends RecyclerView.Adapter<MyItemGroupAdapter.MyViewHolder> {
private Context context;
private List<ItemGroup> dataList;
    public MyItemGroupAdapter(Context context, List<ItemGroup> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(context).inflate(R.layout.mlayout_group,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.item_Title.setText(dataList.get(position).getHeadertitle());
        List<ItemData> itemData=dataList.get(position).getListitems();
        MyItemAdapter m=new MyItemAdapter(context,itemData);
        holder.recyclerView_ITEM_list.setHasFixedSize(true);
        holder.recyclerView_ITEM_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView_ITEM_list.setAdapter(m);
        holder.recyclerView_ITEM_list.setNestedScrollingEnabled(false);//Important

        List<ImageColor> coverimagelist=new ArrayList<>();
coverimagelist=                dataList.get(position).getCoverimagelist();

//        Log.i("coveriagelist","count=> "+coverimagelist.size());
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SingleRecylerViewActivity.class);
                i.putExtra("ProductDetail",dataList.get(position));
                i.putExtra("Category", dataList.get(position).getListitems().get(0).getCategory());
                    i.putExtra("Type",dataList.get(position).getListitems().get(0).getHeadertitle());
                    i.putExtra("Name",position+"");
               // i.putExtra("coverlist", (Serializable) dataList.get(position).getCoverimagelist());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
               // Toast.makeText(context,"Button More:: "+holder.item_Title.getText(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        System.out.println((dataList!=null? dataList.size():0)+"=  getitemcount****");
        return (dataList!=null? dataList.size():0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
public TextView item_Title;
public RecyclerView recyclerView_ITEM_list;
public TextView btn_more;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        item_Title=(TextView)itemView.findViewById(R.id.itemTitle);
        btn_more=itemView.findViewById(R.id.Mybuttonmore);
        recyclerView_ITEM_list=(RecyclerView)itemView.findViewById(R.id.recyclerview_vieww_list);
        }
    }
}
