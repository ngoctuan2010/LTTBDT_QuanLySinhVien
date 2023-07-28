package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.service.HomeSubjectArrayAdapter;
import com.example.service.QLSVDatabase;

import java.util.ArrayList;

public class HomePageLecture extends AppCompatActivity {
    TextView tvName, tvId, tvBirth;
    Spinner spnSemester;
    ListView lvSubject;
    String[] listSemester = {"Học kì I năm 2022-2023", "Học kì II năm 2022-2023", "Học kì III năm 2022-2023"};
    QLSVDatabase db;
    int idLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_lecture);

        spnSemester = (Spinner) findViewById(R.id.spnSemester);
        lvSubject = (ListView) findViewById(R.id.lvSubject) ;

        tvName = (TextView) findViewById(R.id.txtName);
        tvId = (TextView) findViewById(R.id.txtId);
        tvBirth = (TextView) findViewById(R.id.txtBirth);

        db = new QLSVDatabase(this);

        Bundle bundle = getIntent().getExtras();
        int idUser = bundle.getInt("idUser");
        Cursor cursorGetId = db.getUserById(idUser);
        cursorGetId.moveToFirst();
        idLecture = cursorGetId.getInt(3);
        Cursor cursorLecture = db.getLectureById(idLecture);
        cursorLecture.moveToFirst();

//        SetText textview
        tvName.setText(cursorLecture.getString(1));
        tvId.setText(cursorLecture.getString(0));
        tvBirth.setText(cursorLecture.getString(4));

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSemester);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSemester.setAdapter(adapter);

        ArrayList<Class> listClass = new ArrayList<Class>();
        HomeSubjectArrayAdapter homeSubjectArrayAdapter = new HomeSubjectArrayAdapter(HomePageLecture.this, R.layout.class_item_layout, listClass);
        Cursor cursor = db.get_list_class();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int idSubject = cursor.getInt(2);
            int quantity = cursor.getInt(4);
            String year = cursor.getString(5);
            listClass.add(new Class(id, name, idSubject, quantity, year));
        }
        lvSubject.setAdapter(homeSubjectArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.setGroupVisible(R.id.grMenuDefault, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent intent = new Intent(HomePageLecture.this, ProfileLecture.class);
            Bundle bundle = new Bundle();
            bundle.putInt("LectureId", idLecture);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(this, "Bạn vào trang cá nhân", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_class) {
            Toast.makeText(this, "Bạn vào trang sửa lớp", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_collape) {
            Toast.makeText(this, "Bạn vào trang sửa giảng viên", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_student) {
            Toast.makeText(this, "Bạn vào trang sửa sinh viên", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_subject) {
            Toast.makeText(this, "Bạn vào trang sửa môn học", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_sign_out) {
            Intent intent = new Intent(HomePageLecture.this, LogIn.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}