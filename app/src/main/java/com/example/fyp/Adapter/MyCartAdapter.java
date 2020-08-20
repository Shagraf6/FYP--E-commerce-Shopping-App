package com.example.fyp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fyp.Interface.IItemclickListener;
import com.example.fyp.ItemDetailActivity;
import com.example.fyp.Model.DatabaseHelperclass;
import com.example.fyp.MyCart;
import com.example.fyp.R;
import com.example.fyp.Model.cartData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyCartAdapter extends FirebaseRecyclerAdapter<cartData,MyCartAdapter.MyViewHolder> {
    private Context context;
    private List<cartData> itemDataList;
    int totalPRice;

public static DatabaseHelperclass db;

FirebaseAuth firebaseAuth;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
public MyCartAdapter(@NonNull FirebaseRecyclerOptions<cartData> options,Context context)
{
    super(options);
    this.context=context;
    firebaseAuth=FirebaseAuth.getInstance();
    db=new DatabaseHelperclass(context,firebaseAuth.getUid());
}

/*    private MyCartAdapter() {

    }

    public MyCartAdapter(Context context, List<cartData> itemDataList) {
      //  super();
        this.context = context;
        this.itemDataList = itemDataList;
    }
*/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycartview, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final cartData model) {
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getRootView().getContext());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Are You Sure?");
                builder.setMessage("Are you Sure to Remove this Product");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(model.getId(),position,model,v);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();


                //        notifyDataSetChanged();
                //        notifyAll();
            }
        });
        holder.setiItemclickListener(new IItemclickListener() {
            @Override
            public void OnItemClickListener(View view, int Position) {
            }
        });
        holder.counter.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.setCarttotalAmount(-(model.getPrice()*(Integer.parseInt(model.getQty()))));
               // Log.i("dbsozr1=",db.getcartData("1")+"-"+model.getQty());
                //  MyCart.price.setText("Rs."+(MyCart.TotalPRize)+"/-");
                MyCart.TotalPRize=(Integer.parseInt(MyCart.TotalPRize)-(model.getPrice()*(Integer.parseInt(holder.counter.getNumber())-1)))+"";
                db.setCarttotalAmount(-(model.getPrice()*(Integer.parseInt(model.getQty()))));
                model.setQty(holder.counter.getNumber());
 chnge_qty(model);
                db.setCarttotalAmount((model.getPrice()*(Integer.parseInt(model.getQty()))));
                Log.i("dbsozr2=",db.getcartData("1")+"-"+model.getQty());
           //         MyCart.TotalPRize=(Integer.parseInt(MyCart.TotalPRize)+(model.getPrice()*(Integer.parseInt(holder.counter.getNumber())-1)))+"";
                 MyCart.price.setText("Rs."+(db.getcartData("1"))+"/-");
            }
        });

        holder.counter.setNumber(model.getQty());
        if(Integer.parseInt(model.getQty())>1)
            holder.del.setVisibility(View.INVISIBLE);
        holder.header.setText(model.getName());
        holder.size.setText(model.getSize());
        holder.color.setText(model.getColor());
//        MyCart.counter=MyCart.counter+1;
        Picasso.get().load(model.getImage()).into(holder.product_img);
        holder.price.setText("Rs."+model.getPrice()+"");
  //      totalPRice= totalPRice+model.getPrice();
    //    MyCart.price.setText("Rs. "+totalPRice+"/- ");
    }

    public void removeItem(final String id, final int position,final cartData Model,View v) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance()
                .getReference("MyCart")
                .child(firebaseAuth.getUid());
        firebaseDatabase.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//                totalPRice=totalPRice-PRice;
  //              MyCart.counter=MyCart.counter-1;
                db.setCartSize(-(1));
         db.setCarttotalAmount(-(Model.getPrice()*Integer.parseInt(Model.getQty())));
         //       db.setCarttotalAmount(-(PRice));

                MyCart.price.setText("Rs."+db.getcartData("1")+"/-");
    MyCart.toolbar.setTitle("MyCart ("+db.getcartData("0")+")");

                Snackbar snackbar = Snackbar
                        .make(MyCart.framelayout, "Item Removed !", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar
                        .make(MyCart.framelayout, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });
    }
    public void chnge_qty(final cartData Model) {
 //       Log.i("modelty=",(Model.getPrice()*(Integer.parseInt(Model.getQty())))+db.getcartData("1")+"");
   //     db.setCarttotalAmount((Model.getPrice()*(Integer.parseInt(Model.getQty())))+db.getcartData("1"));
     //   MyCart.price.setText("Rs."+db.getcartData("1")+"/-");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance()
                .getReference("MyCart")
                .child(firebaseAuth.getUid());
        firebaseDatabase.child(Model.getId()).setValue(Model);
    }

   /* @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size() : 0);
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.fyp.Adapter.MyViewHolder, View.OnClickListener {
        TextView header;
        TextView price;
        TextView color;
        TextView size;
        RoundedImageView product_img;
        ImageView del;
        ImageView fav;
        IItemclickListener iItemclickListener;
        ElegantNumberButton counter ;

        public void setiItemclickListener(IItemclickListener i) {
            this.iItemclickListener = i;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            price = itemView.findViewById(R.id.price);
            color = itemView.findViewById(R.id.color);
            size = itemView.findViewById(R.id.Size);
            product_img = itemView.findViewById(R.id.productimg);
            del = itemView.findViewById(R.id.del);
            fav = itemView.findViewById(R.id.fav);
            counter = (ElegantNumberButton) itemView.findViewById(R.id.number_counterr);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemclickListener.OnItemClickListener(v, getAdapterPosition());

        }
    }
}
