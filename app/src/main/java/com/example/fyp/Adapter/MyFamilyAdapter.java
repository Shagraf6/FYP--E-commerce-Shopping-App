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
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.ItemData;
import com.example.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyFamilyAdapter extends RecyclerView.Adapter<MyFamilyAdapter.MyViewHolder> {
    private Context context;
    private List<ItemData> itemDataList;
    public FirebaseAuth firebaseAuth;
    DatabaseHelperclass db;

    public MyFamilyAdapter() {
    }

    public MyFamilyAdapter(Context context, List<ItemData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
        firebaseAuth = FirebaseAuth.getInstance();
        db=new DatabaseHelperclass(context,firebaseAuth.getUid());
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.mfamilylayout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.txt_item_title.setText(itemDataList.get(position).getName());
        holder.price.setText("Rs."+itemDataList.get(position).getPrice()+"/-");
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.img_item);

        holder.Fav_img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!db.isFav(itemDataList.get(position).getIdC() + itemDataList.get(position).getIdP())) {
                    db.addToFavorite(itemDataList.get(position).getIdC() + itemDataList.get(position).getIdP());
                    addtofav(holder.Fav_img_item,itemDataList.get(position));
                }
                else {
                    db.removeFromFavroties(itemDataList.get(position).getIdC() + itemDataList.get(position).getIdP());
                    removeFav(holder.Fav_img_item,itemDataList.get(position));
//                    holder.Fav_img_item.setImageResource(R.drawable.nfav);
                }
                //Toast.makeText(context, itemDataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        holder.setiItemclickListener(new IItemclickListener() {
            @Override
            public void OnItemClickListener(View view, int Position) {

                Intent i=new Intent(context,ItemDetailActivity.class);

              i.putExtra("ProductDetail",itemDataList.get(position));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
              //  Toast.makeText(context, itemDataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        SetFav(itemDataList.get(position).getIdC()+itemDataList.get(position).getIdP(),holder.Fav_img_item);
  //      setFav(itemDataList.get(position).getLike(),holder.Fav_img_item);
    }

    public void SetFav(String id,ImageView v)
    {
        if(db.isFav(id))
            v.setImageResource(R.drawable.fav);
        else
            v.setImageResource(R.drawable.nfav);
    }

    public void removeFav(ImageView v, ItemData ProductData) {
        v.setImageResource(R.drawable.nfav);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyFav");
        String id = ProductData.getIdC() + ProductData.getIdP();
       // ProductData.setLike(ProductData.getLike()-1);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).removeValue();
        //chnges in the json
        updateJsonCategory(ProductData);
        updateJsonProduct(ProductData);
        Toast.makeText(context, " Removed from wishlist", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public void addtofav(ImageView v, ItemData ProductData) {

        v.setImageResource(R.drawable.fav);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyFav");
        String id = ProductData.getIdC() + ProductData.getIdP();
        ProductData.setLike(ProductData.getLike()+1);
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(ProductData);
        //chnges in the json
        updateJsonCategory(ProductData);
        updateJsonProduct(ProductData);
        Toast.makeText(context, "added to wishlist", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
    public void setFav(int like,ImageView v)
    {
        if(like==1)
            v.setImageResource(R.drawable.fav);
        else if(like==0)
            v.setImageResource(R.drawable.nfav);

    }
    public void updateJsonProduct(ItemData ProductData)
    {
DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference("MyProducts");
        firebaseDatabase.child("Product")
                .child(ProductData.getIdP()).setValue(ProductData);
    }
    public void updateJsonCategory(ItemData ProductData)
    {
DatabaseReference        firebaseDatabase=FirebaseDatabase.getInstance().getReference("MyCat");
        firebaseDatabase.child("category")
                .child(ProductData.getCategory().toLowerCase())
                //           .child(ProductData.getType())
                .child(ProductData.getHeadertitle())
                .child(ProductData.getIdC())
                .child("listitems")
                .child(ProductData.getIdS()).setValue(ProductData);
    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder, View.OnClickListener {
        TextView txt_item_title;
        TextView price;
        ImageView img_item;
        ImageView Fav_img_item;
        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {

            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_item_title = itemView.findViewById(R.id.tvTitle);
            img_item = itemView.findViewById(R.id.item_image);
            price = itemView.findViewById(R.id.price);
            Fav_img_item = itemView.findViewById(R.id.fav);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
