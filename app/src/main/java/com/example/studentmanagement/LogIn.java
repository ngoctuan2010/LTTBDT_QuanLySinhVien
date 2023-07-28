package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.pojo.Lecture;
import com.example.pojo.Subject;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;

public class LogIn extends AppCompatActivity {
    EditText medtUsername, medtPassword;
    CheckBox mckRemember;
    Button mbtnLogin, mbtnRegister, mbtnForgotPassword;

    QLSVDatabase db;
    int idRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        db = new QLSVDatabase(this);
//        db.add_subject(new Subject(1, "Lập trình Java", 4));
//        db.add_class(new Class(1, "IT02", 1, 1, 45, "2022-2023", "03/07/2022"));
//        db.add_lecture(new Lecture(1, "Trong Nghia"));
//        db.add_lecture(new Lecture(999, "Ngoc Tuan"));
//        db.add_user(new User(1, "admin", "123", 1, 1));
//        db.add_user(new User(1, "nghia", "123", 0, 1));

        medtUsername = (EditText) findViewById(R.id.edtMaGV);
        medtPassword = (EditText) findViewById(R.id.edtPassword);

        mckRemember = (CheckBox) findViewById(R.id.ckRemember);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        mbtnRegister = (Button) findViewById(R.id.btnSwitchRegister);
        mbtnForgotPassword = (Button) findViewById(R.id.btnLoginForgotPassword);

        //Login Default
        medtUsername.setText("nghia");
        medtPassword.setText("123");
        
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logIn() != -1) {
                    if (idRole == 0) {
                        Intent intent = new Intent(LogIn.this, HomePageAdmin.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idUser", logIn());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(LogIn.this, HomePageLecture.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idUser", logIn());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(LogIn.this, "Username hoặc Password không đúng", Toast.LENGTH_SHORT).show();
                    medtUsername.selectAll();
                    medtUsername.requestFocus();
                }
            }
        });

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private int logIn() {
        Cursor cursor = db.checkLogin(medtUsername.getText().toString(), medtPassword.getText().toString());
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            idRole = cursor.getInt(4);
            return cursor.getInt(0);
        }
        else {return -1;}
    }
}