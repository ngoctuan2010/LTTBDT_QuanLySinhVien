package com.example.service;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pojo.Subject;
import com.example.studentmanagement.R;


import java.util.List;

public class SubjectArrayAdapter extends ArrayAdapter {

    Activity context = null;
    int layoutId;
    List<Subject> subjectList = null;

    public SubjectArrayAdapter(Activity context, int layoutId, List<Subject> subjectList) {
        super(context, layoutId, subjectList);
        this.context = context;
        this.layoutId = layoutId;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView tvSubjectName = (TextView) convertView.findViewById(R.id.tvLectureId);
        TextView tvSubjectCredit = (TextView) convertView.findViewById(R.id.tvLectureName);


        Subject sj = subjectList.get(position);
        tvSubjectName.setText("[" + Integer.toString(sj.getId()) + "] " + sj.getName());
        tvSubjectCredit.setText("Tín chỉ: " + Integer.toString(sj.getCredit()));

        return convertView;
    }
}
