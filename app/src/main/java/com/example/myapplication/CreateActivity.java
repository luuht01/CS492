package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import android.media.MediaScannerConnection;

public class CreateActivity extends AppCompatActivity {

    Button addItemBTN, cancelBTN;
    EditText PartNumberTXT, webSiteTXT, PriceTXT, descriptionTXT, keyWoordsTXT;
    Spinner categorySpinner;
    ImageButton imageButton;
    String username;
    User user;
    ItemsDB itemDB;
    CategoryDB categoryDb;
    UserDB userDb;
    private Item item;
    byte itemImage[];
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        this.itemDB = new ItemsDB(this);
        this.userDb = new UserDB(this);
        this.categoryDb = new CategoryDB(this);
        this.addItemBTN = findViewById(R.id.addItemBTN);
        this.cancelBTN = findViewById(R.id.cancelBTN);
        this.PartNumberTXT = findViewById(R.id.PartNumberTXT);
        this.webSiteTXT = findViewById(R.id.webSiteTXT);
        this.PriceTXT = findViewById(R.id.PriceTXT);
        this.descriptionTXT = findViewById(R.id.descriptionTXT);
        this.imageButton=findViewById(R.id.imageButton);
        this.keyWoordsTXT = findViewById(R.id.keywordsTXT);

        username = getIntent().getExtras().getString("Username");
        user = userDb.getUser(username);

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
                            user,
                            _category, itemImage,_keywords);
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
                    item.setImage(itemImage);
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
        this.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showPictureDialog();}
        });
        Bundle _bundle = getIntent().getExtras();
        if (_bundle != null && _bundle.containsKey("ItemID")) {
            long itemId = _bundle.getLong("ItemID");
            loadItem(itemId);
            this.addItemBTN.setText("Save Changes");
        } else {
            this.addItemBTN.setText("Add");
        }
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
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


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
                    itemImage =stream.toByteArray();
                    imageButton.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
            itemImage=stream.toByteArray();
            imageButton.setImageBitmap(bitmap);


            //imageButton.setImageBitmap(thumbnail);
            //saveImage(thumbnail);
            Toast.makeText(CreateActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
