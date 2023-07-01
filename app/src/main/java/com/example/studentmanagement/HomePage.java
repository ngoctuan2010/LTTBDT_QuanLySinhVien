package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    ImageButton mbtnStudent, mbtnCollage, mbtnSubject, mbtnClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mbtnStudent = (ImageButton) findViewById(R.id.btnStudent);
        mbtnCollage = (ImageButton) findViewById(R.id.btnCollage);
        mbtnSubject = (ImageButton) findViewById(R.id.btnSubject);
        mbtnClass = (ImageButton) findViewById(R.id.btnClass);

        mbtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Bạn chọn vào quản lý sinh viên", Toast.LENGTH_SHORT).show();
            }
        });
        mbtnCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Bạn chọn vòa quản lý giảng viên", Toast.LENGTH_SHORT).show();
            }
        });
        mbtnSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Bạn chọn vòa quản lý môn học", Toast.LENGTH_SHORT).show();
            }
        });
        mbtnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Bạn chọn vòa quản lý lớp học", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater.inflate(R.menu.action_menu, menu);
        return true;
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
            Toast.makeText(this, "Bạn vào trang sửa giảng viên", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_student) {
            Toast.makeText(this, "Bạn vào trang sửa sinh viên", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_subject) {
            Toast.makeText(this, "Bạn vào trang sửa môn học", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_sign_out) {
            Intent intent = new Intent(HomePage.this, LogIn.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}