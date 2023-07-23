package com.example.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pojo.Class;
import com.example.pojo.Subject;
import com.example.pojo.User;

public class QLSVDatabase {
    SQLiteDatabase db;
    DBHelper helper;

    public QLSVDatabase(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // User
    public long add_user(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ACCOUNT_USERNAME, user.getUsername());
        values.put(DBHelper.ACCOUNT_PASSWORD, user.getPassword());
        values.put(DBHelper.ACCOUNT_LECTURE, user.getLecture());
        values.put(DBHelper.ACCOUNT_ROLE, user.getRole());
        return db.insert(DBHelper.ACCOUNT_TABLE, null, values);
    }

    public int update_user(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ACCOUNT_ID, user.getId());
        values.put(DBHelper.ACCOUNT_USERNAME, user.getUsername());
        values.put(DBHelper.ACCOUNT_PASSWORD, user.getPassword());
        values.put(DBHelper.ACCOUNT_LECTURE, user.getLecture());
        values.put(DBHelper.ACCOUNT_ROLE, user.getRole());

        String clause = DBHelper.ACCOUNT_ID + " = ?";
        String[] args = {Integer.toString(user.getId())};

        return db.update(DBHelper.ACCOUNT_TABLE, values, clause, args);
    }

    public int delete_user(int user_id){

        String clause = DBHelper.ACCOUNT_ID + " = ?";
        String[] args = {Integer.toString(user_id)};

        return db.delete(DBHelper.ACCOUNT_TABLE, clause, args);
    }

    public Cursor get_list_user(){
        return db.query(DBHelper.ACCOUNT_TABLE, null, null,null,null,null,null );
    }

    public Cursor getListBy(String str){
        String query = "SELECT * " +
                        "FROM " + DBHelper.ACCOUNT_TABLE +
                        " WHERE " + DBHelper.ACCOUNT_ID + " = ? OR " + DBHelper.ACCOUNT_USERNAME + " like ?;";

        String[] arg = {str, "%"+str+"%"};
        return db.rawQuery(query, arg);
    }

    public Cursor getListUserByRole(int i){
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_ROLE + " = ?;";

        String[] arg = {Integer.toString(i)};
        return db.rawQuery(query, arg);
    }


    //Subject
    public long add_subject(Subject subject){
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());

        return db.insert(DBHelper.SUBJECT_TABLE, null, values);
    }

    public int update_subject(Subject subject){
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_ID, subject.getId());
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());

        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject.getId())};

        return db.update(DBHelper.SUBJECT_TABLE, values, clause, args);
    }

    public int delete_subject(int subject_id){
        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject_id)};

        return db.delete(DBHelper.SUBJECT_TABLE, clause, args);
    }

    public Cursor get_list_subject(){
        return db.query(DBHelper.SUBJECT_TABLE, null, null, null, null, null, null);
    }

    public Cursor getListSubjectBy(String str, int sort){
        String ob = "ASC";
        if(sort == 1){
            ob = "DESC";
        }

        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_ID + " = ? OR " + DBHelper.SUBJECT_NAME + " like ?" +
                "ORDER BY " + DBHelper.SUBJECT_NAME + " " + ob + ";";

        String[] arg = {str, "%"+str+"%"};
        return db.rawQuery(query, arg);
    }

    public Cursor getListSubjectById(int subject_id){
        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_ID + " = ?;";
        String[] arg = {Integer.toString(subject_id)};
        return db.rawQuery(query, arg);
    }

    //Class
    public long add_class(Class cl){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_LECTURE, cl.getLecture());
        values.put(DBHelper.CLASS_NAME, cl.getName());
        values.put(DBHelper.CLASS_SUBJECT, cl.getSubject_id());
        values.put(DBHelper.CLASS_QUANTITY, cl.getQuantity());
        values.put(DBHelper.CLASS_YEAR, cl.getYear());
        values.put(DBHelper.CLASS_STARTED, cl.getStarted());

        return db.insert(DBHelper.CLASS_TABLE, null, values);
    }

    public int update_class(Class cl){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_ID, cl.getId());
        values.put(DBHelper.CLASS_LECTURE, cl.getLecture());
        values.put(DBHelper.CLASS_SUBJECT, cl.getSubject_id());
        values.put(DBHelper.CLASS_QUANTITY, cl.getQuantity());
        values.put(DBHelper.CLASS_YEAR, cl.getYear());
        values.put(DBHelper.CLASS_STARTED, cl.getStarted());

        String clause = DBHelper.CLASS_ID + " = ?";
        String[] args = {Integer.toString(cl.getId())};

        return db.update(DBHelper.CLASS_TABLE, values, clause, args);
    }

    public int delete_class(int class_id){
        String clause = DBHelper.CLASS_ID + " = ?";
        String[] args = {Integer.toString(class_id)};

        return db.delete(DBHelper.CLASS_TABLE, clause,args);
    }

    public Cursor get_list_class(){
        return db.query(DBHelper.CLASS_TABLE, null, null, null, null, null, null);
    }

    public Cursor get_list_class_by(String str){
        String query = "SELECT * " +
                    " FROM " + DBHelper.CLASS_TABLE + " as c " +
                    "  JOIN " + DBHelper.SUBJECT_TABLE + " as s " +
                    "ON c." + DBHelper.CLASS_SUBJECT + " = s." + DBHelper.SUBJECT_ID +
                    "  JOIN " + DBHelper.LECTURE_TABLE + " as l " +
                    "ON c." + DBHelper.CLASS_LECTURE + " = l." + DBHelper.LECTURE_ID +
                    " WHERE c." + DBHelper.CLASS_ID + " = ? OR " +
                            "s." + DBHelper.SUBJECT_NAME + " like ? OR " +
                            "l." + DBHelper.LECTURE_FNAME + " = ? OR " +
                            "l." + DBHelper.LECTURE_LNAME + " = ?;";

        String[] args = {str, "%"+str+"%"};
        return db.rawQuery(query, args);
    }

    //...


}