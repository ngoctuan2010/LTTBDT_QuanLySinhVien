package com.example.studentmanagement.adding;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Class;
import com.example.pojo.Lecture;
import com.example.pojo.Subject;
import com.example.service.DateFormatter;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ClassAdding extends AppCompatActivity {
    DateFormatter df = new DateFormatter("dd/MM/yyyy");

    EditText edtName, edtStarted, edtQuantity, edtYear;

    AutoCompleteTextView actvSubject, actvLecture;
    Button btnAdd, btnEdit;

    TextView tvTitle;
    ImageButton imgCalendar;
    QLSVDatabase db;

    int subject_id = -1;
    int lecture_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_class_adding);

        db = new QLSVDatabase(ClassAdding.this);

        edtName = (EditText) findViewById(R.id.edtClassAddingName);
        actvSubject = (AutoCompleteTextView) findViewById(R.id.edtClassAddingSubject);
        actvLecture = (AutoCompleteTextView) findViewById(R.id.edtClassAddingLecture);
        edtStarted = (EditText) findViewById(R.id.edtClassAddingStarted);
        edtQuantity = (EditText) findViewById(R.id.edtClassAddingQuantity);
        edtYear = (EditText) findViewById(R.id.edtClassAddingYear);

        btnAdd = (Button) findViewById(R.id.btnClassAdd);
        btnEdit = (Button) findViewById(R.id.btnClassEdit);

        tvTitle = (TextView) findViewById(R.id.tvTitleClassDialog);

        imgCalendar = (ImageButton) findViewById(R.id.imgCalendar);

        ArrayList<Subject> subjects = initListSubject();
        ArrayAdapter<Subject> sAdapter = new ArrayAdapter(ClassAdding.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, subjects);
        actvSubject.setAdapter(sAdapter);

        ArrayList<Lecture> lectures = initListLecture();
        ArrayAdapter<Lecture> lAdapter = new ArrayAdapter<>(ClassAdding.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lectures);
        actvLecture.setAdapter(lAdapter);

        try {
            checkDateValidate("7/29/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        actvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               subject_id = ((Subject) parent.getItemAtPosition(position)).getId();
            }
        });
        actvSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                subject_id = -1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actvSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && subject_id == -1){
                    actvSubject.setError("Vui lòng chọn môn học");
                }
            }
        });
        actvLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lecture_id = ((Lecture) parent.getItemAtPosition(position)).getId();
            }
        });
        actvLecture.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lecture_id = -1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actvLecture.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && lecture_id == -1){
                    actvLecture.setError("Vui lòng chọn giảng viên");
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            btnAdd.setVisibility(View.GONE);
            tvTitle.setText("Sửa thông tin lớp học");

            Class _class = (Class) bundle.getSerializable("class");
            edtName.setText(_class.getName());

            Cursor c = db.getListSubjectById(_class.getSubject_id());
            String name = "";
            if(c.getCount() > 0)
            {
                c.moveToFirst();
                name = c.getString(1);
            }

            actvSubject.setText(name);
            subject_id = _class.getSubject_id();

            c = db.getLectureById(_class.getLecture());
            String lecture = "";
            if(c.getCount() > 0){
                c.moveToFirst();
                lecture = c.getString(1) + " [" + c.getString(0) + "]";
            }

            actvLecture.setText(lecture);
            lecture_id = _class.getLecture();

            edtYear.setText(_class.getYear());
            try {
                edtStarted.setText(DateFormatter.reformat(_class.getStarted(), "yyyy-MM-dd", "dd/MM/yyyy"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            edtQuantity.setText(Integer.toString(_class.getQuantity()));


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(validateInput()) {


                       if(subject_id == -1){
                           return;
                       }

                       if(lecture_id == -1){
                           return;
                       }

                       _class.setName(edtName.getText().toString());
                       _class.setSubject_id(subject_id);
                       _class.setLecture(lecture_id);
                       _class.setStarted(edtStarted.getText().toString());
                       _class.setQuantity(Integer.parseInt(edtQuantity.getText().toString()));
                       _class.setYear(edtYear.getText().toString());

                       if(db.getDiffClassBySubjectAndName(_class.getName(), _class.getId()).getCount() > 0){
                           Toast.makeText(ClassAdding.this, "Lớp học đã tồn tại", Toast.LENGTH_SHORT).show();
                           return;
                       }

                       try {
                           if(!checkDateValidate(_class.getStarted())){
                               Toast.makeText(ClassAdding.this, "Thời gian không phù hợp", Toast.LENGTH_SHORT).show();
                               return;
                           }
                       } catch (ParseException e) {
                           throw new RuntimeException(e);
                       }

                       try {
                           if(db.update_class(_class) > 0){
                               Toast.makeText(ClassAdding.this, "Bạn đã cập nhật lớp học thành công", Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(ClassAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                           }
                       } catch (ParseException e) {
                           throw new RuntimeException(e);
                       }
                   }else{
                       requiredInput();
                   }

                }
            });
        }else {
            btnEdit.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validateInput()){

                        if(subject_id == -1){
                            return;
                        }

                        if(lecture_id == -1){
                            return;
                        }

                        String name = edtName.getText().toString();
                        String started = edtStarted.getText().toString();
                        int quantity = Integer.parseInt(edtQuantity.getText().toString());
                        String year = edtYear.getText().toString();
                        Class _class = new Class(-1, name, subject_id, lecture_id, quantity, year, started);

                        if(db.getDiffClassBySubjectAndName(name, -1).getCount() > 0){
                            Toast.makeText(ClassAdding.this, "Lớp học đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            if(!checkDateValidate(DateFormatter.reformat(started, "dd/MM/yyyy", "yyyy-MM-dd"))){
                                Toast.makeText(ClassAdding.this, "Thời gian không phù hợp", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            try {
                                if(db.add_class(_class) > 0){
                                    Toast.makeText(ClassAdding.this, "Bạn đã thêm lớp học thành công", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ClassAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        requiredInput();
                    }
                }
            });
        }

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ClassAdding.this);
                dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String y = Integer.toString(year);
                        String m = Integer.toString(month + 1);
                        String d = Integer.toString(dayOfMonth);
                        edtStarted.setText(d+"/"+m+"/"+y);
                        edtStarted.setText(d+"/"+m+"/"+y, TextView.BufferType.NORMAL);
                    }
                });
                dialog.show();
            }
        });
    }

    private ArrayList<Lecture> initListLecture() {
        ArrayList<Lecture> list = new ArrayList<>();
        Cursor c = db.getListLecture();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            Lecture lecture = new Lecture(id, name);
            list.add(lecture);
        }
        c.close();
        return list;
    }

    private ArrayList<Subject> initListSubject(){

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

    private boolean validateInput(){
        String name = edtName.getText().toString();
        String subject = actvSubject.getText().toString();
        String lecture = actvLecture.getText().toString();
        String started = actvLecture.getText().toString();
        String quantity = edtQuantity.getText().toString();
        String year = edtYear.getText().toString();

        return (name != null && !name.isEmpty() &&
                subject != null && !subject.isEmpty() &&
                lecture != null && !lecture.isEmpty() &&
                started != null && !started.isEmpty() &&
                quantity != null && !quantity.isEmpty() &&
                year != null && !year.isEmpty());
    }

    private void requiredInput(){
        String name = edtName.getText().toString();
        String subject = actvSubject.getText().toString();
        String lecture = actvLecture.getText().toString();
        String started = actvLecture.getText().toString();
        String quantity = edtQuantity.getText().toString();
        String year = edtYear.getText().toString();

        if(!(name != null && !name.isEmpty()))
            edtName.setError(getResources().getString(R.string.inputRequired));

        if(!(subject != null && !subject.isEmpty()))
            actvSubject.setError(getResources().getString(R.string.inputRequired));

        if(!(lecture != null && !lecture.isEmpty()))
            actvLecture.setError(getResources().getString(R.string.inputRequired));

        if(!(year != null && !year.isEmpty()))
            edtYear.setError(getResources().getString(R.string.inputRequired));

        if(!(started != null && !started.isEmpty()))
            edtStarted.setError(getResources().getString(R.string.inputRequired));

        if(!(quantity != null && !quantity.isEmpty()))
            edtQuantity.setError(getResources().getString(R.string.inputRequired));

    }

    private boolean checkDateValidate(String date) throws ParseException {
        Date d = df.StringToDate(date);
        Date now = new Date();
        long m = d.getTime() - now.getTime();
        return m > 0 ? true : false;
    }
}

