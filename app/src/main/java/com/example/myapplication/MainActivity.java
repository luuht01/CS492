package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button addItemBTN, addCategoryBTN,listItemsBtn, btnAcc, btnExit, btnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Remove next two lines from production
        //These drop db on each run
        //ShopcastDBHelper ShopcastDB = new  ShopcastDBHelper(this);
        //this.deleteDatabase(ShopcastDB.DatabaseName);
        
        this.addItemBTN =findViewById(R.id.addPartBtn);
        this.addCategoryBTN = findViewById(R.id.addCategoryBtn);
        this.listItemsBtn=findViewById(R.id.listItemsBtn);

        final String _username = getIntent().getExtras().getString("Username");
		this.btnAcc=findViewById(R.id.btnAccount);
        this.btnExit=findViewById(R.id.btnExit);

        addItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CreateActivity.class);
                intent.putExtra("Username", _username);
                startActivity(intent);
            }
        });
        addCategoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CategoryActivity.class);
                intent.putExtra("Username", _username);
                startActivity(intent);
            }
        });
        listItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SearchActivity.class);
				intent.putExtra("Username", _username);
                startActivity(intent);
            }
        });
        btnAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
