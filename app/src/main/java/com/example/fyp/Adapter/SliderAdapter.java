package com.example.fyp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fyp.Model.SliderItem;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fyp.Model.SliderItem2;
import com.example.fyp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
   private List<SliderItem2> sliderItems;
   private ViewPager2 viewPager2;

 public    SliderAdapter(List<SliderItem2> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(sliderItems.get(position).getImageUrl())
               .placeholder(R.drawable.logo)
                .centerInside()
                .into(holder.imageView);
        /*for(int i=0;i<sliderItems.size();i++) {
         Glide.with(holder.itemView)
                 .load(sliderItems.get(i).getImageUrl())
                 .centerCrop()
                 .into(holder.imageView);
     }*/
     //Picasso.get().load(sliderItems.get(position).getImageUrl()).into(holder.imageView);
//        holder.setImage(sliderItems.get(position));
         if (position==sliderItems.size()-2){
             viewPager2.post(runnable);
         }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;
        View itemView;

         SliderViewHolder(@NonNull View itemView) {
            super(itemView);
         this.itemView=itemView;

            imageView=itemView.findViewById(R.id.imageSlide);
        }
        void setImage(SliderItem sliderItem){
            //if you want to display image from the internet,
            //you can put code here using glide or picasso.
            imageView.setImageResource(sliderItem.getImage());
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

}
