package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {
    CategoryDB categoryDB = new CategoryDB(this);
    Button addCategoryBtn, exitBtn;
    EditText categoryNameEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        this.addCategoryBtn  =findViewById(R.id.addCategoryBtn);
        this.categoryNameEdt = findViewById(R.id.categoryNameEdt);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category _category = new Category(categoryNameEdt.getText().toString());
                long categoryID=categoryDB.add(_category);
                if(categoryID>0){
                    Toast.makeText(getApplicationContext(),("New Category " + _category.getName() + ", ID: " + _category.getId()),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"New Category insert Failed",Toast.LENGTH_LONG).show();
                }

            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
