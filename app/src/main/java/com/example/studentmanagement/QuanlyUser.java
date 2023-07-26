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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pojo.User;
import com.example.service.QLSVDatabase;
import com.example.service.UserArrrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class QuanlyUser extends AppCompatActivity {

    EditText edtUserSearch;
    Button btnUserFilter, btnUserAdd;
    ListView lvUserList;
    ArrayList<User> userList;
    UserArrrayAdapter userArrrayAdapter;

    Menu menuInflater;

    QLSVDatabase db;

    String[] _role = {"Quản trị viên cấp cao", "Quản trị viên", "Giảng viên"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_quanly_user);


        db = new QLSVDatabase(this);

        edtUserSearch = (EditText) findViewById(R.id.edtSearchUser);
        btnUserFilter = (Button) findViewById(R.id.btnFilterUser);
        btnUserAdd = (Button) findViewById(R.id.btnAddUser);
        lvUserList = (ListView) findViewById(R.id.lvUser);

        userList = new ArrayList<User>();
        userArrrayAdapter = new UserArrrayAdapter(this, R.layout.listview_user_item, userList);
        lvUserList.setAdapter(userArrrayAdapter);

        btnUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlyUser.this, UserAdding.class);
                startActivity(intent);
            }
        });
        single_select_item();
        lvUserList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                multi_select_item(parent, view, position, id);
                return false;
            }
        });

       edtUserSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
                userList.clear();
               Cursor c = db.getListBy(s.toString());
                initUserList(c);
           }
       });

       btnUserFilter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(QuanlyUser.this);
               builder.setTitle("Vai trò");
               builder.setSingleChoiceItems(_role, -1, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       userList.clear();
                       Cursor c = db.getListUserByRole(which);
                       initUserList(c);
                       dialog.cancel();
                   }
               });

               builder.create();
               builder.show();
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menuInflater = menu;
        menu.setGroupVisible(R.id.grMenuDefault, true);
        return super.onCreateOptionsMenu(menu);
    }

    private void single_select_item(){
        lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(QuanlyUser.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int choice = item.getItemId();
                        if(choice == R.id.user_show){
                            return true;
                        }else if(choice == R.id.user_edit){
                            Intent intent = new Intent(QuanlyUser.this, UserAdding.class);
                            Bundle bundle = new Bundle();
                            Serializable pack = (Serializable) userList.get(position);
                            bundle.putSerializable("User", pack);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return true;
                        }else if(choice == R.id.user_delete){
                            AlertDialog.Builder builder = new AlertDialog.Builder(QuanlyUser.this);
                            builder.setTitle("Xoá người dùng");
                            builder.setMessage("Bạn có chắc chắn muốn xoá người dùng này: \n" +
                                    userList.get(position).getUsername() + " - " + User.ROLE.values()[userList.get(position).getRole()]);
                            builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete_user(userList.get(position).getId());
                                    userList.remove(position);
                                    userArrrayAdapter.notifyDataSetChanged();
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
                popupMenu.show();
            }
        });

    }

    private void multi_select_item(AdapterView<?> parent, View view, int position, long id){
        lvUserList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView checked = (ImageView) view.findViewById(R.id.imgUserItemCheck);
                Toast.makeText(QuanlyUser.this, Integer.toString(userArrrayAdapter.checkList.size()), Toast.LENGTH_SHORT).show();
               if(lvUserList.isItemChecked(position))
               {
                   checked.setVisibility(View.VISIBLE);
               }
               else {
                   checked.setVisibility(View.GONE);
               }
            }
        });
        menuInflater.setGroupVisible(R.id.grMenuDelete, true);
        menuInflater.setGroupVisible(R.id.grMenuDefault, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == R.id.cancel_action){
            menuInflater.setGroupVisible(R.id.grMenuDelete, false);
            menuInflater.setGroupVisible(R.id.grMenuDefault, true);
            remove_checked_list();
            single_select_item();

        }else if(item_id == R.id.delete_multi){
           for(int i = lvUserList.getChildCount() - 1; i >= 0;i--){
               if(lvUserList.isItemChecked(i)){
                   db.delete_user(userList.get(i).getId());
                   userList.remove(i);
               }
           }

            remove_checked_list();
            userArrrayAdapter.notifyDataSetChanged();
             }

        return super.onOptionsItemSelected(item);
    }

    private void remove_checked_list(){
        for(int i = lvUserList.getChildCount() - 1; i >= 0;i--)
            if(lvUserList.isItemChecked(i)) {
                lvUserList.setItemChecked(i, false);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList.clear();
        Cursor c = db.getListUser();
        initUserList(c);
    }

    private void initUserList(Cursor c){
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String username = c.getString(1);
            String password = c.getString(2);
            int lecture_id = c.getInt(3);
            int role = c.getInt(4);
            User user = new User(id, username,password,role, lecture_id);
            userList.add(user);
        }
        userArrrayAdapter.notifyDataSetChanged();
        c.close();
    }
}