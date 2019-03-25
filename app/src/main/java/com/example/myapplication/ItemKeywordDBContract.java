package com.example.myapplication;

import android.provider.BaseColumns;

public class ItemKeywordDBContract {
    private ItemKeywordDBContract(){}

public static class KeywordEntry implements BaseColumns {
    public static final String TableName = "ItemKeywords";
    public static final String ColKeywords = "Keyword";
    public static final String ColItemID = "ItemID";

}

    public static final String CreateTable =
            "Create Table " +  KeywordEntry.TableName + " (" +
                    KeywordEntry.ColItemID + " integer, " +
                    KeywordEntry.ColKeywords + " text, "+
                    "foreign key ("+ KeywordEntry.ColItemID +
                    ") references "+ ItemDBContract.ItemEntry.TableName + "("+ ItemDBContract.ItemEntry.ColProductKey +"));";

    public static final String DropTable = "drop table if exists " + KeywordEntry.TableName;
}
