package com.example.service;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojo.Student;
import com.example.studentmanagement.R;

import java.util.ArrayList;

public class StudentArrayAdapter extends ArrayAdapter<Student> {
    int layoutId;
    ArrayList<Student> arr = null;
    Activity context;

    public StudentArrayAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if (arr.size() > 0 && position >= 0) {
            TextView txt = convertView.findViewById(R.id.txtItemStudent);
            ImageView img = convertView.findViewById(R.id.imgItemStudent);
            CheckBox chk = convertView.findViewById(R.id.chkitemStudent);
            Student student = arr.get(position);
            txt.setText(student.getId() + ". " + student.getName());
            if (student.isGender())
                img.setImageResource(R.drawable.malestudent);
            else
                img.setImageResource(R.drawable.femalestudent);
        }
        return convertView;
    }
}
