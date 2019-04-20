package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class AccountActivity extends AppCompatActivity {
    boolean alert = false;
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
            }
        });

        Button btnAlert = (Button) findViewById(R.id.btnAlert);

        btnAlert.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(alert == false) {
                    Toast.makeText(getApplicationContext(), "Alerts will now be shown", Toast.LENGTH_SHORT);
                    alert = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Alerts will not be shown", Toast.LENGTH_SHORT);
                    alert = false;
                }
            }
        });
        Button btnLog = (Button) findViewById(R.id.btnLogout);
        btnLog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //set db activeuser to null
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
        Button btnExit = (Button) findViewById(R.id.btnExitAccount);
        btnExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });
    }
}
