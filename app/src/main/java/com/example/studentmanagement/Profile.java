package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

public class Profile extends AppCompatActivity {
    EditText medtName, medtBirth,medtAddress, medtPhone, medtDepartment;
    RadioGroup mrdgGender;
    RadioButton mrdMale, mrdFemale;
    Button mbtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        medtName = (EditText) findViewById(R.id.edtName);
        medtBirth = (EditText) findViewById(R.id.edtBirth);
        medtAddress = (EditText) findViewById(R.id.edtAddress);
        medtPhone = (EditText) findViewById(R.id.edtPhone);
        medtDepartment = (EditText) findViewById(R.id.edtDepartment);

        mrdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        mrdMale = (RadioButton) findViewById(R.id.rdMale);
        mrdFemale = (RadioButton) findViewById(R.id.rdFemale);

        mbtnSave = (Button) findViewById(R.id.btnSave);

        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medtName.getText().toString().equals("")) {
                    Toast.makeText(Profile.this, "Bạn phải nhập tên", Toast.LENGTH_SHORT).show();
                    medtName.requestFocus();
                    return;
                }
                if (medtBirth.getText().toString().equals("")) {
                    Toast.makeText(Profile.this, "Bạn phải nhập ngày sinh", Toast.LENGTH_SHORT).show();
                    medtBirth.requestFocus();
                    return;
                }
                if (medtAddress.getText().toString().equals("")) {
                    Toast.makeText(Profile.this, "Bạn phải nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    medtAddress.requestFocus();
                    return;
                }if (medtPhone.getText().toString().equals("")) {
                    Toast.makeText(Profile.this, "Bạn phải nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    medtPhone.requestFocus();
                    return;
                }
                if (medtDepartment.getText().toString().equals("")) {
                    Toast.makeText(Profile.this, "Bạn phải nhập khoa", Toast.LENGTH_SHORT).show();
                    medtDepartment.requestFocus();
                    return;
                }
                String stringGetText = "";
                stringGetText = medtName.getText().toString();
                medtName.setText(stringGetText);

                stringGetText = medtBirth.getText().toString();
                medtBirth.setText(stringGetText);

                stringGetText = medtAddress.getText().toString();
                medtAddress.setText(stringGetText);

                stringGetText = medtPhone.getText().toString();
                medtPhone.setText(stringGetText);

                stringGetText = medtDepartment.getText().toString();
                medtDepartment.setText(stringGetText);

                Toast.makeText(Profile.this, "Lưu thành công", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}