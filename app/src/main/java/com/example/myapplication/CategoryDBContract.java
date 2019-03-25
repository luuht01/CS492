package com.example.myapplication;

import android.provider.BaseColumns;

public class CategoryDBContract {
    private CategoryDBContract(){}

public static class CategoryEntry implements BaseColumns {
    public static final String TableName = "ItemCategories";
    public static final String ColCategoryName = "CategoryName";
    public static final String ColCategoryKey = "Id";

}

    public static final String CreateTable =
            "Create Table " +  CategoryEntry.TableName + " (" +
                    CategoryEntry.ColCategoryKey + " integer primary key autoincrement, " +
                    CategoryEntry.ColCategoryName + " text)";

    public static final String DropTable = "drop table if exists " + CategoryEntry.TableName;
}
