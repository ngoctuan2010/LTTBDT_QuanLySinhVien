package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HomePageLecture extends AppCompatActivity {
    Spinner spnSemester;
    String[] listSemester = {"Học kì I năm 2022-2023", "Học kì II năm 2022-2023", "Học kì III năm 2022-2023"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_lecture);

        spnSemester = (Spinner) findViewById(R.id.spnSemester);

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSemester);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSemester.setAdapter(adapter);
    }
}