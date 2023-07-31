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
import com.example.pojo.Subject;
import com.example.pojo.User;
import com.example.service.HashSHA1;
import com.example.service.QLSVDatabase;
import com.example.service.SharePreferenceServeice;

import java.text.ParseException;


public class LogIn extends AppCompatActivity {
    EditText medtUsername, medtPassword;
    CheckBox mckRemember;
    Button mbtnLogin, mbtnRegister, mbtnForgotPassword;

    SharePreferenceServeice sharePreferenceServeice;

    QLSVDatabase db;
    int idRole, idLecture, idAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        db = new QLSVDatabase(this);

        sharePreferenceServeice = new SharePreferenceServeice(this, "User");
//        db.add_subject(new Subject(1, "Lập trình Java", 4));
//        db.add_class(new Class(1, "IT02", 1, 1, 45, "2022-2023", "03/07/2022"));
//        db.add_lecture(new Lecture(1, "Trong Nghia", false, "03/07/2002", "TP.HCM", "0971934970", "CNTT"));
//        db.add_lecture(new Lecture(999, "Ngoc Tuan"));
//        db.add_user(new User(1, "admin", "123", 0, 1));
//        db.add_user(new User(1, "nghia", "123", 0, 1));
//        db.add_user(new User(1, "admin", "123", 1, 1));
//        db.add_user(new User(1, "admin123", HashSHA1.SHA1("123"), 0, 1));

        medtUsername = (EditText) findViewById(R.id.edtMaGV);
        medtPassword = (EditText) findViewById(R.id.edtPassword);

        mckRemember = (CheckBox) findViewById(R.id.ckRemember);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        mbtnRegister = (Button) findViewById(R.id.btnSwitchRegister);
        mbtnForgotPassword = (Button) findViewById(R.id.btnLoginForgotPassword);

        //Login Default
        medtUsername.setText("admin123");
        medtPassword.setText("123");

        if(checkRemember())
        {
            idRole = Integer.parseInt(sharePreferenceServeice.getString("user_role"));
            Intent intent;
            if(idRole == 0) {
                intent = new Intent(LogIn.this, HomePageAdmin.class);
            }
            else {
                intent = new Intent(LogIn.this, HomePageLecture.class);
            }
            startActivity(intent);
            finish();
        }

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logIn()) {


                    if(mckRemember.isChecked())
                        sharePreferenceServeice.putString("remember", "Check");

                    sharePreferenceServeice.putString("current_user", Integer.toString(idLecture));
                    sharePreferenceServeice.putString("user_role", Integer.toString(idRole));
                    sharePreferenceServeice.putString("user_id", Integer.toString(idAccount));
                    Intent intent;
                    if(idRole == 0) {
                        intent = new Intent(LogIn.this, HomePageAdmin.class);
                    }
                    else {
                        intent = new Intent(LogIn.this, HomePageLecture.class);
                    }
                    startActivity(intent);
                    finish();
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

    private boolean logIn() {
        Cursor cursor = db.checkLogin(medtUsername.getText().toString().trim(), HashSHA1.SHA1(medtPassword.getText().toString().trim()));
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            idAccount = cursor.getInt(0);
            idLecture = cursor.getInt(3);
            idRole = cursor.getInt(4);
            return true;
        }
        return false;
    }

    private boolean checkRemember(){
        String user_id = sharePreferenceServeice.getString("remember");
        if(user_id != null && !user_id.isEmpty())
            return true;
        return false;
    }
}