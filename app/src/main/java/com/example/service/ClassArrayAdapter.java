package com.example.service;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojo.Class;
import com.example.studentmanagement.R;

import java.util.List;

public class ClassArrayAdapter extends ArrayAdapter {
    Activity context = null;
    int layoutID;
    List<Class> listClass = null;

    public ClassArrayAdapter(Activity context, int layoutID, List<Class> listClass) {
        super(context, layoutID, listClass);
        this.context = context;
        this.layoutID = layoutID;
        this.listClass = listClass;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.listview_class_item, null);
        if(listClass.size() > 0 && position >= 0){
            TextView tvSubject = (TextView) convertView.findViewById(R.id.tvClassSubject);
            TextView tvClass = (TextView) convertView.findViewById(R.id.tvClassName);
            TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvClassQuantity);
            TextView tvLecture = (TextView) convertView.findViewById(R.id.tvClassLecture);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tvClassStarted);
            TextView tvYear = (TextView) convertView.findViewById(R.id.tvClassYear);

            Class cl = listClass.get(position);

            tvSubject.setText(Integer.toString(cl.getSubject_id()));
            tvClass.setText("[" + cl.getId() + "] " + cl.getName());
            tvQuantity.setText("SL: " +Integer.toString(cl.getQuantity()));
            tvLecture.setText("GV: " + Integer.toString(cl.getLecture()));
            tvDate.setText("Thời gian bắt đầu: " + cl.getStarted());
            tvYear.setText("Khoá: " + cl.getYear());


        }
        return convertView;
    }
}
