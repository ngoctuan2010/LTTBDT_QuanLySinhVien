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

public class HomePageAdmin extends AppCompatActivity {
    ImageButton mbtnStudent, mbtnCollage, mbtnSubject, mbtnClass;
    TextView mtxtName, mtxtId, mtxtBirth;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);

        mbtnStudent = (ImageButton) findViewById(R.id.btnStudent);
        mbtnCollage = (ImageButton) findViewById(R.id.btnCollage);
        mbtnSubject = (ImageButton) findViewById(R.id.btnSubject);
        mbtnClass = (ImageButton) findViewById(R.id.btnClass);

        mtxtName = (TextView) findViewById(R.id.txtName);
        mtxtId = (TextView) findViewById(R.id.txtId);
        mtxtBirth = (TextView) findViewById(R.id.txtBirth);

        db = new QLSVDatabase(this);

        Bundle bundle = getIntent().getExtras();
        int idUser = bundle.getInt("idUser");
        Cursor cursor = db.getUserById(idUser);
        cursor.moveToFirst();
        int idLecture = cursor.getInt(3);
        Cursor cursorLecture = db.getLectureById(idLecture);
        cursorLecture.moveToFirst();

//        SetText textview
        mtxtName.setText(cursorLecture.getString(1));
        mtxtId.setText(cursorLecture.getString(0));
        mtxtBirth.setText(cursorLecture.getString(4));

        mbtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageAdmin.this, "Bạn chọn vào quản lý sinh viên", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(HomePageAdmin.this, "Bạn chọn vòa quản lý lớp học", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_profile) {
            Toast.makeText(this, "Bạn vào trang cá nhân", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_class) {
            Toast.makeText(this, "Bạn vào trang sửa lớp", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_collape) {
            Intent intent = new Intent(HomePageAdmin.this, QuanLyGiangVien.class);
            startActivity(intent);
        }

        if (id == R.id.action_student) {
            Toast.makeText(this, "Bạn vào trang sửa sinh viên", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_subject) {
            Intent intent = new Intent(HomePageAdmin.this, QuanlyMonhoc.class);
            startActivity(intent);
        }

        if (id == R.id.action_sign_out) {
            Intent intent = new Intent(HomePageAdmin.this, LogIn.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}