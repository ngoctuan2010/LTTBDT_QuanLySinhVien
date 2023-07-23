package com.example.studentmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Subject;
import com.example.service.QLSVDatabase;

public class SubjectAdding extends AppCompatActivity {

    EditText edtSubjectName, edtSubjectCredit;
    Button btnSubjectAdd, btnSubjectUpdate;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subject_adding);
        db = new QLSVDatabase(this);
        edtSubjectCredit = (EditText) findViewById(R.id.edtClassAddingQuantity);
        edtSubjectName = (EditText) findViewById(R.id.edtClassAddingStarted);
        btnSubjectAdd = (Button) findViewById(R.id.btnClassAdd);
        btnSubjectUpdate = (Button) findViewById(R.id.btnSubjectEdit);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            btnSubjectAdd.setVisibility(View.GONE);
            btnSubjectUpdate.setVisibility(View.VISIBLE);

            Subject subject = (Subject) bundle.getSerializable("Subject");
            edtSubjectName.setText(subject.getName());
            edtSubjectCredit.setText(Integer.toString(subject.getCredit()));

            btnSubjectUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edtSubjectName.getText().toString();
                    int credit = Integer.parseInt(edtSubjectCredit.getText().toString());

                    subject.setName(name);
                    subject.setCredit(credit);

                    if(db.update_subject(subject) > 0){
                        Toast.makeText(SubjectAdding.this, "Bạn đã sửa môn học thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SubjectAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();
                }
            });

        }else{
            btnSubjectUpdate.setVisibility(View.GONE);
            btnSubjectAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edtSubjectName.getText().toString();
                    int credit = Integer.parseInt(edtSubjectCredit.getText().toString());

                    Subject s = new Subject(-1, name, credit);
                    if(db.add_subject(s) > 0){
                        Toast.makeText(SubjectAdding.this, "Bạn đã thêm môn học thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SubjectAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
