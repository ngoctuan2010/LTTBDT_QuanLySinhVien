package com.example.studentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Student;
import com.example.service.QLSVDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StudentUpdate extends AppCompatActivity {
    EditText edtName, edtBirth, edtAddress, edtPhone, edtDepartment, edtSchoolyear;
    RadioGroup rdgSex;
    RadioButton rbMale, rbFemale;
    Button btnUpdate;
    QLSVDatabase database;
    Student student;
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_update);
        edtName = findViewById(R.id.edtName);
        edtBirth = findViewById(R.id.edtBirth);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtSchoolyear = findViewById(R.id.edtShoolyear);
        rdgSex = findViewById(R.id.rdgSex);
        rbFemale = findViewById(R.id.rdbFemale);
        rbMale = findViewById(R.id.rdbMale);
        btnUpdate = findViewById(R.id.btnUpdateStudent);
        database = new QLSVDatabase(StudentUpdate.this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int studentId = bundle.getInt("student_id", -1);
        student = database.getStudentById(studentId);
        doDuLieuLenEditText();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()) {
                    Toast.makeText(StudentUpdate.this, "Vui lòng nhập đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkPhone()) {
                    Toast.makeText(StudentUpdate.this, "Vui lòng nhập đúng thông tin số điện thoại!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (!checkBirth(edtBirth.getText().toString())) {
                        Toast.makeText(StudentUpdate.this, "Vui lòng nhập ngày sinh đúng định dạng!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String nameStudentUpdate = edtName.getText().toString();
                String birthStudentUpdate = edtBirth.getText().toString();
                String addressStudentUpdate = edtAddress.getText().toString();
                String phoneStudentUpdate = edtPhone.getText().toString();
                String departmentStudentUpdate = edtDepartment.getText().toString();
                String schoolyearStudentUpdate = edtSchoolyear.getText().toString();
                boolean gt = false;
                if (rbMale.isChecked())
                    gt = true;
                Student studentUpdate = new Student(nameStudentUpdate, gt, birthStudentUpdate, addressStudentUpdate, phoneStudentUpdate, departmentStudentUpdate, schoolyearStudentUpdate);
                studentUpdate.setId(studentId);
                if (database.update_student(studentUpdate) == -1)
                    Toast.makeText(StudentUpdate.this, "Sửa thất bại!!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(StudentUpdate.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void doDuLieuLenEditText() {
        edtName.setText(student.getName());
        edtBirth.setText(student.getBirth());
        edtAddress.setText(student.getAddress());
        edtPhone.setText(student.getPhone());
        edtDepartment.setText(student.getDepartment());
        edtSchoolyear.setText(student.getSchool_year());
        if (!student.isGender())
            rbFemale.setChecked(true);
    }

    private boolean itemCheck(EditText editText) {
        if (editText.getText().toString().isEmpty())
            return false;
        return true;
    }

    private boolean checkInput() {
        if (itemCheck(edtBirth) && itemCheck(edtName) && itemCheck(edtPhone) && itemCheck(edtDepartment) && itemCheck(edtSchoolyear))
            return true;
        return false;
    }

    private boolean checkPhone() {
        if (edtPhone.getText().toString().length() == 11 || edtPhone.getText().toString().length() == 10)
            return true;
        return false;
    }

    private boolean checkBirth(String string) throws ParseException {
        try {
            spdf.parse(string);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
