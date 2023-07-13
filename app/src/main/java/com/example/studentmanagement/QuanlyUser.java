package com.example.studentmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pojo.User;
import com.example.service.UserArrrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class QuanlyUser extends AppCompatActivity {

    EditText edtUserSearch;
    Button btnUserFilter, btnUserAdd;
    ListView lvUserList;

    ArrayList<User> userList = new ArrayList<User>();
    UserArrrayAdapter userArrrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quanly_user);

        edtUserSearch = (EditText) findViewById(R.id.edtSearchUser);
        btnUserFilter = (Button) findViewById(R.id.btnFilterUser);
        btnUserAdd = (Button) findViewById(R.id.btnAddUser);
        lvUserList = (ListView) findViewById(R.id.lvUser);

        userList.add(new User(1, "ngoctuan2010", "123", 0,1));
        userList.add(new User(2, "ngoctuan2011", "123", 1,1));
        userList.add(new User(3, "ngoctuan2012", "123", 2,1));
        userList.add(new User(4, "ngoctuan2013", "123", 2,1));

        userArrrayAdapter = new UserArrrayAdapter(this, R.layout.listview_user_item, userList);
        lvUserList.setAdapter(userArrrayAdapter);

        btnUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlyUser.this, UserAdding.class);
                startActivityForResult(intent, 1);
            }
        });

        lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuanlyUser.this, UserAdding.class);
                Bundle bundle = new Bundle();
                Serializable pack = (Serializable) userList.get(position);
                bundle.putSerializable("User", pack);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                User user = (User) data.getExtras().get("UserAdded");
                userList.add(user);
                userArrrayAdapter.notifyDataSetChanged();
            }
        }
    }
}