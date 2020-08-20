package com.example.fyp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.Model.ItemData;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.cartData;
import com.example.fyp.MyCart;
import com.example.fyp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyORderdtaillAdapter extends RecyclerView.Adapter<MyORderdtaillAdapter.MyViewHolder>
{
    private Context context;
    private List<cartData> itemDataList;
       private MyORderdtaillAdapter() {

      }

      public MyORderdtaillAdapter(Context context, List<cartData> itemDataList) {
        //  super();
          this.context = context;
          this.itemDataList = itemDataList;
      }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetailactivity, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.header.setText(itemDataList.get(position).getName());
        holder.size.setText("Color: "+itemDataList.get(position).getSize());
        holder.color.setText("Size"+itemDataList.get(position).getColor());
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.product_img);
        holder.price.setText("Rs."+itemDataList.get(position).getPrice()+"/-");
        //totalPRice= totalPRice+model.getPrice();
        //MyCart.price.setText("Rs. "+totalPRice+"/- ");
        holder.Qty.setText("Qty: "+itemDataList.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder, View.OnClickListener {
        TextView header;
        TextView price;
        TextView color;
        TextView size;
        ImageView product_img;
TextView Qty;
        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {
            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            price = itemView.findViewById(R.id.price);
            color = itemView.findViewById(R.id.color);
            size = itemView.findViewById(R.id.Size);
            product_img = itemView.findViewById(R.id.productimg);
         Qty=itemView.findViewById(R.id.qty);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
