package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    SharedPreferences sp;
    static int mNotifCount=0;
    SharedPreferences.Editor editor;
    private static final String TOPIC_NOTIFICATION="Notification";
 TextView chagePassword ;
    TextView logout ;
    SwitchCompat notificationSwitch;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    INIT();
        chagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
        setSwitchState(notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sp.edit();
                editor.putBoolean("" + TOPIC_NOTIFICATION, isChecked);
                editor.apply();
                if (isChecked) {
                    subscribe();
                } else {
                    unsubscribeNotification();
                }
            }
        });
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        firebaseAuth.signOut();
       checkUserStatus();
        Toast.makeText(SettingActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();

    }
});
    }
    public void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
       // tv.clearFocus();
        if (user != null) {
     //       tv.setFocusable(true);
       //     tv.setText(user.getEmail());
        }
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
public void INIT()
{dialog = new SpotsDialog.Builder().setContext(this).build();
firebaseAuth=FirebaseAuth.getInstance();
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    FirebaseUser user = firebaseAuth.getCurrentUser();
    toolbar.setTitle(user.getEmail());
   chagePassword = findViewById(R.id.changepass);
    notificationSwitch = findViewById(R.id.notificationSwitch);
logout=findViewById(R.id.LogOut);

}
    public void setSwitchState(SwitchCompat Sc) {
        sp = getSharedPreferences("Notification_sp", MODE_PRIVATE);
        boolean isNotificationEnabled = sp.getBoolean("" + TOPIC_NOTIFICATION, false);
        if (isNotificationEnabled)
            Sc.setChecked(true);
        else
            Sc.setChecked(false);
    }

    private void showsettinglayout() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Setting");

        View Submit_ordr_Layout = LayoutInflater.from(this).inflate(R.layout.settings, null);
        //views to set in dailog
        //button recover
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
            }
        });
        //button cancel
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();

            }
        });
        builder.create().show();
    }

    private void callNoificationChannel() {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(TOPIC_NOTIFICATION, TOPIC_NOTIFICATION, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = null;
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("" + TOPIC_NOTIFICATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "You will recieve app Notifications";
                        if (!task.isSuccessful()) {
                            msg = "Subscription Failed";
                        }
                        Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unsubscribeNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("" + TOPIC_NOTIFICATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "You will not recieve app Notifications";
                        if (!task.isSuccessful()) {
                            msg = "Un Subscription Failed";
                        }
                        Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showRecoverPasswordDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        //set layout Linearlayout
        LinearLayout linearLayout = new LinearLayout(this);
//views to set in dailog
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);


        linearLayout.addView(emailEt);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        //button recover
        builder.setPositiveButton("Update Password ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);

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

    private void beginRecovery(String email) {

        dialog.setMessage("Sending email....");
        dialog.show();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                //get and show proper error message
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}