package com.example.service;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pojo.Class;
import com.example.studentmanagement.HomePageLecture;
import com.example.studentmanagement.R;

import java.util.ArrayList;

public class HomeSubjectArrayAdapter extends ArrayAdapter {
    QLSVDatabase db;
    Activity context = null;
    ArrayList<Class> listSubject = null;
    int layoutId;

    public HomeSubjectArrayAdapter(Activity context, int resource, ArrayList<Class> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listSubject = objects;
        this.layoutId = resource;
        db = new QLSVDatabase(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if(listSubject.size() > 0 && position >= 0) {
            TextView tvSubjectName = (TextView) convertView.findViewById(R.id.tvSubjectName);
            TextView tvClassName = (TextView) convertView.findViewById(R.id.tvSubjectClass);
            TextView tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            TextView tvNumberStudent = (TextView) convertView.findViewById(R.id.tvNumberStudent);

            Class aClass = listSubject.get(position);
            tvClassName.setText("Lớp: " + aClass.getName().toString());
            tvYear.setText("Khóa: " + aClass.getYear().toString());
            tvNumberStudent.setText("Sỉ số: " + Integer.toString(aClass.getQuantity()));

            int idSubject = aClass.getSubject_id();
//            Toast.makeText(context, Integer.toString(idSubject), Toast.LENGTH_SHORT).show();
            Cursor cursor = db.getListSubjectById(idSubject);
            cursor.moveToFirst();
//            Toast.makeText(context, Integer.toString(cursor.getCount()), Toast.LENGTH_SHORT).show();
            String subjectName = cursor.getString(1);
            tvSubjectName.setText(subjectName);
        }
        return convertView;
    }
}
