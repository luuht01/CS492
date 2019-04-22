package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SearchItemActivity extends AppCompatActivity {

    Button addToFavBTN, backBTN;
    TextView itemNum, category, description, price, websites, keywords;
    ImageView imageView;
    ItemsDB itemDB;
    CategoryDB categoryDb;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        this.itemDB = new ItemsDB(this);
        this.categoryDb = new CategoryDB(this);
        this.addToFavBTN = findViewById(R.id.addToFavBTN);
        this.backBTN = findViewById(R.id.backToSearchBTN);
        this.itemNum = findViewById(R.id.itemNumContentTV);
        this.websites = findViewById(R.id.websiteContentTV);
        this.price = findViewById(R.id.priceContentTV);
        this.keywords = findViewById(R.id.keywordsContentTV);
        this.description = findViewById(R.id.descriptionContentTV);
        this.imageView=findViewById(R.id.imageView);

        this.categoryDb = new CategoryDB(this);
        ArrayList<Category> categoryArray = categoryDb.getCategories();
        this.category = findViewById(R.id.categoryContentTV);

        this.backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.addToFavBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Bundle _bundle = getIntent().getExtras();
        if (_bundle != null) {
            long itemId = _bundle.getLong("ItemID");
            loadItem(itemId);
        } else {

        }
    }

    private void loadItem(long _itemID) {

        this.itemDB = new ItemsDB(this);
        this.item = this.itemDB.getItem(_itemID);
        this.itemNum.setText(this.item.getProductNumber());
        this.description.setText(this.item.getDescription());
        this.websites.setText(this.item.getWebsiteUrl());
        this.price.setText(Double.valueOf(this.item.getPrice()).toString());
        ByteArrayInputStream imageStream = new ByteArrayInputStream(this.item.getImage());
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        this.imageView.setImageBitmap(theImage);

        String keywordString = "";
        int wordCount = 0;
        for (String word : this.item.keywords) {
            keywordString = keywordString + word;
            wordCount++;
            if (wordCount < this.item.keywords.size()) {
                keywordString = keywordString + ", ";
            }
        }
        keywords.setText(keywordString);

    }

}
