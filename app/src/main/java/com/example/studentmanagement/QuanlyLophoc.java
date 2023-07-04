package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class QuanlyLophoc extends AppCompatActivity {

    Button btnAdd, btnFilter;
    ListView lvClass;
    EditText edtID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_lophoc);

        btnAdd = (Button) findViewById(R.id.btnAddClass);
        btnFilter = (Button) findViewById(R.id.btnFilterClass);
        lvClass = (ListView) findViewById(R.id.lvListClass);
        edtID = (EditText) findViewById(R.id.edtSearchClass);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}