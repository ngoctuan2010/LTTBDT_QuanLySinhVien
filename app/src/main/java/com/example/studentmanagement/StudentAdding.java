package com.example.studentmanagement;

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


public class StudentAdding extends AppCompatActivity {
    EditText edtName, edtBirth, edtPhone, edtDepartment, edtSchoolyear, edtAddress;
    RadioGroup rdgSex;
    RadioButton rdFemale, rdMale;
    Button btnAdd;
    QLSVDatabase db;
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_adding);
        edtBirth = findViewById(R.id.edtBirth);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtPhone = findViewById(R.id.edtPhone);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtSchoolyear = findViewById(R.id.edtShoolyear);
        edtName = findViewById(R.id.edtName);
        rdgSex = findViewById(R.id.rdgSex);
        rdFemale = findViewById(R.id.rdbFemale);
        rdMale = findViewById(R.id.rdbMale);
        btnAdd = findViewById(R.id.btnAddingStudent);
        edtAddress = findViewById(R.id.edtAddress);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new QLSVDatabase(StudentAdding.this);
                if (!checkInput()) {
                    Toast.makeText(StudentAdding.this, "Vui lòng nhập đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkPhone()) {
                    Toast.makeText(StudentAdding.this, "Vui lòng nhập đúng thông tin số điện thoại!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (!checkBirth(edtBirth.getText().toString())) {
                        Toast.makeText(StudentAdding.this, "Vui lòng nhập ngày sinh đúng định dạng!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                boolean gioiTinh = false;
                if (rdgSex.getCheckedRadioButtonId() == rdMale.getId())
                    gioiTinh = true;
                Student student = new Student(edtName.getText().toString(), gioiTinh, edtBirth.getText().toString(), edtAddress.getText().toString(), edtPhone.getText().toString(), edtDepartment.getText().toString(), edtSchoolyear.getText().toString());
                long result = db.add_student(student);
                if (result == -1)
                    Toast.makeText(StudentAdding.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(StudentAdding.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
            }
        });
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