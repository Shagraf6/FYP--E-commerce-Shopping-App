package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Model.Dashboard_item;

import com.example.fyp.MultiRecyclerViewActivity;
import com.example.fyp.R;
import com.example.fyp.SingleRecylerViewActivity;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

        private Context mContext ;
        private List<Dashboard_item> mData ;
//private List<ItemData> mData;



    public DashboardAdapter(Context mContext, List<Dashboard_item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
  /* public DashboardAdapter(Context mContext, List<ItemData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }*/

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.categorylayout,parent,false);
           return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

           holder.tv_title.setText(mData.get(position).getName());
//            Picasso.get().load(mData.get(position).getImage()).into(holder.img_book_thumbnail);
            holder.img_thumbnail.setImageResource(mData.get(position).getImage());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, MultiRecyclerViewActivity.class);

                    if(mData.get(position).getCheck()==2) {
                        intent.putExtra("Category", mData.get(position).getCategory());
                        intent.putExtra("Type","W2");
                        intent.putExtra("Name",mData.get(position).getName());
                      //  Toast.makeText(mContext,"2",Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);
                    }
                    if (mData.get(position).getCheck()==1) {
                     //   Intent intent = new Intent(mContext, SingleRecylerViewActivity.class);
                        intent.putExtra("Category", mData.get(position).getCategory());
                        intent.putExtra("Type","W2");
                        intent.putExtra("Name",mData.get(position).getName());
                       // Toast.makeText(mContext,"1",Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);
                    }

                }
            });



        }


    @Override
        public int getItemCount() {
            //System.out.println((mData!=null? mData.size():0)+"=  getitemcount****");
            //return (mData!=null? mData.size():0);

               return mData.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_title;
            ImageView img_thumbnail;
            CardView cardView ;

            public MyViewHolder(View itemView) {
                super(itemView);

                tv_title = (TextView) itemView.findViewById(R.id.CategoryTitle) ;
                img_thumbnail = (ImageView) itemView.findViewById(R.id.CategoryImg);
                cardView = (CardView) itemView.findViewById(R.id.categorylayout);


            }
        }


    }

