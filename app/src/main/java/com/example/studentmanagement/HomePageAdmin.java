package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.service.QLSVDatabase;
import com.example.service.SharePreferenceServeice;

public class HomePageAdmin extends AppCompatActivity {
    ImageButton mbtnStudent, mbtnCollage, mbtnSubject, mbtnClass, mbtnUser, mbtnStatistical;
    TextView mtxtName, mtxtId, mtxtBirth;

    QLSVDatabase db;

    SharePreferenceServeice sharePreferenceServeice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);

        mbtnStudent = (ImageButton) findViewById(R.id.btnStudent);
        mbtnCollage = (ImageButton) findViewById(R.id.btnCollage);
        mbtnSubject = (ImageButton) findViewById(R.id.btnSubject);
        mbtnClass = (ImageButton) findViewById(R.id.btnClass);
        mbtnUser = (ImageButton) findViewById(R.id.btnUserManagement);
        mbtnStatistical = (ImageButton) findViewById(R.id.btnStatistical);

        mtxtName = (TextView) findViewById(R.id.txtName);

        db = new QLSVDatabase(this);
        sharePreferenceServeice = new SharePreferenceServeice(this, "User");

        int IdLecture = Integer.parseInt(sharePreferenceServeice.getString("current_user"));
        Cursor cursorLecture = db.getLectureById(IdLecture);
        cursorLecture.moveToFirst();

//        SetText textview
        mtxtName.setText(cursorLecture.getString(1));

        mbtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdmin.this, QuanlySinhvien.class);
                startActivity(intent);
            }
        });
        mbtnCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdmin.this, QuanLyGiangVien.class);
                startActivity(intent);
            }
        });
        mbtnSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdmin.this, QuanlyMonhoc.class);
                startActivity(intent);
            }
        });
        mbtnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageAdmin.this, QuanlyLophoc.class);
                startActivity(intent);
            }
        });
        mbtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageAdmin.this, QuanlyUser.class);
                startActivity(intent);
            }
        });
        mbtnStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageAdmin.this, AdminStatistical.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.setGroupVisible(R.id.grMenuDefault, true);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClick() {
        Toast.makeText(this, "Yo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_profile){
            Intent intent = new Intent(HomePageAdmin.this, ProfileLecture.class);
            Bundle bundle = new Bundle();
            bundle.putInt("LectureId", Integer.parseInt(sharePreferenceServeice.getString("current_user")));
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (id == R.id.action_class) {
            Intent intent = new Intent(HomePageAdmin.this, QuanlyLophoc.class);
            startActivity(intent);
        }

        if (id == R.id.action_collape) {
            Intent intent = new Intent(HomePageAdmin.this, QuanLyGiangVien.class);
            startActivity(intent);
        }

        if (id == R.id.action_student) {
            Intent intent = new Intent(HomePageAdmin.this, QuanlySinhvien.class);
            startActivity(intent);
        }

        if (id == R.id.action_subject) {
            Intent intent = new Intent(HomePageAdmin.this, QuanlyMonhoc.class);
            startActivity(intent);
        }

        if (id == R.id.action_sign_out) {
            sharePreferenceServeice.clear();
            Intent intent = new Intent(HomePageAdmin.this, LogIn.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_user) {
            Intent intent = new Intent(HomePageAdmin.this, QuanlyUser.class);
            startActivity(intent);
        }
        if (id == R.id.action_statistical) {
            Intent intent = new Intent(HomePageAdmin.this, AdminStatistical.class);
            startActivity(intent);
        }
        return true;
    }
}