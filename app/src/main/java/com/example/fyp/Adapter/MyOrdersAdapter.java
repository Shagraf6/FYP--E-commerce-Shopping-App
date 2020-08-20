package com.example.fyp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.Model.Model;
import com.example.fyp.Model.Order;
import com.example.fyp.Model.cartData;
import com.example.fyp.MyOrdersActivity;
import com.example.fyp.OrderDetailActivity;
import com.example.fyp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersAdapter extends FirebaseRecyclerAdapter<Order,MyOrdersAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Context context;
    public MyOrdersAdapter(@NonNull FirebaseRecyclerOptions<Order> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final Order model) {
  //      Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis(Long.parseLong(model.getTimeStamp()));
    //    String dateTime = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", calendar));
//        holder.TimeStamp.setText(dateTime);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(model.getTimeStamp()));
        String dateTime = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", calendar));
        holder.TimeStamp.setText(dateTime);
        holder.Phoneno.setText(model.getPhoneNo());
        holder.price.setText("Rs. " + model.getTotalAmount() + " /-");
        holder.address.setText(model.getAddres());
        holder.orderStatus.setText(setStatus(model.getStatus()));
        if (model.getStatus().equals("0")) {
            holder.cancel_button.setVisibility(View.VISIBLE);
            holder.reorer.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.cancel_button.setVisibility(View.INVISIBLE);
            holder.reorer.setVisibility(View.VISIBLE);
        }
        holder.reorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow(model);
            }
        });
        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyServerOrder");
                firebaseDatabase.child(model.getOrderid()).removeValue();
                holder.orderStatus.setText("Canceled");
                model.setStatus("-1");
                firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyOrder");
                firebaseDatabase.child(model.getUserid()).child(model.getOrderid()).setValue(model);
            }
        });
        holder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("OrderDetail", model);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }
public String setStatus(String status)
{
    int c=Integer.parseInt(status);
    switch (c)
    {
        case 0:
            return "Placed";
        case 1:
            return "Delivered";
        case -1:
            return "Canceled";
        default:
            return "Placed";
    }
}
    public void addToMyORders(String id,Order model,String address, String name, String Number, String timeStamp) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyOrder");
       // List<cartData> orderLists = new ArrayList<>();
        //orderLists.add(CartD);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number,  model.getTotalAmount(), 0+"", timeStamp, model.getProductlist());
        firebaseDatabase.child(firebaseAuth.getUid()).child(id).setValue(i);
        View contextView = MyOrdersActivity.frameLayout;
        Snackbar snackbar = Snackbar
                .make(contextView, "Your order is placed", Snackbar.LENGTH_LONG);
        snackbar.show();//Toast.makeText(this, "Your order is placed", Toast.LENGTH_SHORT).show();
    }

    public void addToserver(Order Model,String address, String name, String Number) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String timeStamp = String.valueOf(System.currentTimeMillis());
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("MyServerOrder");
        String id = firebaseDatabase.push().getKey();
       // List<cartData> orderLists = new ArrayList<>();
        //orderLists.add(CartD);
        Order i = new Order(firebaseAuth.getUid(), id, address, name, Number, Model.getTotalAmount()+ "", 0+"", timeStamp, Model.getProductlist());
        firebaseDatabase.child(id).setValue(i);
        addToMyORders(id,Model,address, name, Number, timeStamp);
    }

    public void selectPaymntMehtod(final Order Model,final String address, final String phoneNo, final String Name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");

        View Submit_ordr_Layout = LayoutInflater.from(context).inflate(R.layout.paymentmethod, null);
        //views to set in dailog
        final TextView UserInfo = Submit_ordr_Layout.findViewById(R.id.userinfo);
        final RadioButton R1 = Submit_ordr_Layout.findViewById(R.id.radio1);
        final RadioButton R2 = Submit_ordr_Layout.findViewById(R.id.radio2);

        UserInfo.setText("Address : " + address + "\nName : " + Name + "\nPhone No : " + phoneNo);
        builder.setView(Submit_ordr_Layout);

        //button recover
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String Address = address;
                addToserver(Model,address, phoneNo, Name);


            }
        });
        //button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();

            }
        });
        builder.create().show();
    }


    public void buyNow(final Order MODEL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Place Your Order");
        View Submit_ordr_Layout = LayoutInflater.from(context).inflate(R.layout.activity_buy_now, null);
        //views to set in dailog
        final EditText address = Submit_ordr_Layout.findViewById(R.id.address);
        final EditText phoneNo = Submit_ordr_Layout.findViewById(R.id.PhoneNo);
        final EditText Name = Submit_ordr_Layout.findViewById(R.id.Name);
        builder.setView(Submit_ordr_Layout);

        //button recover
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String Address = address.getText().toString().trim();
                if(TextUtils.isEmpty( Name.getText().toString()))
                {
                    Name.setError("Name Required");
                    Name.setFocusable(true);
                }
                else
                if(TextUtils.isEmpty( phoneNo.getText().toString())) {
                    phoneNo.setError("PhoneNO Required");
                    phoneNo.setFocusable(true);
                }
                else
                if(TextUtils.isEmpty( address.getText().toString()))
                {
                    address.setError("Address Required");
                    address.setFocusable(true);
                }
                else {
                    selectPaymntMehtod(MODEL,address.getText().toString().trim(), Name.getText().toString().trim(), phoneNo.getText().toString().trim());
                }

            }
        });
        //button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();

            }
        });
        builder.create().show();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_layout, parent, false);
        return new MyOrdersAdapter.MyViewHolder(itemView);
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
Button reorer;
        IItemclickListener iItemclickListener;

        public void setiItemclickListener(IItemclickListener i) {
            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reorer=itemView.findViewById(R.id.reorder);
            orderStatus=itemView.findViewById(R.id.orderstatus);
            TimeStamp = itemView.findViewById(R.id.orderTime);
            price = itemView.findViewById(R.id.orderTotalAmount);
            address = itemView.findViewById(R.id.orderAddress);
            Phoneno = itemView.findViewById(R.id.orderPhoneNo);
            cancel_button = itemView.findViewById(R.id.cancelorder);
            order_detail = itemView.findViewById(R.id.orderDetails);
         //   counter = (ElegantNumberButton) itemView.findViewById(R.id.number_counterr);
           // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
