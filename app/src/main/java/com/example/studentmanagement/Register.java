package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.Lecture;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;

public class Register extends AppCompatActivity {
    EditText medtMaGV, medtRegisterUserName, medtRegisterPasswordConfirm, medtRegisterPassword;
    Button mbtnRegister, mbtnSwitchLogin, mbtnRegisterForgotPassword;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        medtMaGV = (EditText) findViewById(R.id.edtMaGV);
        medtRegisterUserName = (EditText) findViewById(R.id.edtRegisterUserName);
        medtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        medtRegisterPasswordConfirm = (EditText) findViewById(R.id.edtRegisterPasswordConfirm);

        mbtnRegister = (Button) findViewById(R.id.btnRegister);
        mbtnSwitchLogin = (Button) findViewById(R.id.btnSwitchLogin);
        mbtnRegisterForgotPassword = (Button) findViewById(R.id.btnRegisterForgotPassword);

        db = new QLSVDatabase(this);

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(medtMaGV) || isEmpty(medtRegisterUserName) || isEmpty(medtRegisterPassword) || isEmpty(medtRegisterPasswordConfirm)) {
                    Toast.makeText(Register.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (equalToLectureId(medtMaGV)) {
                        if (medtRegisterPassword.getText().toString().equals(medtRegisterPasswordConfirm.getText().toString())) {
                            User user = new User(1, medtRegisterUserName.getText().toString(), medtRegisterPassword.getText().toString(), 1, Integer.parseInt(medtMaGV.getText().toString().trim()));
                            db.add_user(user);
                            Toast.makeText(Register.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Register.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Register.this, "Mã giảng viên không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mbtnSwitchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LogIn.class);
                startActivity(intent);
            }
        });
    }

    public boolean equalToLectureId(EditText editText) {
        Cursor c = db.getLectureById(Integer.parseInt(editText.getText().toString().trim()));
        c.moveToFirst();
        if(c.getCount() > 0)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmpty(EditText editText) {
        if (editText.getText().toString().equals("")) {
            return true;
        }
        return false;
    }
}