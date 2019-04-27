package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class UserDB extends User
{
    private ShopcastDBHelper DBhelper;
    private Context context;

    UserDB(Context _context) {
        this.context = _context;
        DBhelper = new ShopcastDBHelper(this.context);
    }

    public long add(User _user) {
        try {
            ContentValues values = new ContentValues();
            values.put(UserDBContract.UserEntry.ColUsername, _user.getUsername());
            values.put(UserDBContract.UserEntry.ColPassword, _user.getPassword());
            SQLiteDatabase db = DBhelper.getWritableDatabase();
            _user.id = db.insert(UserDBContract.UserEntry.TableName, null, values);
            db.close();
            this.getAllUsers();
            return _user.id;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public User getUser(String _username, String _password) {
        try {
            User targetUser = new User();
            SQLiteDatabase db = DBhelper.getReadableDatabase();
            String[] columns = {UserDBContract.UserEntry.ColUserId,
                    UserDBContract.UserEntry.ColUsername,
                    UserDBContract.UserEntry.ColPassword};
            String where = UserDBContract.UserEntry.ColUsername + " =? and "
                    + UserDBContract.UserEntry.ColPassword + " =? ";
            String[] selection = {_username, _password};
            Cursor cursor = db.query(UserDBContract.UserEntry.TableName, columns, where, selection, null, null, null);
            if (cursor.moveToFirst()) {
                targetUser.id = cursor.getLong(0);
                targetUser.setUsername(cursor.getString(1));
                targetUser.setPassword(cursor.getString(2));
            }
            db.close();
            if (targetUser.id>0){
                return targetUser;
            }else{
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public User getUser(String _username) {
        User targetUser = new User();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {UserDBContract.UserEntry.ColUserId,
                UserDBContract.UserEntry.ColUsername,
                UserDBContract.UserEntry.ColPassword};
        String where = UserDBContract.UserEntry.ColUsername + " =? ";
        String[] selection = {_username};
        Cursor cursor = db.query(UserDBContract.UserEntry.TableName, columns, where, selection, null, null, null);
        if (cursor.moveToFirst()) {
            targetUser.id = cursor.getLong(0);
            targetUser.setUsername(cursor.getString(1));
            targetUser.setPassword(cursor.getString(2));
        }
        db.close();

        return targetUser;
    }

    public boolean update(User _user){
        ContentValues values = new ContentValues();
        values.put(UserDBContract.UserEntry.ColUsername, _user.getUsername());
        values.put(UserDBContract.UserEntry.ColPassword, _user.getPassword());
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        db.update(UserDBContract.UserEntry.TableName,values,UserDBContract.UserEntry.ColUserId+"=?",new String[]{String.valueOf(_user.id)});
        db.close();

        return true;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> _users = new ArrayList<>();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {UserDBContract.UserEntry.ColUserId};

        Cursor cursor = db.query(UserDBContract.UserEntry.TableName, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User thisUser = getUser(cursor.getLong(0));
                _users.add(thisUser);
            } while (cursor.moveToNext());
        }
        db.close();

        return _users;
    }

    public User getUser(long _userId) {
        CategoryDB _categoryDb = new CategoryDB(this.context);
        User thisUser = new User();
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        String[] columns = {UserDBContract.UserEntry.ColUserId,
                UserDBContract.UserEntry.ColUsername};
        String where = UserDBContract.UserEntry.ColUserId + " =? ";
        String[] selection = {Long.toString(_userId)};
        Cursor cursor = db.query(UserDBContract.UserEntry.TableName, columns, where, selection, null, null, null);
        if (cursor.moveToFirst()) {
            thisUser.id = cursor.getLong(0);
            thisUser.setUsername(cursor.getString(1));
        }
        db.close();

        return thisUser;
    }
}
