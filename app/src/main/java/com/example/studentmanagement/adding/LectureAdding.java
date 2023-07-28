package com.example.studentmanagement.adding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pojo.Lecture;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

public class LectureAdding extends AppCompatActivity {
    EditText edtName, edtBirth, edtAddress, edtPhone, edtDepartment;
    RadioGroup rdgGender;
    RadioButton rdMale, rdFermale;
    Button btnAdd, btnEdit;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_adding);

        edtName = (EditText) findViewById(R.id.edtLectureAddingName);
        edtBirth = (EditText) findViewById(R.id.edtLectureAddingBirth);
        edtAddress = (EditText) findViewById(R.id.edtLectureAddingAddress);
        edtPhone = (EditText) findViewById(R.id.edtLectureAddingPhone);
        edtDepartment = (EditText) findViewById(R.id.edtLectureAddingDepartment);

        btnAdd = (Button) findViewById(R.id.btnLectureAdd);
        btnEdit = (Button) findViewById(R.id.btnLectureEdit);

        rdgGender = (RadioGroup) findViewById(R.id.rdgLectureAdding);
        rdMale = (RadioButton) findViewById(R.id.rdLectureAddingMale);
        rdFermale = (RadioButton) findViewById(R.id.rdLectureAddingFermale);

        db = new QLSVDatabase(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            btnAdd.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);

            Lecture lecture = (Lecture) bundle.getSerializable("Lecture");
            edtName.setText(lecture.getName());
            if (lecture.isGender()) {
                rdFermale.setChecked(true);
                rdMale.setChecked(false);
            }
            else {
                rdFermale.setChecked(false);
                rdMale.setChecked(true);
            }
            edtBirth.setText(lecture.getBirth());
            edtAddress.setText(lecture.getAddress());
            edtPhone.setText(lecture.getPhone());
            edtDepartment.setText(lecture.getDepartment());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateInput()) {
                        String lectureName = edtName.getText().toString();
                        String lectureBirth = edtBirth.getText().toString();
                        String lectureAddress = edtAddress.getText().toString();
                        String lecturePhone = edtPhone.getText().toString();
                        String lectureDepartment = edtDepartment.getText().toString();

                        boolean isGender = true;
                        if (rdMale.isChecked()) {isGender = false;}

                        lecture.setName(lectureName);
                        lecture.setGender(isGender);
                        lecture.setBirth(lectureBirth);
                        lecture.setAddress(lectureAddress);
                        lecture.setPhone(lecturePhone);
                        lecture.setDepartment(lectureDepartment);

                        if(db.update_lecture(lecture) > 0){
                            Toast.makeText(LectureAdding.this, "Bạn đã sửa giảng viên thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LectureAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();
                    }
                    else {
                        requiredInput();
                    }
                }
            });
        }
        else {
            btnEdit.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateInput()) {
                        String lectureName = edtName.getText().toString();
                        String lectureBirth = edtBirth.getText().toString();
                        String lectureAddress = edtAddress.getText().toString();
                        String lecturePhone = edtPhone.getText().toString();
                        String lectureDepartment = edtDepartment.getText().toString();

                        boolean isGender = true;
                        if (rdMale.isChecked()) {isGender = false;}

                        Lecture lecture = new Lecture(1, lectureName, isGender, lectureBirth, lectureAddress, lecturePhone, lectureDepartment);
                        if(db.add_lecture(lecture) > 0){
                            Toast.makeText(LectureAdding.this, "Bạn đã thêm giảng viên thành công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(LectureAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        requiredInput();
                    }
                }
            });
        }
    }

    private boolean validateInput(){
        String lectureName = edtName.getText().toString();
        String lectureBirth = edtBirth.getText().toString();
        String lectureAddress = edtBirth.getText().toString();
        String lecturePhone = edtPhone.getText().toString();
        String lectureDepartment = edtDepartment.getText().toString();

        boolean isCheckGender = rdMale.isChecked() || rdFermale.isChecked();

        return (!lectureName.isEmpty() && !lectureBirth.isEmpty() && !lectureAddress.isEmpty() && !lecturePhone.isEmpty() && !lectureDepartment.isEmpty() && isCheckGender);
    }

    private void requiredInput(){
        String lectureName = edtName.getText().toString();
        String lectureBirth = edtBirth.getText().toString();
        String lectureAddress = edtBirth.getText().toString();
        String lecturePhone = edtPhone.getText().toString();
        String lectureDepartment = edtDepartment.getText().toString();

        boolean isCheckGender = false;
        if (rdMale.isChecked() || rdFermale.isChecked()) {isCheckGender = true;}

        if (lectureName.isEmpty()) {
            edtName.setError(getResources().getString(R.string.inputRequired));
        }
        if (isCheckGender == false) {
            rdFermale.setError(getResources().getString(R.string.inputRequired));
            rdMale.setError(getResources().getString(R.string.inputRequired));
        }
        if (lectureBirth.isEmpty()) {
            edtBirth.setError(getResources().getString(R.string.inputRequired));
        }
        if (lectureAddress.isEmpty()) {
            edtAddress.setError(getResources().getString(R.string.inputRequired));
        }
        if (lecturePhone.isEmpty()) {
            edtPhone.setError(getResources().getString(R.string.inputRequired));
        }
        if (lectureDepartment.isEmpty()) {
            edtDepartment.setError(getResources().getString(R.string.inputRequired));
        }
    }
}