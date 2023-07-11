package com.example.service;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojo.Subject;


import java.util.List;

public class SubjectArrayAdapter extends ArrayAdapter {

    Activity context = null;
    int layoutId;
    List<Subject> userList = null;

    public SubjectArrayAdapter(Activity context, int layoutId, List<Subject> userList) {
        super(context, layoutId, userList);
        this.context = context;
        this.layoutId = layoutId;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        return convertView;
    }
}
