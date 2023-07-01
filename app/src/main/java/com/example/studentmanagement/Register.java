package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText medtMaGV, medtRegisterUserName, medtRegisterPasswordConfirm, medtRegisterPassword;
    Button mbtnRegister, mbtnSwitchLogin, mbtnRegisterForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        medtMaGV = (EditText) findViewById(R.id.edtMaGV);
        medtRegisterUserName = (EditText) findViewById(R.id.edtRegisterUserName);
        medtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        medtRegisterPasswordConfirm = (EditText) findViewById(R.id.edtRegisterPasswordConfirm);

        mbtnRegister = (Button) findViewById(R.id.btnRegister);
        mbtnSwitchLogin = (Button) findViewById(R.id.btnSwitchLogin);
        mbtnRegisterForgotPassword = (Button) findViewById(R.id.btnRegisterForgotPassword);

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(medtMaGV.getText().toString())) {
                    Toast.makeText(Register.this, "Bạn phải nhập mã giảng viên", Toast.LENGTH_SHORT).show();
                    medtMaGV.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(medtRegisterUserName.getText().toString())) {
                    Toast.makeText(Register.this, "Bạn phải nhập username", Toast.LENGTH_SHORT).show();
                    medtRegisterUserName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(medtRegisterPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Bạn phải nhập password", Toast.LENGTH_SHORT).show();
                    medtRegisterPassword.requestFocus();
                    return;
                }
                if (!medtRegisterPasswordConfirm.getText().toString().equals(medtRegisterPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Mật khẩu xác nhận không khớp với mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    medtRegisterPasswordConfirm.selectAll();
                    medtRegisterPasswordConfirm.requestFocus();
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
}