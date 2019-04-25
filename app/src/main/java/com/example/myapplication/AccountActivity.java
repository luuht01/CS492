package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.app.NotificationManager;
import static java.security.AccessController.getContext;

public class AccountActivity extends AppCompatActivity {
    final String _username = getIntent().getExtras().getString("Username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button btnChangePassword = (Button) findViewById(R.id.btnChangePass);
        btnChangePassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
        Button btnEntry = (Button) findViewById(R.id.btnEntries);
        btnEntry.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //redirect a sliding menu with a list of all item entries
                startActivity(new Intent(AccountActivity.this, SearchActivity.class));
            }
        });

        Button btnAlert = (Button) findViewById(R.id.btnAlert);

        btnAlert.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                sendNotification(v);

            }
        });
        Button btnLog = (Button) findViewById(R.id.btnLogout);
        btnLog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("Username", _username);
                startActivity(intent);
            }
        });
        Button btnExit = (Button) findViewById(R.id.btnExitAcc);
        btnExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Username", _username);
                startActivity(intent);
            }
        });
    }
    public void sendNotification(View view) {

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(001, mBuilder.build());
    }

}
