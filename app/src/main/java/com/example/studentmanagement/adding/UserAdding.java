package com.example.studentmanagement.adding;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pojo.Lecture;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UserAdding extends AppCompatActivity {

    private String[] role = {"QUẢN TRỊ VIÊN", "GIẢNG VIÊN"};
    private ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    Spinner spRole;
    EditText edtUsername, edtPassword;

    TextInputLayout tilPassword;
    AutoCompleteTextView edtLecture;
    Button btnAdd, btnUpdate;

    QLSVDatabase db;
    private int lecture_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_adding);

        db = new QLSVDatabase(this);

        spRole = (Spinner) findViewById(R.id.spUserAddingRole);
        edtUsername = (EditText) findViewById(R.id.edtLectureAddingId);
        tilPassword = (TextInputLayout) findViewById(R.id.tilUserAddingPassword);
        edtLecture = (AutoCompleteTextView) findViewById(R.id.edtUserAddingCollage);
        btnAdd = (Button) findViewById(R.id.btnUserAdd);
        btnUpdate = (Button) findViewById(R.id.btnUserEdit);



        ArrayAdapter roleAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, role);
        spRole.setAdapter(roleAdapter);

        lectures = initListLecture();
        ArrayAdapter<Lecture> lectureAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lectures);
        edtLecture.setAdapter(lectureAdapter);

        edtLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lecture_id = ((Lecture) parent.getItemAtPosition(position)).getId();
            }
        });
        edtLecture.addTextChangedListener(new TextWatcher() {
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
        edtLecture.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && lecture_id == -1)
                    edtLecture.setError("Vui lòng chọn giảng viên");
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            btnAdd.setVisibility(View.GONE);
            edtLecture.setEnabled(false);

            User user = (User) bundle.get("User");
            edtUsername.setText(user.getUsername());
            tilPassword.getEditText().setText(user.getPassword());

            Cursor cLecture = db.getLectureById(user.getLecture());
            cLecture.moveToFirst();
            String strLecture = cLecture.getString(1) +  " [" + Integer.toString(cLecture.getInt(0)) +"]";
            edtLecture.setText(strLecture);
            lecture_id = user.getLecture();

            spRole.setSelection(user.getRole());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(validateInput()){
                       String username = edtUsername.getText().toString();
                       String password = tilPassword.getEditText().getText().toString();
                       int role = spRole.getSelectedItemPosition();

                       Cursor c = db.getUserByUsername(username);
                       if(!(c.getCount() > 0)){
                           user.setUsername(username);
                           user.setPassword(password);
                           user.setRole(role);

                           if(db.update_user(user) > 0){
                               Toast.makeText(UserAdding.this, "Tài khoản đã được chỉnh sửa", Toast.LENGTH_SHORT).show();
                               onBackPressed();
                           }else{
                               Toast.makeText(UserAdding.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           Toast.makeText(UserAdding.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                       }


                   }else{
                       requiredInput();
                   }
                }
            });
        }
        else
        {
            btnUpdate.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   if(validateInput()){
                       String username = edtUsername.getText().toString();
                       String password = tilPassword.getEditText().getText().toString();
                       int role = spRole.getSelectedItemPosition();

                       if(lecture_id == -1){
                           return;
                       }

                       Cursor c = db.getUserByUsername(username);

                       if(!(c.getCount() > 0)){
                           User user = new User(10, username, password, role, lecture_id);
                           if(db.add_user(user) > 0){
                               Toast.makeText(UserAdding.this, "Tài khoản đã thêm vào", Toast.LENGTH_SHORT).show();
                               onBackPressed();
                           }else{
                               Toast.makeText(UserAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           Toast.makeText(UserAdding.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                       }

                    }else{
                        requiredInput();
                    }
                }
            });
        }



    }


    private boolean validateInput(){
        String username = edtUsername.getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        String lecture = edtLecture.getText().toString();

       return (username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                lecture != null && !lecture.isEmpty());
    }

    private void requiredInput(){
        String username = edtUsername.getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        String lecture = edtLecture.getText().toString();

        if(!(username != null && !username.isEmpty()))
            edtUsername.setError(getResources().getString(R.string.inputRequired));
        if(!(password != null && !password.isEmpty()))
            tilPassword.getEditText().setError(getResources().getString(R.string.inputRequired));
        if(!(lecture != null && !lecture.isEmpty()))
            edtLecture.setError(getResources().getString(R.string.inputRequired));
    }

    private ArrayList<Lecture> initListLecture(){
        ArrayList<Lecture> list = new ArrayList<Lecture>();
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
}