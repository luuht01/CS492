package com.example.myapplication;

import android.provider.BaseColumns;

public class UserDBContract
{
    public static class UserEntry implements BaseColumns {
        public static final String TableName = "Users";
        public static final String ColUserId = "UserID";
        public static final String ColUsername = "Username";
        public static final String ColPassword = "Password";
    }

    public static final String CreateTable =
            "Create Table " + UserEntry.TableName + " (" +
                    UserEntry.ColUserId + " integer primary key autoincrement, " +
                    UserEntry.ColUsername + " text unique, " +
                    UserEntry.ColPassword + " text); ";

    public static final String DropTable = "drop table if exists " + UserEntry.TableName;
}
