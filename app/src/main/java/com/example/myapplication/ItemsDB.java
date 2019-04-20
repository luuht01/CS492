package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ItemsDB extends Item {
    private ShopcastDBHelper DBhelper;
    private Context context;

    ItemsDB(Context _context) {
        this.context = _context;
        DBhelper = new ShopcastDBHelper(this.context);
    }

    public long add(Item _item) {
        ContentValues values = new ContentValues();
        values.put(ItemDBContract.ItemEntry.ColProductNumber, _item.getProductNumber());
        values.put(ItemDBContract.ItemEntry.ColDescription, _item.getDescription());
        values.put(ItemDBContract.ItemEntry.ColPrice, _item.getPrice());
        values.put(ItemDBContract.ItemEntry.ColUpdateDate, _item.updateDate.toString());
        values.put(ItemDBContract.ItemEntry.ColUpdateUserID, _item.getUpdateBy().getId());
        values.put(ItemDBContract.ItemEntry.ColCategoryID, _item.getCategory().getId());
        values.put(ItemDBContract.ItemEntry.ColWebsiteUrl, _item.getWebsiteUrl());
        values.put(ItemDBContract.ItemEntry.ColImage,_item.image);
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        _item.id = db.insert(ItemDBContract.ItemEntry.TableName, null, values);
        db.close();
        if (_item.id > 0) {
            if (!addKeywords(_item)) {
                return -1;
            }
        }
        this.getAllItems();
        return _item.id;
    }
    public boolean update(Item _item){
        ContentValues values = new ContentValues();
        values.put(ItemDBContract.ItemEntry.ColProductNumber, _item.getProductNumber());
        values.put(ItemDBContract.ItemEntry.ColDescription, _item.getDescription());
        values.put(ItemDBContract.ItemEntry.ColPrice, _item.getPrice());
        values.put(ItemDBContract.ItemEntry.ColUpdateDate, _item.updateDate.toString());
        values.put(ItemDBContract.ItemEntry.ColUpdateUserID, _item.getUpdateBy().getId());
        values.put(ItemDBContract.ItemEntry.ColCategoryID, _item.getCategory().getId());
        values.put(ItemDBContract.ItemEntry.ColWebsiteUrl, _item.getWebsiteUrl());
        values.put(ItemDBContract.ItemEntry.ColImage,_item.image);
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        db.update(ItemDBContract.ItemEntry.TableName,values,ItemDBContract.ItemEntry.ColProductKey+"=?",new String[]{String.valueOf(_item.id)});
        db.close();
        removeKeywords(_item);

        return addKeywords(_item);
    }
    private boolean removeKeywords(Item _item){
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        if(db.delete(ItemKeywordDBContract.KeywordEntry.TableName, ItemKeywordDBContract.KeywordEntry.ColItemID + " = ?",new String[]{String.valueOf(_item.id)}) >0){
            return true;
        }else{
            return false;
        }
    }
    private boolean addKeywords(Item _item) {
        boolean returnvalue = false;
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int count = 0;
        for (String word : _item.keywords) {
            values.put(ItemKeywordDBContract.KeywordEntry.ColItemID, _item.id);
            values.put(ItemKeywordDBContract.KeywordEntry.ColKeywords, word);
            long rowid = db.insert(ItemKeywordDBContract.KeywordEntry.TableName, null, values);
            if (rowid >= 0) {
                count++;
            }
        }
        db.close();
        if (count == _item.keywords.size()) {
            returnvalue = true;
        }
        return returnvalue;
    }

    public ArrayList<Item> getItems(String _descOrKwd, long _categoryId) {
        ArrayList<Item> _items = new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String queryString = "Select  DISTINCT  " + ItemDBContract.ItemEntry.ColProductKey +
                " from " + ItemDBContract.ItemEntry.TableName
                + " inner join " + ItemKeywordDBContract.KeywordEntry.TableName +
                " on " + ItemKeywordDBContract.KeywordEntry.TableName + "." +
                ItemKeywordDBContract.KeywordEntry.ColItemID +
                " = " + ItemDBContract.ItemEntry.TableName + "." +
                ItemDBContract.ItemEntry.ColProductKey +
                " Where " + ItemDBContract.ItemEntry.ColCategoryID + "=?"
                + " and (" + ItemDBContract.ItemEntry.ColDescription + " like ? " +
                "or " + ItemKeywordDBContract.KeywordEntry.ColKeywords + " like ? " +
                "or " + ItemDBContract.ItemEntry.ColProductNumber + " like ? )";
        _descOrKwd = "%" + _descOrKwd + "%";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(_categoryId), _descOrKwd, _descOrKwd, _descOrKwd});

        if (cursor.moveToFirst()) {
            do {
                Item thisItem = getItem(cursor.getLong(0));
                _items.add(thisItem);
            } while (cursor.moveToNext());
        }
        db.close();

        return _items;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> _items = new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {ItemDBContract.ItemEntry.ColProductKey};

        Cursor cursor = db.query(ItemDBContract.ItemEntry.TableName, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Item thisItem = getItem(cursor.getLong(0));
                _items.add(thisItem);
            } while (cursor.moveToNext());
        }
        db.close();

        return _items;
    }

    public Item getItem(long _itemId) {
        CategoryDB _categoryDb = new CategoryDB(this.context);
        Item thisItem = new Item();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {ItemDBContract.ItemEntry.ColProductKey,
                ItemDBContract.ItemEntry.ColProductNumber,
                ItemDBContract.ItemEntry.ColDescription,
                ItemDBContract.ItemEntry.ColCategoryID,
                ItemDBContract.ItemEntry.ColPrice,
                ItemDBContract.ItemEntry.ColUpdateDate,
                ItemDBContract.ItemEntry.ColUpdateUserID,
                ItemDBContract.ItemEntry.ColWebsiteUrl,
                ItemDBContract.ItemEntry.ColImage};
        String where = ItemDBContract.ItemEntry.ColProductKey + " =? ";//+ Long.toString(itemId);
        String[] selection = {Long.toString(_itemId)};
        Cursor cursor = db.query(ItemDBContract.ItemEntry.TableName, columns, where, selection, null, null, null);
        if (cursor.moveToFirst()) {
            thisItem.id = cursor.getLong(0);
            thisItem.setProductNumber(cursor.getString(1));
            thisItem.setDescription(cursor.getString(2));
            thisItem.setCategory(_categoryDb.getCategory(cursor.getLong(3)));
            thisItem.setPrice(cursor.getDouble(4));
            thisItem.updateDate= ZonedDateTime.parse(cursor.getString(5));
            //thisItem.updateBy=new User(cursor.getLong(6));
            thisItem.setWebsiteUrl(cursor.getString(7));
            thisItem.setImage(cursor.getBlob(8));
            thisItem.keywords = getKeywords(thisItem.id);

        }
        db.close();

        return thisItem;
    }

    public ArrayList<String> getKeywords(long itemId) {
        ArrayList<String> _keywords = new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {ItemKeywordDBContract.KeywordEntry.ColItemID, ItemKeywordDBContract.KeywordEntry.ColKeywords};
        String where = ItemKeywordDBContract.KeywordEntry.ColItemID + " =? ";//+ Long.toString(itemId);
        String[] selection = {Long.toString(itemId)};
        Cursor cursor = db.query(ItemKeywordDBContract.KeywordEntry.TableName, columns, where, selection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                _keywords.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        db.close();

        return _keywords;
    }

}
