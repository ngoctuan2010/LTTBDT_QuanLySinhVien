package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        medtUsername = (EditText) findViewById(R.id.edtMaGV);
        medtPassword = (EditText) findViewById(R.id.edtPassword);

        mckRemember = (CheckBox) findViewById(R.id.ckRemember);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        mbtnRegister = (Button) findViewById(R.id.btnSwitchRegister);
        mbtnForgotPassword = (Button) findViewById(R.id.btnLoginForgotPassword);
        
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medtUsername.getText().toString().equals("admin") && medtPassword.getText().toString().equals("123")) {
                    Toast.makeText(LogIn.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this, HomePage.class);
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
}