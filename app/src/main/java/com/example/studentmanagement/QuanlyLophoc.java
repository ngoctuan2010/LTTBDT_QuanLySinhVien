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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.pojo.Class;
import com.example.service.ClassArrayAdapter;
import com.example.service.DateFormatter;
import com.example.service.QLSVDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

public class QuanlyLophoc extends AppCompatActivity {

    Button btnAdd, btnFilter;
    ListView lvClass;
    EditText edtID, edtSearch;

    ArrayList<Class> classList = new ArrayList<Class>();
    ClassArrayAdapter adapter;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_lophoc);

        db = new QLSVDatabase(this);

        btnAdd = (Button) findViewById(R.id.btnAddClass);
        btnFilter = (Button) findViewById(R.id.btnFilterClass);

        edtSearch = (EditText) findViewById(R.id.edtSearchClass);

        lvClass = (ListView) findViewById(R.id.lvListClass);
        edtID = (EditText) findViewById(R.id.edtSearchClass);

        adapter = new ClassArrayAdapter(this, R.layout.listview_class_item, classList);
        lvClass.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlyLophoc.this, ClassAdding.class);
                startActivity(intent);
            }
        });

        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(QuanlyLophoc.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.object_show){
                            return true;
                        }else if(id == R.id.object_edit){
                            Intent intent = new Intent(QuanlyLophoc.this, ClassAdding.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) classList.get(position);
                            bundle.putSerializable("class", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }else if(id == R.id.object_delete){
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuanlyLophoc.this);
                            builder.setTitle("Xoá người dùng");
                            builder.setMessage("Bạn có chắc chắn muốn xoá lớp học này: \n" +
                                    classList.get(position).getName() + " - " + classList.get(position).getSubject_id());
                            builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete_class(classList.get(position).getId());
                                    classList.remove(position);
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
                Cursor c = db.get_list_class_by(s.toString());
                classList.clear();
                initData(c);
            }
        });
    }

    @Override
    protected void onResume() {
        classList.clear();
        Cursor c =  db.get_list_class();
        initData(c);
        super.onResume();
    }

    public void initData(Cursor c) {
        DateFormatter df = new DateFormatter("dd/MM/yyyy");
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int subject = c.getInt(2);
            int lecture = c.getInt(3);
            int quantity = c.getInt(4);
            String year = c.getString(5);
            String stared = c.getString(6);
            Class _class = new Class(id, name, subject, lecture, quantity, year, stared);
            classList.add(_class);
        }
        adapter.notifyDataSetChanged();
        c.close();
    }
}