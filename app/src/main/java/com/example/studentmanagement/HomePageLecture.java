package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.service.HomeSubjectArrayAdapter;
import com.example.service.QLSVDatabase;
import com.example.service.SharePreferenceServeice;
import com.example.studentmanagement.information.ClassInformation;

import java.io.Serializable;
import java.util.ArrayList;

public class HomePageLecture extends AppCompatActivity {
    TextView tvName, tvId, tvBirth;
    Spinner spnSemester;
    ListView lvSubject;
    String[] listSemester = {"Học kì I năm 2022-2023", "Học kì II năm 2022-2023", "Học kì III năm 2022-2023"};
    QLSVDatabase db;
    int idLecture;

    SharePreferenceServeice sharePreferenceServeice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_lecture);

        spnSemester = (Spinner) findViewById(R.id.spnSemester);
        lvSubject = (ListView) findViewById(R.id.lvSubject);

        tvName = (TextView) findViewById(R.id.txtName);

        db = new QLSVDatabase(this);
        sharePreferenceServeice = new SharePreferenceServeice(this, "User");

        int IdLecture = Integer.parseInt(sharePreferenceServeice.getString("current_user"));
        Cursor cursorLecture = db.getLectureById(IdLecture);
        cursorLecture.moveToFirst();

//        SetText textview
        tvName.setText(cursorLecture.getString(1));

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSemester);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSemester.setAdapter(adapter);

        ArrayList<Class> listClass = new ArrayList<Class>();
        HomeSubjectArrayAdapter homeSubjectArrayAdapter = new HomeSubjectArrayAdapter(HomePageLecture.this, R.layout.class_item_layout, listClass);

        Cursor cursor = db.get_list_class_by_lecture_active(IdLecture);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int idSubject = cursor.getInt(2);
            int idLecture = Integer.parseInt(sharePreferenceServeice.getString("current_user"));
            int quantity = cursor.getInt(4);
            String year = cursor.getString(5);
            String date = cursor.getString(6);
            listClass.add(new Class(id, name, idSubject, idLecture, quantity, year, date));
        }
        cursor.close();
        lvSubject.setAdapter(homeSubjectArrayAdapter);

        lvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HomePageLecture.this, ClassInformation.class);
                Bundle bundle = new Bundle();
                Serializable pack = (Serializable) listClass.get(position);
                bundle.putSerializable("class", pack);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.setGroupVisible(R.id.grMenuDefault, true);
        menu.findItem(R.id.action_class).setVisible(false);
        menu.findItem(R.id.action_collape).setVisible(false);
        menu.findItem(R.id.action_statistical).setVisible(false);
        menu.findItem(R.id.action_student).setVisible(false);
        menu.findItem(R.id.action_user).setVisible(false);
        menu.findItem(R.id.action_subject).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sign_out) {
            sharePreferenceServeice.clear();
            Intent intent = new Intent(HomePageLecture.this, LogIn.class);
            startActivity(intent);
            finish();
        }
        if(id == R.id.action_profile)
        {
            Intent intent = new Intent(HomePageLecture.this, ProfileLecture.class);
            Bundle bundle = new Bundle();
            bundle.putInt("LectureId", Integer.parseInt(sharePreferenceServeice.getString("current_user")));
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return true;
    }
}