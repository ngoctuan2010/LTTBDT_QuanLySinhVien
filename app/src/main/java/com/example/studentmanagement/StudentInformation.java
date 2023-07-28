package com.example.studentmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.service.QLSVDatabase;

public class StudentInformation extends AppCompatActivity {

    TextView txtNameClass, txtQuantity, txtSchoolyear, txtIdStudent, txtStudentName, txtBirth, txtScore10, txtScore4;
    EditText edtMidScore, edtEndScore;
    Button btnSave;
    QLSVDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        txtNameClass = findViewById(R.id.txtClassName);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtSchoolyear = findViewById(R.id.txtSchoolyear);
        txtIdStudent = findViewById(R.id.txtIdStudent);
        txtStudentName = findViewById(R.id.txtStudentName);
        txtBirth = findViewById(R.id.txtBirth);
        txtScore10 = findViewById(R.id.txtScore10);
        txtScore4 = findViewById(R.id.txtScore4);
        edtMidScore = findViewById(R.id.edtMidScore);
        edtEndScore = findViewById(R.id.edtEndScore);
        btnSave = findViewById(R.id.btnSave);
        database = new QLSVDatabase(StudentInformation.this);
    }
}
