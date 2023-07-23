package com.example.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pojo.Class;
import com.example.pojo.Student;
import com.example.pojo.Subject;
import com.example.pojo.User;

import java.text.ParseException;
import java.util.ArrayList;

public class QLSVDatabase {
    SQLiteDatabase db;
    DBHelper helper;

    public QLSVDatabase(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // User
    public long add_user(User user) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ACCOUNT_USERNAME, user.getUsername());
        values.put(DBHelper.ACCOUNT_PASSWORD, user.getPassword());
        values.put(DBHelper.ACCOUNT_LECTURE, user.getLecture());
        values.put(DBHelper.ACCOUNT_ROLE, user.getRole());
        return db.insert(DBHelper.ACCOUNT_TABLE, null, values);
    }

    public int update_user(User user) {
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

    public int delete_user(int user_id) {

        String clause = DBHelper.ACCOUNT_ID + " = ?";
        String[] args = {Integer.toString(user_id)};

        return db.delete(DBHelper.ACCOUNT_TABLE, clause, args);
    }

    public Cursor get_list() {
        return db.query(DBHelper.ACCOUNT_TABLE, null, null, null, null, null, DBHelper.ACCOUNT_ID + " DESC");
    }

    public Cursor getListBy(String str) {
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_ID + " = ? OR " + DBHelper.ACCOUNT_USERNAME + " like ?;";

        String[] arg = {str, "%" + str + "%"};
        return db.rawQuery(query, arg);
    }

    public Cursor getListUserByRole(int i) {
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_ROLE + " = ?;";

        String[] arg = {Integer.toString(i)};
        return db.rawQuery(query, arg);
    }

    //Subject
    public long add_subject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());

        return db.insert(DBHelper.SUBJECT_TABLE, null, values);
    }

    public int update_subject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_ID, subject.getId());
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());

        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject.getId())};

        return db.update(DBHelper.SUBJECT_TABLE, values, clause, args);
    }

    public int delete_subject(int subject_id) {
        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject_id)};

        return db.delete(DBHelper.SUBJECT_TABLE, clause, args);
    }

    //Class
    public long add_class(Class cl) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_LECTURE, cl.getLecture());
        values.put(DBHelper.CLASS_SUBJECT, cl.getSubject_id());
        values.put(DBHelper.CLASS_QUANTITY, cl.getQuantity());
        values.put(DBHelper.CLASS_YEAR, cl.getYear());
        values.put(DBHelper.CLASS_STARTED, cl.getStarted());

        return db.insert(DBHelper.CLASS_TABLE, null, values);
    }

    public int update_class(Class cl) {
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

    public int delete_class(int class_id) {

        String clause = DBHelper.CLASS_ID + " = ?";
        String[] args = {Integer.toString(class_id)};

        return db.delete(DBHelper.CLASS_TABLE, clause, args);
    }

    //STUDENT

    //thêm một sinh viên
    public long add_student(Student student) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.STUDENT_NAME, student.getName());
        values.put(DBHelper.STUDENT_GENDER, student.isGender());
        values.put(DBHelper.STUDENT_BIRTH, student.getBirth());
        values.put(DBHelper.STUDENT_ADDRESS, student.getAddress());
        values.put(DBHelper.STUDENT_PHONE, student.getPhone());
        values.put(DBHelper.STUDENT_DEPARTMENT, student.getDepartment());
        values.put(DBHelper.STUDENT_YEAR, student.getSchool_year());
        return db.insert(DBHelper.STUDENT_TABLE, null, values);
    }


    //sửa một sinh viên
    public long update_student(Student student) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.STUDENT_NAME, student.getName());
        values.put(DBHelper.STUDENT_GENDER, student.isGender());
        values.put(DBHelper.STUDENT_BIRTH, student.getBirth());
        values.put(DBHelper.STUDENT_ADDRESS, student.getAddress());
        values.put(DBHelper.STUDENT_PHONE, student.getPhone());
        values.put(DBHelper.STUDENT_DEPARTMENT, student.getDepartment());
        values.put(DBHelper.STUDENT_YEAR, student.getSchool_year());

        String clause = DBHelper.STUDENT_ID + " = ?";
        String[] args = {String.valueOf(student.getId())};

        return db.update(DBHelper.STUDENT_TABLE, values, clause, args);
    }


    //xóa một sinh viên
    public long delete_student(Student student) {
        return db.delete(DBHelper.STUDENT_TABLE, DBHelper.STUDENT_ID + " = " + "'" + student.getId() + "'", null);
    }

    //lấy toàn bộ danh sách sinh viên
    public ArrayList<Student> get_list_student() {
        ArrayList<Student> studentsList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, null, null, null, null, DBHelper.STUDENT_ID);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(DBHelper.STUDENT_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.STUDENT_NAME);
                int genderIndex = cursor.getColumnIndex(DBHelper.STUDENT_GENDER);
                int birthIndex = cursor.getColumnIndex(DBHelper.STUDENT_BIRTH);
                int addressIndex = cursor.getColumnIndex(DBHelper.STUDENT_ADDRESS);
                int phoneIndex = cursor.getColumnIndex(DBHelper.STUDENT_PHONE);
                int departmentIndex = cursor.getColumnIndex(DBHelper.STUDENT_DEPARTMENT);
                int schoolYearIndex = cursor.getColumnIndex(DBHelper.STUDENT_YEAR);

                if (idIndex >= 0 && nameIndex >= 0 && genderIndex >= 0 && birthIndex >= 0 &&
                        addressIndex >= 0 && phoneIndex >= 0 && departmentIndex >= 0 && schoolYearIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    boolean gender = cursor.getInt(genderIndex) == 1;
                    String birth = cursor.getString(birthIndex);
                    String address = cursor.getString(addressIndex);
                    String phone = cursor.getString(phoneIndex);
                    String department = cursor.getString(departmentIndex);
                    String schoolYear = cursor.getString(schoolYearIndex);
                    Student student = new Student(name, gender, birth, address, phone, department, schoolYear);
                    student.setId(id);
                    studentsList.add(student);
                }
            }
            cursor.close();
        }
        return studentsList;
    }

    //lấy một sinh viên theo mã sinh viên
    public Student getStudentById(int studentId) {
        String[] columns = {
                DBHelper.STUDENT_ID,
                DBHelper.STUDENT_NAME,
                DBHelper.STUDENT_GENDER,
                DBHelper.STUDENT_BIRTH,
                DBHelper.STUDENT_ADDRESS,
                DBHelper.STUDENT_PHONE,
                DBHelper.STUDENT_DEPARTMENT,
                DBHelper.STUDENT_YEAR
        };

        String selection = DBHelper.STUDENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(studentId) };

        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, columns, selection, selectionArgs, null, null, null);

        Student student = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.STUDENT_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.STUDENT_NAME);
            int genderIndex = cursor.getColumnIndex(DBHelper.STUDENT_GENDER);
            int birthIndex = cursor.getColumnIndex(DBHelper.STUDENT_BIRTH);
            int addressIndex = cursor.getColumnIndex(DBHelper.STUDENT_ADDRESS);
            int phoneIndex = cursor.getColumnIndex(DBHelper.STUDENT_PHONE);
            int departmentIndex = cursor.getColumnIndex(DBHelper.STUDENT_DEPARTMENT);
            int schoolYearIndex = cursor.getColumnIndex(DBHelper.STUDENT_YEAR);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            boolean gender = cursor.getInt(genderIndex) == 1;
            String birth = cursor.getString(birthIndex);
            String address = cursor.getString(addressIndex);
            String phone = cursor.getString(phoneIndex);
            String department = cursor.getString(departmentIndex);
            String schoolYear = cursor.getString(schoolYearIndex);

            student = new Student(name, gender, birth, address, phone, department, schoolYear);
            student.setId(id);

            cursor.close();
        }

        return student;
    }
}

