package com.example.service;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojo.Lecture;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class LectureArrayAdapter extends ArrayAdapter {
    Activity context = null;
    int layoutID;
    ArrayList<Lecture> listLecture = null;
    public LectureArrayAdapter(Activity context, int resource, ArrayList<Lecture> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.listLecture = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        TextView tvId = (TextView) convertView.findViewById(R.id.tvLectureId);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvLectureName);

        Lecture lecture = listLecture.get(position);
        tvId.setText(Integer.toString(lecture.getId()));
        tvName.setText(lecture.getName());

        return convertView;
    }
}
