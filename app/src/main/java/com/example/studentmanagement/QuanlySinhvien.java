package com.example.studentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import com.example.studentmanagement.adding.StudentAdding;

import java.io.File;
import java.text.ParseException;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        try {
            get_Full_List();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        mspnProperties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinnerPosition = i;
                switch (selectedSpinnerPosition) {
                    case 0:
                        medtSearch.setHint("Nhập thông tin");
                        medtSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 6:
                        medtSearch.setHint("Nhập thông tin");
                        medtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 5:
                        medtSearch.setHint("Nhập thông tin");
                        medtSearch.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case 3:
                        medtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                        medtSearch.setHint("dd/MM/yyyy");
                        break;
                    case 7:
                        medtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                        medtSearch.setHint("yyyy-yyyy");
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
                try {
                    get_Full_List();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        mbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = medtSearch.getText().toString().trim().toLowerCase();
                switch (selectedSpinnerPosition) {
                    case 0:
                        try {
                            showSearchList("mã sinh viên.", database.get_list_students_by_id(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 1:
                        try {
                            showSearchList("tên sinh viên.", database.get_list_students_by_name(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        boolean gioiTinh;
                        if (string.equals("nam"))
                            gioiTinh = true;
                        else if (string.equals("nu") || string.equals("nữ"))
                            gioiTinh = false;
                        else {
                            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập đúng giới tính(Nam hoặc Nữ).", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arrayListStudent.clear();
                        try {
                            arrayListStudent.addAll(database.get_list_students_by_gender(gioiTinh));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        checkAndShow();
                        break;
                    case 3:
                        try {
                            showSearchList("ngày sinh sinh viên.", database.get_list_students_by_birth(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 4:
                        try {
                            showSearchList("địa chỉ sinh viên.", database.get_list_students_by_address(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 5:
                        try {
                            showSearchList("số điện thoại sinh viên.", database.get__list_students_by_phone(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 6:
                        try {
                            showSearchList("khoa.", database.get_list_students_by_department(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 7:
                        try {
                            showSearchList("niên khóa.", database.get_list_students_by_schoolyear(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
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
                String string = medtSearch.getText().toString().trim().toLowerCase();
                switch (selectedSpinnerPosition){
                    case 0:
                        try {
                            showSuggestionList(database.get_list_students_by_id(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 1:
                        try {
                            showSuggestionList(database.get_list_students_by_name(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        boolean gioiTinh;
                        if (string.equals("nam"))
                            gioiTinh = true;
                        else if (string.equals("nu") || string.equals("nữ"))
                            gioiTinh = false;
                        else
                            return;
                        arrayListStudent.clear();
                        try {
                            arrayListStudent.addAll(database.get_list_students_by_gender(gioiTinh));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        studentArrayAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        try {
                            showSuggestionList(database.get_list_students_by_birth(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 4:
                        try {
                            showSuggestionList(database.get_list_students_by_address(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 5:
                        try {
                            showSuggestionList(database.get__list_students_by_phone(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 6:
                        try {
                            showSuggestionList(database.get_list_students_by_department(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 7:
                        try {
                            showSuggestionList(database.get_list_students_by_schoolyear(string));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (medtSearch.getText().toString().trim().isEmpty()) {
                    try {
                        get_Full_List();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void get_Full_List() throws ParseException {
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
        try {
            get_Full_List();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            get_Full_List();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    public void showSearchList(String stringMessgae, ArrayList<Student> arrayList) {
        String string = medtSearch.getText().toString().trim().toLowerCase();
        if (string.isEmpty()) {
            Toast.makeText(QuanlySinhvien.this, "Vui lòng nhập " + stringMessgae, Toast.LENGTH_SHORT).show();
            return;
        }
        arrayListStudent.clear();
        arrayListStudent.addAll(arrayList);
        studentArrayAdapter.notifyDataSetChanged();
        checkAndShow();
    }
    public void showSuggestionList(ArrayList<Student> arrayList){
        arrayListStudent.clear();
        arrayListStudent.addAll(arrayList);
        studentArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
