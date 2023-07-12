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

import com.example.pojo.User;
import com.example.studentmanagement.R;

import org.w3c.dom.Text;

import java.util.List;

public class UserArrrayAdapter extends ArrayAdapter {
    Activity context = null;
    int layoutId;
    List<User> userList = null;

    public UserArrrayAdapter(Activity context, int layoutId, List<User> userList) {
        super(context, layoutId, userList);
        this.context = context;
        this.layoutId = layoutId;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if(userList.size() > 0 && position >= 0){
            TextView tvId = (TextView) convertView.findViewById(R.id.tvUserItemID);
            TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUserItemUsername);
            TextView tvRole = (TextView) convertView.findViewById(R.id.tvUserItemRole);

            User user = userList.get(position);

            tvId.setText(Integer.toString(user.getId()));
            tvUsername.setText(user.getUsername());
            tvRole.setText(Integer.toString(user.getRole()));
        }

        return convertView;
    }
}
