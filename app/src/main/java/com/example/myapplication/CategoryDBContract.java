package com.example.myapplication;

import android.provider.BaseColumns;

    class CategoryDBContract {
    private CategoryDBContract(){}

    static class CategoryEntry implements BaseColumns {
    static final String TableName = "ItemCategories";
    static final String ColCategoryName = "CategoryName";
    static final String ColCategoryKey = "Id";

}

    static final String CreateTable =
            "Create Table " +  CategoryEntry.TableName + " (" +
                    CategoryEntry.ColCategoryKey + " integer primary key autoincrement, " +
                    CategoryEntry.ColCategoryName + " text)";

    static final String DropTable = "drop table if exists " + CategoryEntry.TableName;
}
