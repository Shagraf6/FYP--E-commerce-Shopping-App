package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.Product;
import com.example.fyp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemAdapter3 extends RecyclerView.Adapter<MyItemAdapter3.MyViewHolder> {
    private Context context;
    private List<Product> itemDataList;

    public MyItemAdapter3() {
    }

    public MyItemAdapter3(Context context, List<Product> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.mlayout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txt_item_title.setText(itemDataList.get(position).getName());
     //   Picasso.get().load(itemDataList.get(position).getThumbnail()).into(holder.img_book_thumbnail);

        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.img_item);
        holder.setiItemclickListener(new IItemclickListener() {
            @Override
            public void OnItemClickListener(View view, int Position) {
                Intent i=new Intent(context,ItemDetailActivity.class);
i.putExtra("ProductDetail",itemDataList.get(position));
//                context.startActivity(i);

                Toast.makeText(context, itemDataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder, View.OnClickListener {
        TextView txt_item_title;
        ImageView img_item;
        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {
            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_item_title = itemView.findViewById(R.id.tvTitle);
            img_item = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
