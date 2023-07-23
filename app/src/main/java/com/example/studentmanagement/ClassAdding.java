package com.example.studentmanagement;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Class;
import com.example.pojo.Subject;
import com.example.service.DateFormatter;
import com.example.service.QLSVDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ClassAdding extends AppCompatActivity {

    EditText edtName, edtLecture, edtStarted, edtQuantity, edtYear;

    AutoCompleteTextView actvSubject;
    Button btnAdd, btnEdit;
    ImageButton imgCalendar;
    QLSVDatabase db;

    int subject_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_class_adding);

        db = new QLSVDatabase(ClassAdding.this);

        edtName = (EditText) findViewById(R.id.edtClassAddingName);
        actvSubject = (AutoCompleteTextView) findViewById(R.id.edtClassAddingSubject);
        edtLecture = (EditText) findViewById(R.id.edtClassAddingLecture);
        edtStarted = (EditText) findViewById(R.id.edtClassAddingStarted);
        edtQuantity = (EditText) findViewById(R.id.edtClassAddingQuantity);
        edtYear = (EditText) findViewById(R.id.edtClassAddingYear);

        btnAdd = (Button) findViewById(R.id.btnClassAdd);
        btnEdit = (Button) findViewById(R.id.btnClassEdit);

        imgCalendar = (ImageButton) findViewById(R.id.imgCalendar);

        ArrayList<Subject> subjects = initSubject();
        ArrayAdapter<Subject> sAdapter = new ArrayAdapter(ClassAdding.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, subjects);
        actvSubject.setAdapter(sAdapter);

        actvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               subject_id = ((Subject) parent.getItemAtPosition(position)).getId();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            btnAdd.setVisibility(View.GONE);
            Class _class = (Class) bundle.getSerializable("class");
            edtName.setText(_class.getName());

            Cursor c = db.getListSubjectById(_class.getSubject_id());
            c.moveToFirst();
            String name = c.getString(1);
            actvSubject.setText(name);

            edtLecture.setText(Integer.toString(_class.getLecture()));
            edtYear.setText(_class.getYear());
            edtStarted.setText(_class.getStarted());
            edtQuantity.setText(Integer.toString(_class.getQuantity()));
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _class.setName(edtName.getText().toString());
                    _class.setSubject_id(subject_id);
                    _class.setLecture(Integer.parseInt(edtLecture.getText().toString()));
                    _class.setStarted(edtStarted.getText().toString());
                    _class.setQuantity(Integer.parseInt(edtQuantity.getText().toString()));
                    _class.setYear(edtYear.getText().toString());

                    if(db.update_class(_class) > 0){
                        Toast.makeText(ClassAdding.this, "Bạn đã cập nhật lớp học thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ClassAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            btnEdit.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edtName.getText().toString();
                    int lecture = Integer.parseInt(edtLecture.getText().toString());
                    String started = edtStarted.getText().toString();
                    int quantity = Integer.parseInt(edtQuantity.getText().toString());
                    String year = edtYear.getText().toString();
                    Class _class = new Class(-1, name, subject_id, lecture, quantity, year, started);

                    if(db.add_class(_class) > 0){
                        Toast.makeText(ClassAdding.this, "Bạn đã thêm lớp học thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ClassAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassAdding.this);
                builder.setTitle("Chọn ngày");
                CalendarView calendarView = new CalendarView(ClassAdding.this);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String y = Integer.toString(year);
                        String m = Integer.toString(month + 1);
                        String d = Integer.toString(dayOfMonth);
                        edtStarted.setText(d+"/"+m+"/"+y);
                        }
                });

                builder.setView(calendarView);
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
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

    private ArrayList<Subject> initSubject(){

        ArrayList<Subject> list = new ArrayList<Subject>();
        Cursor c = db.get_list_subject();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int credit = c.getInt(2);
            Subject s = new Subject(id, name, credit);
            list.add(s);
        }

        return list;
    }
}
