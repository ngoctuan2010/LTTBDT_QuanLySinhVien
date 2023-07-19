package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pojo.Lecture;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;

public class UserAdding extends AppCompatActivity {

    private String[] role = {"QUẢN TRỊ VIÊN CẤP CAO", "QUẢN TRỊ VIÊN", "GIẢNG VIÊN"};
    private String[] collage = {"TNT", "TTN", "LHTV"};
    Spinner spRole;
    EditText edtUsername, edtPassword;
    AutoCompleteTextView edtLecture;
    Button btnAdd, btnUpdate;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adding);

        db = new QLSVDatabase(this);

        spRole = (Spinner) findViewById(R.id.spUserAddingRole);
        edtUsername = (EditText) findViewById(R.id.edtUserAddingUsername);
        edtPassword = (EditText) findViewById(R.id.edtUserAddingPassword);
        edtLecture = (AutoCompleteTextView) findViewById(R.id.edtUserAddingCollage);
        btnAdd = (Button) findViewById(R.id.btnUserAdd);
        btnUpdate = (Button) findViewById(R.id.btnUserEdit);



        ArrayAdapter roleAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, role);
        spRole.setAdapter(roleAdapter);

        ArrayAdapter lectureAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, collage);
        edtLecture.setAdapter(lectureAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            btnAdd.setVisibility(View.GONE);
            edtLecture.setEnabled(false);

            User user = (User) bundle.get("User");
            edtUsername.setText(user.getUsername());
            edtPassword.setText(user.getPassword());
            edtLecture.setText(user.getLecture());
            spRole.setSelection(user.getRole());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    int role = spRole.getSelectedItemPosition();

                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);

                    db.update_user(user);
                    onBackPressed();
                }
            });
        }
        else
        {
            btnUpdate.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
int lecture_id = Integer.parseInt(edtLecture.getText().toString());
int role = spRole.getSelectedItemPosition();
                    User user = new User(10, username, password, role, lecture_id);
                    db.add_user(user);
                }
            });
        }



    }


}