package com.example.studentmanagement.information;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.pojo.Student;
import com.example.service.QLSVDatabase;
import com.example.service.StudentArrayAdapter;
import com.example.studentmanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClassInformation extends AppCompatActivity {
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

    TextView tvId, tvName, tvSubject, tvLecture, tvQuantity, tvYear, tvStarted;
    EditText edtSearch;

    ListView lvStudents;
    Button btnAddStudent;

    QLSVDatabase db;

    ArrayList<Student> students = new ArrayList<Student>();

    StudentArrayAdapter adapter;

    Student student;

    Class _class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_infomation);

        db = new QLSVDatabase(this);

        tvId = (TextView) findViewById(R.id.tvClassInfoID);
        tvName = (TextView) findViewById(R.id.tvClassInfoName);
        tvSubject = (TextView) findViewById(R.id.tvClassInfoSubject);
        tvLecture = (TextView) findViewById(R.id.tvClassInfoLecture);
        tvQuantity = (TextView) findViewById(R.id.tvClassInfoQuantity);
        tvYear = (TextView) findViewById(R.id.tvClassInfoYear);
        tvStarted = (TextView) findViewById(R.id.tvClassInfoStarted);
        btnAddStudent = (Button) findViewById(R.id.btnClassAddStudent);
        lvStudents = (ListView) findViewById(R.id.lvClassInfoStudent);

        edtSearch = (EditText) findViewById(R.id.edtClassSearchStudent);

        adapter = new StudentArrayAdapter(this, R.layout.custom_layout_listview_student, students);
        lvStudents.setAdapter(adapter);


        Bundle bundle = getIntent().getExtras();
        _class = (Class) bundle.getSerializable("class");

        tvId.setText("ID: " + Integer.toString(_class.getId()));
        tvName.setText("Lớp: " + _class.getName());

        Cursor c = db.getListSubjectById(_class.getSubject_id());
        if (c.getCount() > 0) {
            c.moveToFirst();
            tvSubject.setText("Môn học: " + c.getString(1));
        } else {
            tvSubject.setText("Môn học: ");
        }

        c = db.getLectureById(_class.getLecture());
        if (c.getCount() > 0) {
            c.moveToFirst();
            tvLecture.setText("Giảng viên: " + c.getString(1));
        } else {
            tvLecture.setText("Giảng viên: ");
        }
        tvQuantity.setText("Số lượng: " + Integer.toString(_class.getQuantity()));
        tvYear.setText("Khoá: " + _class.getYear());
        tvStarted.setText("Thời gian bắt đầu: " + _class.getStarted());

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassInformation.this);
                builder.setTitle("Thêm sinh viên vào lớp học");
                EditText input = new EditText(ClassInformation.this);
                TextView view = new TextView(ClassInformation.this);

                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            int id = Integer.parseInt(s.toString());
                            student = db.getStudentById(id);
                            if (student != null)
                                view.setText("MSSV: " + student.getId() + "\n"
                                        + "Họ và tên: " + student.getName());
                            else
                                view.setText("Không tìm thấy");
                        } catch (ParseException e) {
                            view.setText("");
                        } catch (NumberFormatException e) {
                            view.setText("");
                        }
                    }
                });

                LinearLayout layout = new LinearLayout(ClassInformation.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(input);
                layout.addView(view);
                builder.setView(layout);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (student != null) {

                            if (db.is_student_in_class(student.getId(), _class.getId())) {
                                Toast.makeText(ClassInformation.this, "Sinh viên đã có trong lớp", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(students.size() + 1 > _class.getQuantity())
                            {
                                Toast.makeText(ClassInformation.this, "Lớp đã đủ sinh viên", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            if (db.add_student_to_class(student.getId(), _class.getId()) > 0) {
                                students.add(student);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ClassInformation.this, "Đã thêm " + student.getName() + " vào lớp " + _class.getName(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ClassInformation.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ClassInformation.this, "Chưa tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
                        }
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

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student st = (Student) parent.getItemAtPosition(position);
                PopupMenu popupMenu = new PopupMenu(ClassInformation.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.student_detail) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClassInformation.this);
                            builder.setTitle("Thông tin sinh viên");
                            builder.setMessage(st.toString());
                            builder.setNeutralButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create();
                            builder.show();
                            return true;
                        } else if (id == R.id.student_score) {
<<<<<<< HEAD:app/src/main/java/com/example/studentmanagement/infomation/ClassInfomation.java
                            Intent intent = new Intent(ClassInfomation.this, StudentInformation.class);
                            Student std = (Student) lvStudents.getItemAtPosition(position);
                            bundle.putSerializable("student", std);
=======
                            Intent intent = new Intent(ClassInformation.this, StudentInformation.class);
                            bundle.putSerializable("student", student);
>>>>>>> 3ce845fc4280a0edc8352c149f1ad8279fe1209c:app/src/main/java/com/example/studentmanagement/information/ClassInformation.java
                            bundle.putSerializable("class", _class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        } else if (id == R.id.student_remove) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClassInformation.this);
                            builder.setTitle("Xác nhận");
                            builder.setMessage("Bạn có chắc chắn muốn xoá sinh viên này: " + st.getName() + " - " + st.getId());
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (db.delete_student_from_class(st.getId(), _class.getId()) > 0) {
                                        students.remove(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(ClassInformation.this, "Đã xoá " + st.getName() + " ra khỏi lớp " + _class.getName(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ClassInformation.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder.setNeutralButton("Huỷ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create();
                            builder.show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu_student_in_class);
                popupMenu.setGravity(Gravity.END);

                popupMenu.show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String param = s.toString();
                Cursor c = db.find_student_in_class_by(param, _class.getId());
                try {
                    initStudentList(c);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor c = db.getListStudentByClass(_class.getId());
        try {
            initStudentList(c);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void initStudentList(Cursor c) throws ParseException {
        students.clear();
        c.moveToPosition(-1);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            int gender = c.getInt(2);
            String birth = c.getString(3);
            String address = c.getString(4);
            String phone = c.getString(5);
            String department = c.getString(6);
            String year = c.getString(7);
            Student sts = new Student(id, name, (gender == 1), spdf.parse(birth), address, phone, department, year);
            students.add(sts);
        }
        adapter.notifyDataSetChanged();
        c.close();
    }
}