package com.example.fyp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.fyp.Model.SliderItem;

public class SliderAdapter2 extends
            SliderViewAdapter<SliderAdapter2.SliderAdapterVH> {

        private Context context;
        private List<SliderItem> mSliderItems = new ArrayList<>();

        public SliderAdapter2(Context context) {
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
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_buy_now, null);
           return new SliderAdapterVH(inflate);
       }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

            SliderItem sliderItem = mSliderItems.get(position);

            viewHolder.textViewDescription.setText("hello worls");
            viewHolder.textViewDescription.setTextSize(16);
            viewHolder.textViewDescription.setTextColor(Color.WHITE);
          /*  Picasso.with(viewHolder.itemView)
                    .load(sliderItem.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
*/
         // viewHolder.itemView.setIm
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
             //   imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
               // imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
                //textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
                this.itemView = itemView;
            }
        }

    }

