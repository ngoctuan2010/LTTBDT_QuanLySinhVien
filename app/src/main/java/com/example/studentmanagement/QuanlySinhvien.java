package com.example.studentmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        arrayListStudent = new ArrayList<>();
        studentArrayAdapter = new StudentArrayAdapter(QuanlySinhvien.this, R.layout.custom_layout_listview_student, arrayListStudent);
        mlvSV.setAdapter(studentArrayAdapter);

        get_Full_List();

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
                        get_Full_List();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void get_Full_List() {
        arrayListStudent.clear();
        arrayListStudent.addAll(database.get_list_student());
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
}
