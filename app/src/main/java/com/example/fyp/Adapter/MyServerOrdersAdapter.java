package com.example.fyp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.Model.Model;
import com.example.fyp.Model.Order;
import com.example.fyp.OrderDetailActivity;
import com.example.fyp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyServerOrdersAdapter extends FirebaseRecyclerAdapter<Order, MyServerOrdersAdapter.MyViewHolder> {
public  boolean mprocessProduct;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
public static List<String> PhoneNo;
 public static    String message="Oder confirmation sms";
public static    int check=0;
    public Context context;
    public MyServerOrdersAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context) {
        super(options);
        this.context=context;
        PhoneNo=new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final Order model) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(model.getTimeStamp()));
        String dateTime = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", calendar));
        holder.TimeStamp.setText(dateTime);
        holder.Phoneno.setText(model.getPhoneNo());
        holder.price.setText(model.getTotalAmount());
        holder.address.setText(model.getAddres());
     if(model.getStatus().equals("-1")) {
         holder.orderStatus.setText("Canceled");
         holder.confrimorder.setVisibility(View.INVISIBLE);
         holder.cancel_button.setVisibility(View.INVISIBLE);
     }
     else if(model.getStatus().equals("1"))
     {
         holder.orderStatus.setText("Delivered");
         holder.confrimorder.setVisibility(View.INVISIBLE);
         holder.cancel_button.setVisibility(View.INVISIBLE);
     }
     holder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("OrderDetail", model);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateORderstatus(model.getUserid(), model.getOrderid(), 0, model);
                    holder.confrimorder.setVisibility(View.INVISIBLE);
                    holder.cancel_button.setVisibility(View.INVISIBLE);
                    holder.orderStatus.setText("Canceled");
           //    check=0;
                      } catch (ClassCastException cce2) {
                    Toast.makeText(context, cce2.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.confrimorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateORderstatus(model.getUserid(), model.getOrderid(), 1, model);
                    PhoneNo.add(model.getPhoneNo());
                    //sendSMSMessage(model.getPhoneNo());
                    Log.i("phoneno", model.getPhoneNo());
                    holder.confrimorder.setVisibility(View.INVISIBLE);
                    holder.cancel_button.setVisibility(View.INVISIBLE);
                    holder.orderStatus.setText("Delivered");
                    check=1;
                } catch (ClassCastException cce2) {
                    //check=0;
                    Toast.makeText(context, cce2.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        public void updateORderstatus(final String userid,final String orderid, final int Check,Order Model) {
        mprocessProduct = true;
        if (Check == 1) {
            Model.setStatus("1");
        } else if (Check == 0) {
            Model.setStatus("-1");
        }
        Log.i("orerid",orderid);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
            ref.child("MyServerOrder")
                    .child(orderid).setValue(Model);
ref=FirebaseDatabase.getInstance().getReference("MyOrder");
        ref.child(userid).child(orderid).setValue(Model);
            mprocessProduct = false;
 }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.serverlayout, parent, false);
        return new MyServerOrdersAdapter.MyViewHolder(itemView);
        }

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder, View.OnClickListener {
        TextView TimeStamp;
        TextView price;
        TextView address;
        TextView Phoneno;
        TextView orderStatus;
        TextView order_detail;
TextView imageDetail;
Button cancel_button;
Button confrimorder;
        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {
            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderStatus=itemView.findViewById(R.id.orderstatus);
            TimeStamp = itemView.findViewById(R.id.orderTime);
            price = itemView.findViewById(R.id.orderTotalAmount);
            address = itemView.findViewById(R.id.orderAddress);
            Phoneno = itemView.findViewById(R.id.orderPhoneNo);
            cancel_button = itemView.findViewById(R.id.cancelorder);
            order_detail= itemView.findViewById(R.id.orderDetails);
            confrimorder = itemView.findViewById(R.id.reorder);
         //   counter = (ElegantNumberButton) itemView.findViewById(R.id.number_counterr);
           // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
