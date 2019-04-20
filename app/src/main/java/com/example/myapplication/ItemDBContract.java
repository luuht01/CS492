package com.example.myapplication;

import android.provider.BaseColumns;

public class ItemDBContract {
    private ItemDBContract() {
    }

    public static class ItemEntry implements BaseColumns {
        public static final String TableName = "Items";
        public static final String ColProductKey = "ProductKey";
        public static final String ColProductNumber = "ProductNumber";
        public static final String ColDescription = "Description";
        public static final String ColWebsiteUrl = "WebsiteUrl";
        public static final String ColPrice = "Price";
        public static final String ColUpdateUserID = "UpdateUserID";
        public static final String ColUpdateDate = "UpdateDate";
        public static final String ColCategoryID = "CategoryID";
        public static final String ColImage="Image";
    }

    public static final String CreateTable =
            "Create Table " + ItemEntry.TableName + " (" +
                    ItemEntry.ColProductKey + " integer primary key autoincrement, " +
                    ItemEntry.ColProductNumber + " text, " +
                    ItemEntry.ColDescription + " text, " +
                    ItemEntry.ColWebsiteUrl + " text, " +
                    ItemEntry.ColPrice + " text, " +
                    ItemEntry.ColUpdateUserID + " integer, " +
                    ItemEntry.ColUpdateDate + " date, " +
                    ItemEntry.ColCategoryID + " integer, " +
                    ItemEntry.ColImage + " blob, " +
                    "foreign key ("+ ItemEntry.ColCategoryID +") references "+
                    CategoryDBContract.CategoryEntry.TableName +
                    "("+ CategoryDBContract.CategoryEntry.ColCategoryKey +"));";

    public static final String DropTable = "drop table if exists " + ItemEntry.TableName;
}
