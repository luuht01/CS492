package com.example.myapplication;

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

public class CreateActivity extends AppCompatActivity {

    Button addItemBTN, cancelBTN;
    EditText PartNumberTXT, webSiteTXT, PriceTXT, descriptionTXT, keyWoordsTXT;
    Spinner categorySpinner;
    ItemsDB itemDB;
    CategoryDB categoryDb;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        this.itemDB = new ItemsDB(this);
        this.categoryDb = new CategoryDB(this);
        this.addItemBTN = findViewById(R.id.addItemBTN);
        this.cancelBTN = findViewById(R.id.cancelBTN);
        this.PartNumberTXT = findViewById(R.id.PartNumberTXT);
        this.webSiteTXT = findViewById(R.id.webSiteTXT);
        this.PriceTXT = findViewById(R.id.PriceTXT);
        this.descriptionTXT = findViewById(R.id.descriptionTXT);
        this.keyWoordsTXT = findViewById(R.id.keywordsTXT);

        this.categoryDb = new CategoryDB(this);
        ArrayList<Category> categoryArray = categoryDb.getCategories();
        this.categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryArray);
        this.categorySpinner.setAdapter(categoryAdapter);

        this.cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.addItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category _category = (Category) categorySpinner.getSelectedItem();
                String keywords = keyWoordsTXT.getText().toString();
                ArrayList<String> _keywords = new ArrayList<>(Arrays.asList(keywords.split("\\s*(\\s|=>|,)\\s*")));

                if (addItemBTN.getText() == "Add") {
                    Item _item = new Item(PartNumberTXT.getText().toString(),
                            descriptionTXT.getText().toString(),
                            webSiteTXT.getText().toString(),
                            Double.valueOf(PriceTXT.getText().toString()),
                            new User(),
                            _category, _keywords);
                    long partKey = itemDB.add(_item);
                    if (partKey > 0) {
                        Toast.makeText(getApplicationContext(), "Part added ID:" + partKey, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Part add failed", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    item.setProductNumber(PartNumberTXT.getText().toString());
                    item.setDescription(descriptionTXT.getText().toString());
                    item.setWebsiteUrl(webSiteTXT.getText().toString());
                    item.setPrice(Double.valueOf(PriceTXT.getText().toString()));
                    item.setUpdateUser(new User());
                    item.setCategory(_category);
                    item.setKeywords(_keywords);
                    if (itemDB.update(item)) {
                        Toast.makeText(getApplicationContext(), "Part updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Part update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Bundle _bundle = getIntent().getExtras();
        if (_bundle != null) {
            long itemId = _bundle.getLong("ItemID");
            loadItem(itemId);
            this.addItemBTN.setText("Save Changes");
        } else {
            this.addItemBTN.setText("Add");
        }
    }

    private void loadItem(long _itemID) {

        this.itemDB = new ItemsDB(this);
        this.item = this.itemDB.getItem(_itemID);
        this.PartNumberTXT.setText(this.item.getProductNumber());
        this.descriptionTXT.setText(this.item.getDescription());
        this.webSiteTXT.setText(this.item.getWebsiteUrl());
        PriceTXT.setText(Double.valueOf(this.item.getPrice()).toString());

        String keywordString = "";
        int wordCount = 0;
        for (String word : this.item.keywords) {
            keywordString = keywordString + word;
            wordCount++;
            if (wordCount < this.item.keywords.size()) {
                keywordString = keywordString + ", ";
            }
        }
        keyWoordsTXT.setText(keywordString);

        categorySpinner.setSelection(((ArrayAdapter) categorySpinner.getAdapter()).getPosition(this.item.getCategory()));

    }

}
