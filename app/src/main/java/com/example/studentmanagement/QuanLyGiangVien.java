package com.example.studentmanagement;

import androidx.annotation.NonNull;
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

import com.example.pojo.Lecture;
import com.example.service.LectureArrayAdapter;
import com.example.service.QLSVDatabase;
import com.example.service.SharePreferenceServeice;
import com.example.studentmanagement.adding.LectureAdding;
import com.example.studentmanagement.information.LectureInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class QuanLyGiangVien extends AppCompatActivity {
    Button btnFilter, btnAddLecture;
    EditText edtSearch;
    ListView lvLecture;
    ArrayList<Lecture> listLecture = new ArrayList<Lecture>();
    LectureArrayAdapter adapter;

    QLSVDatabase db;

    SharePreferenceServeice sharePreferenceServeice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_quan_ly_giang_vien);

        btnFilter = (Button) findViewById(R.id.btnFilterLecture);
        btnAddLecture = (Button) findViewById(R.id.btnLectureAdding);
        edtSearch = (EditText) findViewById(R.id.edtSearchLecture);
        lvLecture = (ListView) findViewById(R.id.lvLectureList);
        sharePreferenceServeice = new SharePreferenceServeice(this, "User");
        db = new QLSVDatabase(this);

        //Set ListView
        adapter = new LectureArrayAdapter(this, R.layout.listview_lecture_item, listLecture);
        lvLecture.setAdapter(adapter);

        btnAddLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyGiangVien.this, LectureAdding.class);
                startActivity(intent);
            }
        });

        lvLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(QuanLyGiangVien.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.object_edit){
                            Intent intent = new Intent(QuanLyGiangVien.this, LectureAdding.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) listLecture.get(position);
                            bundle.putSerializable("Lecture", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }
                        else if(id == R.id.object_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyGiangVien.this);
                            builder.setTitle("Xác nhận");
                            builder.setMessage("Bạn có chắc chắn muốn xoá giảng viên này: \n" +
                                    listLecture.get(position).getName());
                            builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(db.delete_lecture(listLecture.get(position).getId()) > 0){
                                        Toast.makeText(QuanLyGiangVien.this, listLecture.get(position).getName() + " đã bị xoá", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(QuanLyGiangVien.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                    listLecture.remove(position);
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
                            Intent intent = new Intent(QuanLyGiangVien.this, LectureInformation.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) listLecture.get(position);
                            bundle.putSerializable("Lecture", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.inflate(R.menu.popup_menu_class);
                popupMenu.setGravity(Gravity.END);
                popupMenu.show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listLecture.clear();
                Cursor c = db.getListLectureBy(editable.toString(), 0);
                initData(c);
            }
        });

        String[] filterArrange = {"A - Z", "Z - A"};
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyGiangVien.this);
                builder.setTitle("Lọc danh sách");
                builder.setSingleChoiceItems(filterArrange, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Collections.reverse(listLecture);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    public void initData(Cursor cursor) {
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            boolean gender = true;
            if (cursor.getString(2).equals("0")) {
                gender = false;
            }
            String date = cursor.getString(3);
            String address = cursor.getString(4);
            String phone = cursor.getString(5);
            String department = cursor.getString(6);

            Lecture lecture = new Lecture(id, name, gender, date, address, phone, department);
            listLecture.add(lecture);
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listLecture.clear();
        Cursor cursor = db.getListLecture();
        initData(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        Menu menuInflater = menu;
        menu.setGroupVisible(R.id.grMenuDefault, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_profile){
            Intent intent = new Intent(QuanLyGiangVien.this, ProfileLecture.class);
            Bundle bundle = new Bundle();
            bundle.putInt("LectureId", Integer.parseInt(sharePreferenceServeice.getString("current_user")));
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (id == R.id.action_class) {
            Intent intent = new Intent(QuanLyGiangVien.this, QuanlyLophoc.class);
            startActivity(intent);
        }

        if (id == R.id.action_student) {
            Intent intent = new Intent(QuanLyGiangVien.this, QuanlySinhvien.class);
            startActivity(intent);
        }

        if (id == R.id.action_subject) {
            Intent intent = new Intent(QuanLyGiangVien.this, QuanlyMonhoc.class);
            startActivity(intent);
        }

        if (id == R.id.action_sign_out) {
            sharePreferenceServeice.clear();
            Intent intent = new Intent(QuanLyGiangVien.this, LogIn.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_user) {
            Intent intent = new Intent(QuanLyGiangVien.this, QuanlyUser.class);
            startActivity(intent);
        }
        if (id == R.id.action_statistical) {
            Intent intent = new Intent(QuanLyGiangVien.this, AdminStatistical.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}