package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class QuanlyMonhoc extends AppCompatActivity {

    Button btnFilter, btnAdd;
    EditText edtSearch;
    ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_monhoc);

        btnAdd = (Button) findViewById(R.id.btnSubjectAdding);
        lvList = (ListView) findViewById(R.id.lvSubjectList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlyMonhoc.this, SubjectAdding.class);
                startActivity(intent);
            }
        });

    }
}