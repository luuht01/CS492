package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    Button submitBTN, cancelBTN;
    EditText PasswordTXT;
    UserDB userDb;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        String username=getIntent().getExtras().getString("Username");
        this.userDb = new UserDB(this);
        currentUser= this.userDb.getUser(username);

        this.submitBTN = findViewById(R.id.submitBTN);
        this.cancelBTN = findViewById(R.id.cancelBTN);
        this.PasswordTXT = findViewById(R.id.passwordTXT);

        this.cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (currentUser.id>0){
                   currentUser.password=PasswordTXT.getText().toString();

                userDb.update(currentUser);
                   Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_LONG).show();
                   finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Password Update Failed", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}
