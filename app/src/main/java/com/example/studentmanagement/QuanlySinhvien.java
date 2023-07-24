package com.example.studentmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Student;
import com.example.service.QLSVDatabase;
import com.example.service.StudentArrayAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuanlySinhvien extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText medtSearch;
    ImageButton mbtnSearch, mbtnDelete;
    Spinner mspnProperties;
    ListView mlvSV;
    Button mbtnAdd;
    String[] itemsSpinner = {"Mã", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "Khoa", "Niên khóa"};
    ArrayAdapter<String> adapter;
    ArrayList<Student> arrayListStudent;
    StudentArrayAdapter studentArrayAdapter;
    QLSVDatabase database;
    int selectedSpinnerPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_sinhvien);
        medtSearch = findViewById(R.id.edtSearchStudent);
        mbtnSearch = findViewById(R.id.btnSearch);
        mspnProperties = findViewById(R.id.spnProperties);
        mlvSV = findViewById(R.id.lvSinhVien);
        mbtnAdd = findViewById(R.id.btnAddStudent);
        mbtnDelete = findViewById(R.id.imgbtnDeleteStudent);
        database = new QLSVDatabase(QuanlySinhvien.this);
        adapter = new ArrayAdapter<String>(QuanlySinhvien.this, android.R.layout.simple_spinner_dropdown_item, itemsSpinner);
        mspnProperties.setAdapter(adapter);
        get_Full_List();
        mspnProperties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinnerPosition = i;
                switch (selectedSpinnerPosition) {
                    case 0:
                        medtSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 6:
                    case 7:
                        medtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 5:
                        medtSearch.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case 3:
                        medtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                        medtSearch.setHint("dd/MM/yyyy");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanlySinhvien.this, StudentAdding.class);
                startActivity(intent);
            }
        });
        mlvSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupMenu(view, i);
            }
        });
        mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = mlvSV.getChildCount() - 1; i >= 0; i--) {
                    view = mlvSV.getChildAt(i);
                    CheckBox ck = view.findViewById(R.id.chkitemStudent);
                    if (ck.isChecked()) {
                        Student student = arrayListStudent.get(i);
                        database.delete_student(student);
                    }
                }
                get_Full_List();
            }
        });
        mbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = medtSearch.getText().toString().trim().toLowerCase();
                switch (selectedSpinnerPosition) {
                    case 0:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập mã sinh viên.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (database.getStudentById(Integer.parseInt(string)) == null) {
                            Toast.makeText(QuanlySinhvien.this, "Không tìm thấy sinh viên phù hợp.", Toast.LENGTH_SHORT).show();
                            arrayListStudent.clear();
                            studentArrayAdapter.notifyDataSetChanged();
                            return;
                        }
                        checkAndShow();
                        break;
                    case 1:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập tên sinh viên.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_name(string));
                        checkAndShow();
                        break;
                    case 2:
                        boolean gioiTinh;
                        if (string.equals("nam"))
                            gioiTinh = true;
                        else if (string.equals("nu") || string.equals("nữ"))
                            gioiTinh = false;
                        else {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập đúng giá trị giới tính(Nam hoặc Nữ).", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_gender(gioiTinh));
                        checkAndShow();
                        break;
                    case 3:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập ngày sinh sinh viên.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_birth(string));
                        checkAndShow();
                        break;
                    case 4:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập địa chỉ sinh viên.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_address(string));
                        checkAndShow();
                        break;
                    case 5:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập số điện thoại sinh viên.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get__list_students_by_phone(string));
                        checkAndShow();
                        break;
                    case 6:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập khoa.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_department(string));
                        checkAndShow();
                        break;
                    case 7:
                        if (string.isEmpty()) {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập niên khóa.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        arrayListStudent.addAll(database.get_list_students_by_schoolyear(string));
                        checkAndShow();
                        break;
                }
            }
        });
        medtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (selectedSpinnerPosition == 5) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get__list_students_by_phone(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 0) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_id(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 3) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_birth(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 7) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_schoolyear(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 4) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_address(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 1) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_name(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 2) {
                    String string = medtSearch.getText().toString().trim().toLowerCase();
                    boolean gioiTinh;
                    if (string.equals("nam"))
                        gioiTinh = true;
                    else if (string.equals("nu") || string.equals("nữ"))
                        gioiTinh = false;
                    else
                        return;
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_gender(gioiTinh));
                    studentArrayAdapter.notifyDataSetChanged();
                }
                if (selectedSpinnerPosition == 6) {
                    arrayListStudent.clear();
                    arrayListStudent.addAll(database.get_list_students_by_department(medtSearch.getText().toString().trim().toLowerCase()));
                    studentArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (medtSearch.getText().toString().trim().isEmpty())
                    get_Full_List();
            }
        });
    }

    public void get_Full_List() {
        arrayListStudent = new ArrayList<>();
        studentArrayAdapter = new StudentArrayAdapter(QuanlySinhvien.this, R.layout.custom_layout_listview_student, arrayListStudent);
        mlvSV.setAdapter(studentArrayAdapter);
        arrayListStudent.clear();
        arrayListStudent.addAll(database.get_list_students());
        studentArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_Full_List();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        get_Full_List();
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(QuanlySinhvien.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu_item_student, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemXemChiTiet) {
                    Student student = arrayListStudent.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuanlySinhvien.this);
                    builder.setTitle("Thông tin chi tiết của sinh viên: ");
                    builder.setMessage(student.toString());
                    builder.create();
                    builder.show();
                    return true;
                }
                if (menuItem.getItemId() == R.id.itemupdateStudent) {
                    Student student = arrayListStudent.get(position);
                    int idStudent = student.getId();
                    Intent intent = new Intent(QuanlySinhvien.this, StudentUpdate.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("student_id", idStudent);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void checkAndShow() {
        if (arrayListStudent.isEmpty())
            Toast.makeText(QuanlySinhvien.this, "Không tìm thấy sinh viên phù hợp.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(QuanlySinhvien.this, "Tìm thấy " + arrayListStudent.size() + " sinh viên.", Toast.LENGTH_SHORT).show();
        studentArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}