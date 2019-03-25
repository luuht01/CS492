package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CategoryDB extends Category{
    private ShopcastDBHelper DBhelper;
    CategoryDB(Context _context){
        DBhelper = new  ShopcastDBHelper(_context);

    }

    public long add(Category _category){
        ContentValues values = new ContentValues();
        values.put(CategoryDBContract.CategoryEntry.ColCategoryName,_category.name);
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        _category.id = db.insert(CategoryDBContract.CategoryEntry.TableName,null,values);
        db.close();
        return _category.id;
    }
 /*   public ArrayList<String> getCategories(){
        ArrayList<String> _categories=new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns ={CategoryDBContract.CategoryEntry.ColCategoryKey,CategoryDBContract.CategoryEntry.ColCategoryName};

        Cursor cursor= db.query(CategoryDBContract.CategoryEntry.TableName,columns,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                Category thisCategory = new Category();
                thisCategory.id = cursor.getLong(0);
                thisCategory.name = cursor.getString(1);
                _categories.add(thisCategory.getName());
            }while (cursor.moveToNext());
        }
        db.close();

        return _categories;
    }
    */
    public ArrayList<Category> getCategories(){
    ArrayList<Category> _categories=new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns ={CategoryDBContract.CategoryEntry.ColCategoryKey,CategoryDBContract.CategoryEntry.ColCategoryName};

    Cursor cursor= db.query(CategoryDBContract.CategoryEntry.TableName,columns,null,null,null,null,null);
    if(cursor.moveToFirst())
    {
        do {
            Category thisCategory;// = new Category();
            thisCategory=getCategory(cursor.getLong(0));
            //thisCategory.name = cursor.getString(1);
            _categories.add(thisCategory);
        }while (cursor.moveToNext());
    }
    db.close();

    return _categories;
    }
    public Category getCategory(long _categoryId){
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        Category thisCategory = new Category();
        String[] columns ={CategoryDBContract.CategoryEntry.ColCategoryKey,CategoryDBContract.CategoryEntry.ColCategoryName};
        String where = CategoryDBContract.CategoryEntry.ColCategoryKey+" =? ";
        String[] selection ={Long.toString(_categoryId)};
        Cursor cursor= db.query(CategoryDBContract.CategoryEntry.TableName,columns,where,selection,null,null,null);
        if(cursor.moveToFirst())
        {


                thisCategory.id = cursor.getLong(0);
                thisCategory.name = cursor.getString(1);


        }
        db.close();

        return thisCategory ; }
}

