package com.example.studentmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pojo.Subject;
import com.example.service.QLSVDatabase;
import com.example.service.SubjectArrayAdapter;
import com.example.studentmanagement.adding.SubjectAdding;
import com.example.studentmanagement.infomation.SubjectInfomation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class QuanlyMonhoc extends AppCompatActivity {

    Button btnFilter, btnAdd;
    EditText edtSearch;
    ListView lvList;
    SubjectArrayAdapter adapter;
    ArrayList<Subject> subjectList = new ArrayList<Subject>();

    QLSVDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_quanly_monhoc);
        db = new QLSVDatabase(this);
        btnAdd = (Button) findViewById(R.id.btnSubjectAdding);
        btnFilter = (Button) findViewById(R.id.btnFilterSubject);
        edtSearch = (EditText) findViewById(R.id.edtSearchSubject);
        lvList = (ListView) findViewById(R.id.lvSubjectList);
        adapter = new SubjectArrayAdapter(this, R.layout.listview_subject_item, subjectList);
        lvList.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlyMonhoc.this, SubjectAdding.class);
                startActivity(intent);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(QuanlyMonhoc.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.object_edit){
                            Intent intent = new Intent(QuanlyMonhoc.this, SubjectAdding.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) subjectList.get(position);
                            bundle.putSerializable("Subject", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }else if(id == R.id.object_delete) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(QuanlyMonhoc.this);
                            builder.setTitle("Xác nhận");
                            builder.setMessage("Bạn có chắc chắn muốn xoá môn họv này: \n" +
                                    subjectList.get(position).getName());
                            builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(db.delete_subject(subjectList.get(position).getId()) > 0){
                                        Toast.makeText(QuanlyMonhoc.this, subjectList.get(position).getName() + " đã bị xoá", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(QuanlyMonhoc.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                    subjectList.remove(position);
                                    adapter.notifyDataSetChanged();
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
                            return true;
                        }else if(id == R.id.object_show){
                            Intent intent = new Intent(QuanlyMonhoc.this, SubjectInfomation.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) subjectList.get(position);
                            bundle.putSerializable("Subject", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.inflate(R.menu.popup_user_item);
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
                subjectList.clear();
                Cursor c = db.getListSubjectBy(s.toString(), 0);
                initData(c);
            }
        });


        String[] filter1 = {"A - Z", "Z - A"};
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanlyMonhoc.this);
                builder.setTitle("Lọc danh sách");
                builder.setSingleChoiceItems(filter1, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Collections.reverse(subjectList);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.create();
                builder.show();
            }
        });
    }

    private void initData(Cursor c){
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int credit = c.getInt(2);
            double midPercent = c.getDouble(3);
            double finalPercent = c.getDouble(4);
            Subject s = new Subject(id, name, credit, midPercent, finalPercent);
            subjectList.add(s);
        }
        adapter.notifyDataSetChanged();
        c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subjectList.clear();
        Cursor c = db.get_list_subject();
        initData(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        Menu menuInflater = menu;
        menu.setGroupVisible(R.id.grMenuDefault, true);
        return super.onCreateOptionsMenu(menu);
    }
}