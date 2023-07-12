package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pojo.User;

import java.io.Serializable;

public class UserAdding extends AppCompatActivity {

    private String[] role = {"SUPERADMIN", "ADMIN", "COLLAGE"};
    private String[] collage = {"TNT", "TTN", "LHTV"};
    Spinner spRole;
    EditText edtUsername, edtPassword;
    AutoCompleteTextView edtCollage;
    Button btnAdd, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_adding);
        spRole = (Spinner) findViewById(R.id.spUserAddingRole);
        edtUsername = (EditText) findViewById(R.id.edtAddingSubjectName);
        edtPassword = (EditText) findViewById(R.id.edtAddingSubjectCredit);
        edtCollage = (AutoCompleteTextView) findViewById(R.id.edtUserAddingCollage);
        btnAdd = (Button) findViewById(R.id.btnUserAdd);
        btnUpdate = (Button) findViewById(R.id.btnUserEdit);



        ArrayAdapter roleAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, role);
        spRole.setAdapter(roleAdapter);

        ArrayAdapter collgaeAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, collage);
        edtCollage.setAdapter(collgaeAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            btnAdd.setVisibility(View.GONE);
            User user = (User) bundle.get("User");
            edtUsername.setText(user.getUsername());
            edtPassword.setText(user.getPassword());
            edtCollage.setText(Integer.toString(user.getLecture()));
            spRole.setSelection(user.getRole());
        }else{
            btnUpdate.setVisibility(View.GONE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    User user = new User(10, username, password, 1, 1);

                    Intent intent = new Intent();
                    Bundle b = new Bundle();
                    b.putSerializable("UserAdded", (Serializable) user);
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    onRestart();
                }
            });

        }



    }


}