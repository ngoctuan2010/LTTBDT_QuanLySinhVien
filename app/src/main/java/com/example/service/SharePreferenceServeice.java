package com.example.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceServeice {
    private Context context;
    private String name;
    SharedPreferences myPref;
    SharedPreferences.Editor editor;

    public SharePreferenceServeice(Context context, String name) {
        this.context = context;
        this.name = name;
        this.myPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = myPref.edit();
    }

    public boolean putString(String key, String value){
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key){
        return myPref.getString(key, "");
    }

    public void remove(String key){
        editor.remove(key);
        editor.apply();
    }

    public void clear(){
        for(String key : myPref.getAll().keySet()){
            editor.remove(key);
        }
        editor.apply();
    }
}
