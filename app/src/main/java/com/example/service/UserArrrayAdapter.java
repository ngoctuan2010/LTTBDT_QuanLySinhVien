package com.example.service;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojo.User;
import com.example.studentmanagement.QuanlyUser;
import com.example.studentmanagement.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserArrrayAdapter extends ArrayAdapter {
    Activity context = null;
    int layoutId;
    List<User> userList = null;
    public ArrayList<Boolean> checkList;

    QLSVDatabase db;

    public UserArrrayAdapter(Activity context, int layoutId, List<User> userList) {
        super(context, layoutId, userList);
        this.context = context;
        this.layoutId = layoutId;
        this.userList = userList;
        db = new QLSVDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if(userList.size() > 0 && position >= 0){
            TextView tvId = (TextView) convertView.findViewById(R.id.tvUserItemLecture);
            TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUserItemUsername);
            TextView tvRole = (TextView) convertView.findViewById(R.id.tvUserItemRole);
            ImageView ava = (ImageView) convertView.findViewById(R.id.imgUserItemAva);
            User user = userList.get(position);


            Cursor c = db.getLectureById(user.getLecture());
            c.moveToFirst();
            String title = "[" + Integer.toString(user.getId()) + "] " + c.getString(1);
            tvId.setText(title);
            String username = "Tài khoản: " + user.getUsername();
            tvUsername.setText(username);
            String role = User.ROLE.values()[user.getRole()].toString();
            tvRole.setText(role);

            String path = "";
            int uRole = user.getRole();
            if(uRole == 1)
                path = "user";
            else
                path = "admin";

            int img = context.getResources().getIdentifier(path, "drawable", context.getPackageName());
            ava.setImageResource(img);

        }

        return convertView;
    }


}
