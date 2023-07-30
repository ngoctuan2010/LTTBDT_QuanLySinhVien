package com.example.studentmanagement.information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.pojo.Lecture;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

public class LectureInformation extends AppCompatActivity {
    EditText edtName, edtBirth, edtAddress, edtPhone, edtDepartment;
    RadioGroup rdgGender;
    RadioButton rdMale, rdFermale;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_information);

        edtName = (EditText) findViewById(R.id.edtLectureInfoName);
        edtBirth = (EditText) findViewById(R.id.edtLectureInfoBirth);
        edtAddress = (EditText) findViewById(R.id.edtLectureInfoAddress);
        edtPhone = (EditText) findViewById(R.id.edtLectureInfoPhone);
        edtDepartment = (EditText) findViewById(R.id.edtLectureInfoDepartment);

        rdgGender = (RadioGroup) findViewById(R.id.rdgLectureInfo);
        rdMale = (RadioButton) findViewById(R.id.rdLectureInfoMale);
        rdFermale = (RadioButton) findViewById(R.id.rdLectureInfoFermale);

        db = new QLSVDatabase(this);

        Bundle bundle = getIntent().getExtras();
        Lecture lecture = (Lecture) bundle.get("Lecture");

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
    }
}