package com.example.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "QuanLySinhVien";

    //Student
    public static final String STUDENT_TABLE = "student";
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_FNAME = "first_name";
    public static final String STUDENT_LNAME = "last_name";
    public static final String STUDENT_GENDER = "gender";
    public static final String STUDENT_BIRTH = "birthday";
    public static final String STUDENT_ADDRESS = "address";
    public static final String STUDENT_PHONE = "phone";
    public static final String STUDENT_DEPARTMENT = "department";
    public static final String STUDENT_YEAR = "school_year";
    public static final String CREATE_STUDENT = "create table " + STUDENT_TABLE + "( "
                                                + STUDENT_ID + " integer primary key autoincrement, "
                                                + STUDENT_FNAME + " nvarchar(20) not null, "
                                                + STUDENT_LNAME + " nvarchar(20) not null, "
                                                + STUDENT_GENDER + " bit, "
                                                + STUDENT_BIRTH + " date, "
                                                + STUDENT_ADDRESS + " text, "
                                                + STUDENT_PHONE + " nvarchar(11), "
                                                + STUDENT_DEPARTMENT + " nvarchar(20), "
                                                + STUDENT_YEAR + " nvarchar(20));";

    //Subject
    public static final String SUBJECT_TABLE = "subject";
    public static final String SUBJECT_ID = "id";
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_CREDIT = "credit";
    public static final String CREATE_SUBJECT = "create table " + SUBJECT_TABLE + "( "
                                                + SUBJECT_ID + " integer primary key autoincrement, "
                                                + SUBJECT_NAME + " nvarchar(20), "
                                                + SUBJECT_CREDIT + " integer);";

    //LECTURE
    public static final String LECTURE_TABLE = "lecture";
    public static final String LECTURE_ID = "id";
    public static final String LECTURE_FNAME = "first_name";
    public static final String LECTURE_LNAME = "last_name";
    public static final String LECTURE_GENDER = "gender";
    public static final String LECTURE_BIRTH = "birthday";
    public static final String LECTURE_ADDRESS = "address";
    public static final String LECTURE_PHONE = "phone";
    public static final String LECTURE_DEPARTMENT = "department";
    public static final String CREATE_LECTURE = "create table " + LECTURE_TABLE + "( "
                                                + LECTURE_ID + " integer primary key autoincrement, "
                                                + LECTURE_FNAME + " nvarchar(20) not null, "
                                                + LECTURE_LNAME + " nvarchar(20) not null, "
                                                + LECTURE_GENDER + " bit, "
                                                + LECTURE_BIRTH + " date, "
                                                + LECTURE_ADDRESS + " text, "
                                                + LECTURE_PHONE + " nvarchar(11), "
                                                + LECTURE_DEPARTMENT + " nvarchar(20));";

    //Class
    public static final String CLASS_TABLE = "class";
    public static final String CLASS_ID = "id";
    public static final String CLASS_NAME = "name";
    public static final String CLASS_SUBJECT = "subject_id";
    public static final String CLASS_LECTURE = "lecture_id";
    public static final String CLASS_QUANTITY = "quantity";
    public static final String CLASS_YEAR = "year";
    public static final String CLASS_STARTED = "started_date";

    public static final String CREATE_CLASS = "create table " + CLASS_TABLE + "( "
                                            + CLASS_ID + " integer primary key autoincrement, "
                                            + CLASS_NAME + " nvarchar(50), "
                                            + CLASS_SUBJECT + " integer, "
                                            + CLASS_LECTURE + " integer, "
                                            + CLASS_QUANTITY + " integer, "
                                            + CLASS_YEAR + " nvarchar(10), "
                                            + CLASS_STARTED + " date, "
                                            + "FOREIGN KEY(" + CLASS_SUBJECT + ") REFERENCES " + SUBJECT_TABLE + "(" + SUBJECT_ID + "),"
                                            + "FOREIGN KEY(" + CLASS_LECTURE + ") REFERENCES " + LECTURE_TABLE + "(" + LECTURE_ID + "));";



    //Account
    public static final String ACCOUNT_TABLE = "account";
    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_USERNAME = "username";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_LECTURE = "lecture_id";
    public static final String ACCOUNT_ROLE = "role";
    public static final String CREATE_ACCOUNT = "create table " + ACCOUNT_TABLE + "( "
                                                + ACCOUNT_ID + " integer primary key autoincrement, "
                                                + ACCOUNT_USERNAME + " text not null, "
                                                + ACCOUNT_PASSWORD + " text not null, "
                                                + ACCOUNT_LECTURE + " integer, "
                                                + ACCOUNT_ROLE + " integer, "
                                                + " FOREIGN KEY (" + ACCOUNT_LECTURE + ") REFERENCES " + LECTURE_TABLE + "(" + LECTURE_ID + "));";



    //Score Board
    public static final String SCORE_TABLE = "score";
    public static final String SCORE_ID = "id";
    public static final String SCORE_STUDENT = "student_id";
    public static final String SCORE_CLASS = "class_id";
    public static final String SCORE_POINT = "point";
    public static final String CREATE_SCORE = "create table " + SCORE_TABLE + "("
                                            + SCORE_ID + " integer primary key autoincrement, "
                                            + SCORE_STUDENT + " integer, "
                                            + SCORE_CLASS + " integer, "
                                            + SCORE_POINT + " float, "
                                            + "FOREIGN KEY (" + SCORE_STUDENT + ") REFERENCES " + STUDENT_TABLE + "(" + STUDENT_ID + "), "
                                            + "FOREIGN KEY (" + SCORE_CLASS + ") REFERENCES " + CLASS_TABLE + "(" + CLASS_ID + "));";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_SUBJECT);
        db.execSQL(CREATE_LECTURE);
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
