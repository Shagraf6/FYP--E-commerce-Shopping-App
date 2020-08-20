package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.Interface.OnitemSelectedListener;
import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.Dashboard_item;
import com.example.fyp.Model.FilterObject;
import com.example.fyp.Model.FilterRvdata;
import com.example.fyp.MultiRecyclerViewActivity;
import com.example.fyp.MyChoiceActivity;
import com.example.fyp.R;
import com.example.fyp.SingleRecylerViewActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.fyp.MyChoiceActivity.SelectedOptionslist;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private Context mContext;
    private List<FilterRvdata> mData;
     int count;
     boolean chk=false;
//private List<ItemData> mData;


    public FilterAdapter(Context mContext, List<FilterRvdata> mData) {
        this.mContext = mContext;
        this.mData = mData;
    this.count=-1;
    }
  /* public DashboardAdapter(Context mContext, List<ItemData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.filtercard_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.tv_title.setText(mData.get(position).getOption());

        holder.textInputLayout.setHint(mData.get(position).getOption());

                   ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, mData.get(position).getOptionLits());

          myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(myAdapter);
        holder.spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //this is the way to find selected object/item
//            SelectedOptionslist.
             if (SelectedOptionslist==null||SelectedOptionslist.size()==0) {
                 Log.i("iterator","nul"+count);
                 count++;
              SelectedOptionslist.add(count, new FilterObject(mData.get(position).getOption(), mData.get(position).getOptionLits().get(pos), count));
             }
             else
             {
                 for (int i=0;i<SelectedOptionslist.size();i++)
                 {
                     if(SelectedOptionslist.get(i).selectedOption.equals(mData.get(position).getOption()))
                     {
                         Log.i("iterator","forif"+SelectedOptionslist.get(i).ID);
                         SelectedOptionslist.set(SelectedOptionslist.get(i).ID,new FilterObject(mData.get(position).getOption(),mData.get(position).getOptionLits().get(pos),SelectedOptionslist.get(i).ID));
                         Log.i("iterator","forif"+SelectedOptionslist.size());
                         chk=true;
                     break;
                     }
                 }
                 Log.i("iterator","ifchk"+chk);

                 if(!chk)
                 {
                     count++;
                     SelectedOptionslist.add(count,new FilterObject(mData.get(position).getOption(),mData.get(position).getOptionLits().get(pos),count));
                     }
                 chk=false;
             }
             //    selectedPerson = (People) adapterView.getItemAtPosition(pos);
            }
        });
        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        Toast.makeText(mContext,"Button More:: "+holder.spinner.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //System.out.println((mData!=null? mData.size():0)+"=  getitemcount****");
        //return (mData!=null? mData.size():0);

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder,AdapterView.OnItemSelectedListener,View.OnClickListener{

        //TextView tv_title;
        //MaterialSpinner spinner;
        //  CardView cardView ;

        private TextInputLayout textInputLayout;
        private AutoCompleteTextView spinner;
  //      OnitemSelectedListener iItemclickListener2;
//        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {

    //        this.iItemclickListener = i;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            //  tv_title=itemView.findViewById(R.id.filterOPtion);
            textInputLayout = itemView.findViewById(R.id.text_input_layout);
            spinner = itemView.findViewById(R.id.dropdown_text);
            itemView.setOnClickListener(this);
           spinner.setOnClickListener(this);
            //    cardView = (CardView) itemView.findViewById(R.id.categorylayout);


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          //  iItemclickListener.onItemSelected(parent,view,position,id);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public void onClick(View v) {
      //      iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}


