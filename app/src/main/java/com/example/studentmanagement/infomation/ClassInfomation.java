package com.example.studentmanagement.infomation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.pojo.Student;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

import java.util.ArrayList;

public class ClassInfomation extends AppCompatActivity {

    TextView tvId, tvName, tvSubject, tvLecture, tvQuantity, tvYear, tvStarted;

    ListView lvStudents;
    Button btnAddStudent;

    QLSVDatabase db;

    ArrayList<Student> students = new ArrayList<>();

    ArrayAdapter<Student> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_infomation);

        db = new QLSVDatabase(this);

        tvId = (TextView)findViewById(R.id.tvClassInfoID);
        tvName = (TextView)findViewById(R.id.tvClassInfoName);
        tvSubject = (TextView)findViewById(R.id.tvClassInfoSubject);
        tvLecture = (TextView)findViewById(R.id.tvClassInfoLecture);
        tvQuantity = (TextView)findViewById(R.id.tvClassInfoQuantity);
        tvYear = (TextView)findViewById(R.id.tvClassInfoYear);
        tvStarted = (TextView)findViewById(R.id.tvClassInfoStarted);
        btnAddStudent = (Button)findViewById(R.id.btnClassAddStudent);
        lvStudents = (ListView) findViewById(R.id.lvClassInfoStudent);

        Bundle bundle = getIntent().getExtras();
        Class _class = (Class) bundle.getSerializable("class");

        tvId.setText("ID: " + Integer.toString(_class.getId()));
        tvName.setText("Lớp: " + _class.getName());
        Cursor c = db.getListSubjectById(_class.getSubject_id());
        c.moveToFirst();
        tvSubject.setText("Môn học: " + c.getString(1));
        c = db.getLectureById(_class.getLecture());
        c.moveToFirst();
        tvLecture.setText("Giảng viên: " + c.getString(1) + " " + c.getString(2));
        tvQuantity.setText("Số lượng: " + Integer.toString(_class.getQuantity()));
        tvYear.setText("Khoá: " + _class.getYear());
        tvStarted.setText("Thời gian bắt đầu: " + _class.getStarted());

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, students);
        lvStudents.setAdapter(adapter);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassInfomation.this);
                builder.setTitle("Thêm sinh viên vào lớp học");
                EditText input = new EditText(ClassInfomation.this);
                TextView view = new TextView(ClassInfomation.this);
                LinearLayout layout = new LinearLayout(ClassInfomation.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(input);
                layout.addView(view);
                builder.setView(layout);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create();
                builder.show();
            }
        });
    }



    private void initStudents(Cursor cursor){

    }
}