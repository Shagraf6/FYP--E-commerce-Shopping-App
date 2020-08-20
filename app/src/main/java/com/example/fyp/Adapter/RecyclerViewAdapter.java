package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Model.cardCover;
import com.example.fyp.MultiRecyclerViewActivity;
import com.example.fyp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    int i=0;
    private List<cardCover> mData;
//private List<ItemD> mData;

    /*        public RecyclerViewAdapter(Context mContext, List<Book> mData) {
                this.mContext = mContext;
                this.mData = mData;
            }
      */
    public RecyclerViewAdapter(Context mContext, List<cardCover> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.catgrylist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

if(mData.get(position).getCategory().equals("Women")) {
    holder.tv_book_title.setVisibility(View.INVISIBLE);
    holder.tv_book_title2.setVisibility(View.INVISIBLE);
 }
else {
    holder.tv_book_title.setText(mData.get(position).getTitle());
    holder.tv_book_title2.setText(mData.get(position).getTitle2());
}
holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.img_book_thumbnail2.setImageResource(mData.get(position).getThumbnail2());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MultiRecyclerViewActivity.class);

                    intent.putExtra("Category", mData.get(position).getCategory());
                    intent.putExtra("Type","W2");
                    intent.putExtra("Name","Accessories");
                    Toast.makeText(mContext,"2",Toast.LENGTH_SHORT).show();
                    mContext.startActivity(intent);
                Log.i("log", "pos" + position);

            }
        });
        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MultiRecyclerViewActivity.class);

                intent.putExtra("Category", mData.get(position).getCategory());
                intent.putExtra("Type","W2");
                intent.putExtra("Name","Accessories");
                Toast.makeText(mContext,"2",Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println((mData != null ? mData.size() : 0) + "=  getitemcount****");
        return (mData != null ? mData.size() : 0);

        //   return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        RoundedImageView img_book_thumbnail;
        CardView cardView;
        TextView tv_book_title2;
        RoundedImageView img_book_thumbnail2;
        CardView cardView2;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.AssersoriesText);
            img_book_thumbnail = itemView.findViewById(R.id.AssersoriesImg);
            cardView = (CardView) itemView.findViewById(R.id.myAssersoriesCard);

            tv_book_title2 = (TextView) itemView.findViewById(R.id.AssersoriesText2);
            img_book_thumbnail2 = itemView.findViewById(R.id.AssersoriesImg2);
            cardView2 = (CardView) itemView.findViewById(R.id.myAssersoriesCard2);

        }
    }


}

