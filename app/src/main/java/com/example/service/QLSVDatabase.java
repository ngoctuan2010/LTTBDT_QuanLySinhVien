package com.example.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class QLSVDatabase {
    SQLiteDatabase db;
    DBHelper helper;

    public QLSVDatabase(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }



}
