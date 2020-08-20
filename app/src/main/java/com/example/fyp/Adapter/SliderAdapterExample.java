package com.example.fyp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fyp.Model.SliderItem;
import com.example.fyp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        //  SliderItem sliderItem = mSliderItems.get(position);
       //   viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(mSliderItems.get(position).getImageUrl())
                .placeholder(R.drawable.logo)
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        viewHolder.textViewDescription.setText(mSliderItems.get(position).getDescription());
        /*for(int i=0;i<mSliderItems.size();i++) {
            Glide.with(viewHolder.itemView)
                    .load(mSliderItems.get(i).getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
            viewHolder.textViewDescription.setText(mSliderItems.get(i).getDescription());
        }*
        ///
          /*  switch (position) {
                case 0:
                    Glide.with(viewHolder.itemView)
                            .load("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2Ffashion%20heels.jpg?alt=media&token=880efd83-1db8-4cee-882b-9c918b709235")
                            .into(viewHolder.imageViewBackground);
                    break;
                case 1:
                    Glide.with(viewHolder.itemView)
                           .load("https://firebasestorage.googleapis.com/v0/b/s2zfyp.appspot.com/o/Sidra%20posters%2FAdvertisments%2FCopy%20of%20Spring%20sale%20flyer%20-%20Made%20with%20PosterMyWall.jpg?alt=media&token=1ddc6a3b-dab2-4990-a6d5-666080a74089")
                            .into(viewHolder.imageViewBackground);
                    break;
                case 2:
                    Glide.with(viewHolder.itemView)
                            .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                            .into(viewHolder.imageViewBackground);
                    break;
                    case 3:
                    Glide.with(viewHolder.itemView)
                            .load("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                            .into(viewHolder.imageViewBackground);
                    break;

                case 4:
                    Glide.with(viewHolder.itemView)

                           .load("https://giphy.com/gifs/love-hearts-shurly-3o7TKoWXm3okO1kgHC")
                            .into(viewHolder.imageViewBackground);
                    break;

            }*/

//                Glide.with(viewHolder.itemView)
  //              .load(sliderItem.getImageUrl())
    //            .fitCenter()
      //          .into(viewHolder.imageViewBackground);
//viewHolder.imageViewBackground.setImageResource(sliderItem.getImage());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getCount() {
        //slider view count could be dynamic size
           return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
