package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Lecture;
import com.example.service.QLSVDatabase;

public class ProfileLecture extends AppCompatActivity {
    EditText medtName, medtBirth,medtAddress, medtPhone, medtDepartment;
    TextView tvId;
    RadioGroup mrdgGender;
    RadioButton mrdMale, mrdFemale;
    Button mbtnSave;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_lecture);

        tvId = (TextView) findViewById(R.id.tvId);

        medtName = (EditText) findViewById(R.id.edtName);
        medtBirth = (EditText) findViewById(R.id.edtBirth);
        medtAddress = (EditText) findViewById(R.id.edtAddress);
        medtPhone = (EditText) findViewById(R.id.edtPhone);
        medtDepartment = (EditText) findViewById(R.id.edtDepartment);

        mrdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        mrdMale = (RadioButton) findViewById(R.id.rdMale);
        mrdFemale = (RadioButton) findViewById(R.id.rdFemale);

        mbtnSave = (Button) findViewById(R.id.btnSave);

        db = new QLSVDatabase(this);
        Bundle bundle = getIntent().getExtras();
        int idLecture = bundle.getInt("LectureId");
        Cursor cursor = db.getLectureById(idLecture);
        cursor.moveToFirst();

        //setText
        tvId.setText(cursor.getString(0));
        medtName.setText(cursor.getString(1));
        if (cursor.getString(2).equals("0")) {
            mrdgGender.check(R.id.rdMale);
        }
        else {
            mrdgGender.check(R.id.rdFemale);
        }
        medtBirth.setText(cursor.getString(3));
        medtAddress.setText(cursor.getString(4));
        medtPhone.setText(cursor.getString(5));
        medtDepartment.setText(cursor.getString(6));

        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medtName.getText().toString().equals("")) {
                    Toast.makeText(ProfileLecture.this, "Bạn phải nhập tên", Toast.LENGTH_SHORT).show();
                    medtName.requestFocus();
                    return;
                }
                if (medtBirth.getText().toString().equals("")) {
                    Toast.makeText(ProfileLecture.this, "Bạn phải nhập ngày sinh", Toast.LENGTH_SHORT).show();
                    medtBirth.requestFocus();
                    return;
                }
                if (medtAddress.getText().toString().equals("")) {
                    Toast.makeText(ProfileLecture.this, "Bạn phải nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    medtAddress.requestFocus();
                    return;
                }if (medtPhone.getText().toString().equals("")) {
                    Toast.makeText(ProfileLecture.this, "Bạn phải nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    medtPhone.requestFocus();
                    return;
                }
                if (medtDepartment.getText().toString().equals("")) {
                    Toast.makeText(ProfileLecture.this, "Bạn phải nhập khoa", Toast.LENGTH_SHORT).show();
                    medtDepartment.requestFocus();
                    return;
                }

                //Update Lecture
                String name = medtName.getText().toString();
                Boolean gender = true;
                if (mrdMale.isChecked()) {
                    gender =false;
                }
                String birth = medtBirth.getText().toString();
                String address = medtAddress.getText().toString();
                String phone = medtPhone.getText().toString();
                String department = medtDepartment.getText().toString();

                Lecture lecture = new Lecture(idLecture, name, gender, birth, address, phone, department);
                if (db.update_lecture(lecture) > 0) {
                    Toast.makeText(ProfileLecture.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProfileLecture.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.setGroupVisible(R.id.grMenuDefault, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent intent = new Intent(ProfileLecture.this, ProfileLecture.class);
            startActivity(intent);
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
            Intent intent = new Intent(ProfileLecture.this, LogIn.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}