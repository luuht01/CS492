package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopcastDBHelper extends SQLiteOpenHelper {
    public static final String DatabaseName="Shopcast.db";
   // public static final String TableName="Items";

    public ShopcastDBHelper(Context context){
        super(context,DatabaseName, null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ItemDBContract.CreateTable);
        db.execSQL(CategoryDBContract.CreateTable);
        db.execSQL(ItemKeywordDBContract.CreateTable);
        db.execSQL(UserDBContract.CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion){
        db.execSQL(ItemDBContract.DropTable);
        db.execSQL(CategoryDBContract.DropTable);
        db.execSQL(ItemKeywordDBContract.DropTable);
        db.execSQL(UserDBContract.DropTable);
        onCreate(db);
    }
}

