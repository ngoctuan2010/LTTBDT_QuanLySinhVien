package com.example.studentmanagement.information;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pojo.User;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

public class UserInformation extends AppCompatActivity {

    TextView tvID, tvUsername, tvRole, tvLecture;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        db = new QLSVDatabase(this);

        tvID = (TextView) findViewById(R.id.tvUserInfoId);
        tvUsername = (TextView) findViewById(R.id.tvUserInfoUsername);
        tvRole = (TextView) findViewById(R.id.tvUserInfoRole);
        tvLecture = (TextView) findViewById(R.id.tvUserInfoLecture);

        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable("User");

        tvID.setText("ID: " + Integer.toString(user.getId()));
        tvUsername.setText("Tài khoản: " + user.getUsername());
        tvRole.setText("Vai trò: " + User.ROLE.values()[user.getRole()]);
        Cursor lecture = db.getLectureById(user.getLecture());
        lecture.moveToFirst();
        tvLecture.setText("Chủ sở hữu: " + lecture.getString(1) + " - " + lecture.getString(0));


    }
}