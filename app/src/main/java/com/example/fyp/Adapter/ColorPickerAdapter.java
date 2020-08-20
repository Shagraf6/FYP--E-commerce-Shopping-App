package com.example.fyp.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.ImageColor;
import com.example.fyp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fyp.ItemDetailActivity.CartD;
import static com.example.fyp.ItemDetailActivity.FL;
import static com.example.fyp.ItemDetailActivity.imageColor;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.MyViewHolder>  {
    Context mContext;
    List<ImageColor> mData;
    static public String prePos="-";


    public ColorPickerAdapter(Context context, java.util.List<ImageColor> list) {
        this.mContext = context;
        mData = list;
prePos="-1";
    }

    @NonNull
    @Override
    public ColorPickerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.mycolorpicker,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_title.setText(mData.get(position).getColorname());
        Picasso.get().load(mData.get(position).getImageurl()).into(holder.img_thumbnail);
        holder.img_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetailActivity.color = mData.get(position).getColorname();
                //imageColor.setText("Product Color : " + color);
                //CartD.setColor(color);
                //setImagebackground(R.id.Fcolor3);
                prePos = mData.get(position).getColorname();
                notifyDataSetChanged();
            }
        });
        if (mData.get(position).getColorname().equals(prePos)) {
            imageColor.setText("Product Color : " + mData.get(position).getColorname());
            CartD.setColor(mData.get(position).getColorname());
            Picasso.get().load(mData.get(position).getImageurl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    FL.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            holder.frameLayout.setBackgroundResource(R.drawable.border2);
        } else {
            holder.frameLayout.setBackgroundResource(R.color.white);
        }
        if(prePos.equals("-1"))
        {
            holder.frameLayout.setBackgroundResource(R.drawable.border2);
            prePos="-2";
        }
    }
    public void setImagebackground(int id,FrameLayout frameLayout) {
        for (int i=0;i<mData.size();i++) {
            if (i == id)
                frameLayout.setBackgroundResource(R.drawable.border2);
        }
    }
    @Override
    public int getItemCount() {
        System.out.println((mData!=null? mData.size():0)+"=  getitemcount****");
        return (mData!=null? mData.size():0);
     }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        CircleImageView img_thumbnail;
        FrameLayout frameLayout;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.text1) ;
            img_thumbnail = itemView.findViewById(R.id.Color1);
            frameLayout =  itemView.findViewById(R.id.Fcolor1);
cardView=itemView.findViewById(R.id.colorcard);

        }
    }
}