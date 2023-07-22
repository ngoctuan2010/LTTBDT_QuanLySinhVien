package com.example.studentmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Student;
import com.example.service.QLSVDatabase;
import com.example.service.StudentArrayAdapter;

import java.text.ParseException;
import java.util.ArrayList;

public class QuanlySinhvien extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText medtSearch;
    ImageButton mbtnSearch;
    Spinner mspnProperties;
    ListView mlvSV;
    Button mbtnAdd;
    String[] itemsSpinner = {"Mã", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "Khoa", "Niên khóa"};
    ArrayAdapter<String> adapter;
    ArrayList<Student> arrayListStudent;
    StudentArrayAdapter studentArrayAdapter;
    QLSVDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_sinhvien);
        medtSearch = findViewById(R.id.edtSearchStudent);
        mbtnSearch = findViewById(R.id.btnSearch);
        mspnProperties = findViewById(R.id.spnProperties);
        mlvSV = findViewById(R.id.lvSinhVien);
        mbtnAdd = findViewById(R.id.btnAddStudent);
        database = new QLSVDatabase(QuanlySinhvien.this);
        adapter = new ArrayAdapter<String>(QuanlySinhvien.this, android.R.layout.simple_spinner_dropdown_item, itemsSpinner);
        mspnProperties.setAdapter(adapter);
        mspnProperties.setOnItemSelectedListener(QuanlySinhvien.this);
        arrayListStudent = new ArrayList<>();
        studentArrayAdapter = new StudentArrayAdapter(QuanlySinhvien.this, R.layout.custom_layout_listview_student, arrayListStudent);
        mlvSV.setAdapter(studentArrayAdapter);
        studentArrayAdapter.notifyDataSetChanged();
        mbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanlySinhvien.this, StudentAdding.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
