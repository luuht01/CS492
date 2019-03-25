package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ListView itemList;
    ItemsDB itemDb;
    Spinner categorySpinner;
    CategoryDB categoryDb;
    EditText searchTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.itemDb = new ItemsDB(this);
        this.itemList = findViewById(R.id.itemLst);
        ArrayList items = itemDb.getAllItems();
        this.itemList.setAdapter(new CustomListAdapter(getApplicationContext(), items));
        this.itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), CreateActivity.class);
                Item _item = (Item)parent.getItemAtPosition(position);
                intent.putExtra("ItemID",_item.id);
                startActivity(intent);
            }
        });
        this.searchTxt = findViewById(R.id.searchTxt);
        this.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Category _category= (Category)categorySpinner.getSelectedItem();
                ArrayList items = itemDb.getItems(searchTxt.getText().toString(),_category.id);
                itemList.setAdapter(new CustomListAdapter(getApplicationContext(), items));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //itemList.setAdapter(new CustomListAdapter(this, items));

        this.categoryDb = new CategoryDB(this);
        ArrayList<Category> categoryArray = categoryDb.getCategories();
        this.categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryArray);
        this.categorySpinner.setAdapter(categoryAdapter);
        this.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category _category= (Category)categorySpinner.getSelectedItem();
                ArrayList items = itemDb.getItems(searchTxt.getText().toString(),_category.id);
                itemList.setAdapter(new CustomListAdapter(getApplicationContext(), items));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}


