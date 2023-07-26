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

import com.example.pojo.Lecture;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;

public class LogIn extends AppCompatActivity {
    EditText medtUsername, medtPassword;
    CheckBox mckRemember;
    Button mbtnLogin, mbtnRegister, mbtnForgotPassword;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        db = new QLSVDatabase(this);
        db.add_lecture(new Lecture(2051052089, "Trong", "Nghia", false, "03/07/2002", "TP.HCM", "0636236224", "CNTT"));
        db.add_user(new User(1, "admin", "123", 1, 2051052089));

        medtUsername = (EditText) findViewById(R.id.edtMaGV);
        medtPassword = (EditText) findViewById(R.id.edtPassword);

        mckRemember = (CheckBox) findViewById(R.id.ckRemember);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        mbtnRegister = (Button) findViewById(R.id.btnSwitchRegister);
        mbtnForgotPassword = (Button) findViewById(R.id.btnLoginForgotPassword);
        
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logIn() != -1) {
                    Intent intent = new Intent(LogIn.this, HomePageAdmin.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idUser", logIn());
                    intent.putExtras(bundle);
                    startActivity(intent);
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
        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        }
        else {return -1;}
    }
}