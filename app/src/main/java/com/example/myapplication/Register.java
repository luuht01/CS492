package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Register extends AppCompatActivity {
    Button registerBTN, cancelBTN;
    EditText UsernameTXT, PasswordTXT;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.userDB = new UserDB(this);
        this.registerBTN = findViewById(R.id.registerBTN);
        this.cancelBTN = findViewById(R.id.cancelBTN);
        this.UsernameTXT = findViewById(R.id.usernameTXT);
        this.PasswordTXT = findViewById(R.id.passwordTXT);

        this.cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User _user = new User(UsernameTXT.getText().toString(),
                    PasswordTXT.getText().toString());
                long partKey = userDB.add(_user);
                if (partKey > 0) {
                    Toast.makeText(getApplicationContext(), "New user added", Toast.LENGTH_SHORT).show();
                    Intent activityIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(activityIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}
