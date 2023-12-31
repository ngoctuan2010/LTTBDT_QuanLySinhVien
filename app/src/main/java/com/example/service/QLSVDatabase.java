package com.example.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pojo.Class;
import com.example.pojo.Score;
import com.example.pojo.Lecture;
import com.example.pojo.Student;
import com.example.pojo.Subject;
import com.example.pojo.User;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QLSVDatabase {

    SQLiteDatabase db;
    DBHelper helper;
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");

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

    public Cursor getListUser() {
        return db.query(DBHelper.ACCOUNT_TABLE, null, null, null, null, null, null);
    }


    public Cursor getLectureById(int id) {
        String query = "SELECT * " +
                " FROM " + DBHelper.LECTURE_TABLE +
                " WHERE " + DBHelper.LECTURE_ID + " = ?;";

        String[] args = {Integer.toString(id)};
        return db.rawQuery(query, args);
    }
    public Cursor getListLectureBy(String str, int sort){
        String ob = "ASC";
        if(sort == 1){
            ob = "DESC";
        }

        String query = "SELECT * " +
                "FROM " + DBHelper.LECTURE_TABLE +
                " WHERE " + DBHelper.LECTURE_ID + " = ? OR " + DBHelper.LECTURE_NAME + " like ?" +
                "ORDER BY " + DBHelper.LECTURE_NAME + " " + ob + ";";

        String[] arg = {str, "%"+str+"%"};
        return db.rawQuery(query, arg);
    }



    public Cursor checkLogin(String username, String password) {
        String query = "SELECT * " +
                " FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_USERNAME + " = ? AND " + DBHelper.ACCOUNT_PASSWORD + " = ?;";

        String[] arg = {username, password};
        return db.rawQuery(query, arg);
    }

    public Cursor getListBy(String str) {
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_ID + " = ? OR " + DBHelper.ACCOUNT_USERNAME + " like ?;";

        String[] arg = {str, "%" + str + "%"};
        return db.rawQuery(query, arg);
    }

    public Cursor getUserById(int i) {
        String query = "SELECT * FROM " + DBHelper.ACCOUNT_TABLE + " WHERE " + DBHelper.ACCOUNT_ID + " = ?;";

        String[] arg = {Integer.toString(i)};
        return db.rawQuery(query, arg);
    }

    public Cursor getListUserByRole(int i) {
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_ROLE + " = ?;";

        String[] arg = {Integer.toString(i)};
        return db.rawQuery(query, arg);
    }

    public Cursor getUserByUsername(String username) {
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_USERNAME + " = ?;";

        String[] arg = {username};
        return db.rawQuery(query, arg);
    }

    public Cursor getDiffUserByUsername(int id, String username){
        String query = "SELECT * " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " WHERE " + DBHelper.ACCOUNT_USERNAME + " = ? AND " + DBHelper.ACCOUNT_ID + " != ?;";

        String[] arg = {username, Integer.toString(id)};
        return db.rawQuery(query, arg);
    }

    public Cursor AccountQuantityStatistical() {
        String query = "SELECT " + DBHelper.ACCOUNT_ROLE + ", count(" + DBHelper.ACCOUNT_ROLE + ") " +
                "FROM " + DBHelper.ACCOUNT_TABLE +
                " GROUP BY " + DBHelper.ACCOUNT_ROLE + ";";

        return db.rawQuery(query, null);
    }

    public Cursor SubjectInYearStatistical(int subject_id, int year) {
        String query = "SELECT CAST(strftime('%m'," + DBHelper.CLASS_STARTED + ") as INT) as month, count(*) " +
                "FROM " + DBHelper.SUBJECT_TABLE + " as s " +
                " JOIN " + DBHelper.CLASS_TABLE + " as c " +
                "ON s." + DBHelper.SUBJECT_ID + " = c." + DBHelper.CLASS_SUBJECT +
                " WHERE s." + DBHelper.SUBJECT_ID + " = ? AND strftime('%Y', " + DBHelper.CLASS_STARTED + ") = ? " +
                "GROUP BY month;";

        String[] args = {Integer.toString(subject_id), Integer.toString(year)};
        return db.rawQuery(query, args);
    }

    public Cursor LectureDepartmentStatistical() {
        String query = "SELECT " + DBHelper.LECTURE_DEPARTMENT + ", count(" + DBHelper.LECTURE_DEPARTMENT + ") " +
                "FROM " + DBHelper.LECTURE_TABLE +
                " GROUP BY " + DBHelper.LECTURE_DEPARTMENT + ";";
        return db.rawQuery(query, null);
    }

    public Cursor StudentDepartmentStatistical(String year) {
        String query = "SELECT " + DBHelper.STUDENT_DEPARTMENT + ", count(" + DBHelper.STUDENT_DEPARTMENT + ") " +
                "FROM " + DBHelper.STUDENT_TABLE +
                " WHERE " + DBHelper.STUDENT_YEAR + " = ?" +
                " GROUP BY " + DBHelper.STUDENT_DEPARTMENT + ";";

        String[] args = {year};

        return db.rawQuery(query, args);
    }

    public Cursor ClassQuantityInYearStatistical(int year) {
        String query = "SELECT CAST(strftime('%m', " + DBHelper.CLASS_STARTED + ") AS INT) as month, count(*) " +
                "FROM " + DBHelper.CLASS_TABLE +
                " WHERE strftime('%Y', " + DBHelper.CLASS_STARTED + ") = ? " +
                "GROUP BY month;";

        String[] args = {Integer.toString(year)};
        return db.rawQuery(query, args);
    }

    public Cursor ScoreInClassStatistical(int class_id) {
        String avgScoreSQL = "(s.midGrade * sj.midGradePercent + s.finalGrade * sj.finalGradePercent) / (sj.midGradePercent + sj.finalGradePercent)";

        String query = "SELECT " + avgScoreSQL + " as avg, count(" + avgScoreSQL + ") as q " +
                "FROM " + DBHelper.CLASS_TABLE + " as c " +
                "JOIN " + DBHelper.SCORE_TABLE + " as s ON s." + DBHelper.SCORE_CLASS + " = c." + DBHelper.CLASS_ID +
                " JOIN " + DBHelper.SUBJECT_TABLE + " as sj on sj." + DBHelper.SUBJECT_ID + " = c." + DBHelper.CLASS_SUBJECT +
                " WHERE c." + DBHelper.CLASS_ID + " = ? " +
                "GROUP BY avg;";

        String[] args = {Integer.toString(class_id)};
        return db.rawQuery(query, args);
    }

    //Subject
    public long add_subject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());
        values.put(DBHelper.SUBJECT_MIDGRADEPERCENT, subject.getMidGracePercent());
        values.put(DBHelper.SUBJECT_FINALGRADEPERCENT, subject.getFinalGracePercent());

        return db.insert(DBHelper.SUBJECT_TABLE, null, values);
    }

    public int update_subject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBJECT_ID, subject.getId());
        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_CREDIT, subject.getCredit());
        values.put(DBHelper.SUBJECT_MIDGRADEPERCENT, subject.getMidGracePercent());
        values.put(DBHelper.SUBJECT_FINALGRADEPERCENT, subject.getFinalGracePercent());

        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject.getId())};

        return db.update(DBHelper.SUBJECT_TABLE, values, clause, args);
    }

    public int delete_subject(int subject_id) {
        String clause = DBHelper.SUBJECT_ID + " = ?";
        String[] args = {Integer.toString(subject_id)};

        db.setForeignKeyConstraintsEnabled(true);
        return db.delete(DBHelper.SUBJECT_TABLE, clause, args);
    }

    public Cursor get_list_subject() {
        return db.query(DBHelper.SUBJECT_TABLE, null, null, null, null, null, null);
    }

    public Cursor getListSubjectBy(String str, int sort) {
        String ob = "ASC";
        if (sort == 1) {
            ob = "DESC";
        }

        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_ID + " = ? OR " + DBHelper.SUBJECT_NAME + " like ?" +
                "ORDER BY " + DBHelper.SUBJECT_NAME + " " + ob + ";";

        String[] arg = {str, "%" + str + "%"};
        return db.rawQuery(query, arg);
    }

    public Cursor getListSubjectById(int subject_id) {
        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_ID + " = ?;";
        String[] arg = {Integer.toString(subject_id)};
        return db.rawQuery(query, arg);
    }

    public Cursor getSubjectByName(String name) {
        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_NAME + " = ?;";
        String[] arg = {name};
        return db.rawQuery(query, arg);
    }

    public Cursor getDiffSubjectByName(int id, String name){
        String query = "SELECT * " +
                "FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_NAME + " = ? AND " + DBHelper.SUBJECT_ID + " != ?";
        String[] arg = {name, Integer.toString(id)};
        return db.rawQuery(query, arg);
    }

    public int countSubject() {
        String query = "SELECT COUNT(*) " +
                "FROM " + DBHelper.SUBJECT_TABLE + ";";
        return db.rawQuery(query, null).getInt(0);

    }

    //Class
    public long add_class(Class cl) throws ParseException {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_LECTURE, cl.getLecture());
        values.put(DBHelper.CLASS_NAME, cl.getName());
        values.put(DBHelper.CLASS_SUBJECT, cl.getSubject_id());
        values.put(DBHelper.CLASS_QUANTITY, cl.getQuantity());
        values.put(DBHelper.CLASS_YEAR, cl.getYear());
        values.put(DBHelper.CLASS_STARTED, DateFormatter.reformat(cl.getStarted(), "dd/MM/yyyy", "yyyy-MM-dd"));
        values.put(DBHelper.CLASS_STATUS, cl.getStatus());

        return db.insert(DBHelper.CLASS_TABLE, null, values);
    }

    public int update_class(Class cl) throws ParseException {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_ID, cl.getId());
        values.put(DBHelper.CLASS_NAME, cl.getName());
        values.put(DBHelper.CLASS_LECTURE, cl.getLecture());
        values.put(DBHelper.CLASS_SUBJECT, cl.getSubject_id());
        values.put(DBHelper.CLASS_QUANTITY, cl.getQuantity());
        values.put(DBHelper.CLASS_YEAR, cl.getYear());
        values.put(DBHelper.CLASS_STARTED, DateFormatter.reformat(cl.getStarted(), "dd/MM/yyyy", "yyyy-MM-dd"));
        values.put(DBHelper.CLASS_STATUS, cl.getStatus());

        String clause = DBHelper.CLASS_ID + " = ?";
        String[] args = {Integer.toString(cl.getId())};

        return db.update(DBHelper.CLASS_TABLE, values, clause, args);
    }

    public int delete_class(int class_id) {
        String clause = DBHelper.CLASS_ID + " = ?";
        String[] args = {Integer.toString(class_id)};

        return db.delete(DBHelper.CLASS_TABLE, clause, args);
    }

    //Lecture
    public long add_lecture(Lecture lecture) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.LECTURE_NAME, lecture.getName());
        values.put(DBHelper.LECTURE_GENDER, lecture.isGender());
        values.put(DBHelper.LECTURE_BIRTH, lecture.getBirth());
        values.put(DBHelper.LECTURE_ADDRESS, lecture.getAddress());
        values.put(DBHelper.LECTURE_PHONE, lecture.getPhone());
        values.put(DBHelper.LECTURE_DEPARTMENT, lecture.getDepartment());

        return db.insert(DBHelper.LECTURE_TABLE, null, values);
    }

    public Cursor get_list_class() {
        return db.query(DBHelper.CLASS_TABLE, null, null, null, null, null, null);
    }

    public Cursor get_list_class_by_lectureId (int idLecture) {
        String query = "SELECT * " +
                " FROM " + DBHelper.CLASS_TABLE +
                " WHERE " + DBHelper.CLASS_LECTURE + " = ?;";

        String[] args = {Integer.toString(idLecture)};
        return db.rawQuery(query, args);
    }

    public Cursor get_list_class_by_lecture_active(int lecture_id){
        String query = "SELECT * " +
                        "FROM " + DBHelper.CLASS_TABLE +
                        " WHERE " + DBHelper.CLASS_STATUS + " = 0 AND " + DBHelper.CLASS_LECTURE + " = ?";
        String[] args = {Integer.toString(lecture_id)};

        return db.rawQuery(query, args);
    }

    public Cursor getDiffClassBySubjectAndName(String name, int class_id) {
        String query = "SELECT * " +
                " FROM " + DBHelper.CLASS_TABLE +
                " WHERE " + DBHelper.CLASS_NAME + " = ? AND " + DBHelper.CLASS_ID + " != ?";

        String[] args = {name, Integer.toString(class_id)};
        return db.rawQuery(query, args);
    }

    public long add_student_to_class(int student_id, int class_id) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCORE_STUDENT, student_id);
        values.put(DBHelper.SCORE_CLASS, class_id);

        return db.insert(DBHelper.SCORE_TABLE, null, values);
    }

    public long delete_student_from_class(int student_id, int class_id) {
        String clause = DBHelper.SCORE_STUDENT + " = ? AND " + DBHelper.SCORE_CLASS + " = ?";
        String[] args = {Integer.toString(student_id), Integer.toString(class_id)};
        return db.delete(DBHelper.SCORE_TABLE, clause, args);
    }

    public boolean is_student_in_class(int student_id, int class_id) {
        String query = "SELECT * " +
                " FROM " + DBHelper.SCORE_TABLE +
                " WHERE " + DBHelper.SCORE_STUDENT + " = ? AND " + DBHelper.SCORE_CLASS + " = ?";

        String[] args = {Integer.toString(student_id), Integer.toString(class_id)};
        Cursor c = db.rawQuery(query, args);
        return c.getCount() > 0;
    }

    public Cursor find_student_in_class_by(String param, int class_id) {
        String query = "SELECT * " +
                " FROM " + DBHelper.STUDENT_TABLE +
                " WHERE (" + DBHelper.STUDENT_ID + " = ? OR " + DBHelper.STUDENT_NAME + " like ?) AND " +
                DBHelper.STUDENT_ID + " in (SELECT " + DBHelper.SCORE_STUDENT +
                " FROM " + DBHelper.SCORE_TABLE +
                " WHERE " + DBHelper.SCORE_CLASS + " = ?)";

        String[] args = {param, "%" + param + "%", Integer.toString(class_id)};

        return db.rawQuery(query, args);
    }

    public Cursor getListClassByYear(String year) {
        String query = "SELECT * " +
                " FROM " + DBHelper.CLASS_TABLE +
                " WHERE " + DBHelper.CLASS_YEAR + " = ?";

        String[] args = {year};
        return db.rawQuery(query, args);
    }

    public Cursor getListLecture() {
        return db.query(DBHelper.LECTURE_TABLE, null, null, null, null, null, DBHelper.LECTURE_ID + " DESC");
    }


    public Cursor getListQuantityLectureByDepartment() {
        String query = "SELECT COUNT(" + DBHelper.LECTURE_DEPARTMENT + ") " +
                "FROM " + DBHelper.LECTURE_TABLE +
                " GROUP BY " + DBHelper.LECTURE_DEPARTMENT + ";";
        return db.rawQuery(query, null);
    }

    public Cursor get_list_class_by(String str) {
        String query = "SELECT * " +
                " FROM " + DBHelper.CLASS_TABLE + " as c " +
                "  JOIN " + DBHelper.SUBJECT_TABLE + " as s " +
                "ON c." + DBHelper.CLASS_SUBJECT + " = s." + DBHelper.SUBJECT_ID +
                "  JOIN " + DBHelper.LECTURE_TABLE + " as l " +
                "ON c." + DBHelper.CLASS_LECTURE + " = l." + DBHelper.LECTURE_ID +
                " WHERE c." + DBHelper.CLASS_ID + " = ? OR " +
                "c." + DBHelper.CLASS_NAME + " like ? OR " +
                "s." + DBHelper.SUBJECT_NAME + " like ? OR " +
                "l." + DBHelper.LECTURE_NAME + " like ?;";
        String[] args = {str, "%" + str + "%", "%" + str + "%", "%" + str + "%"};
        return db.rawQuery(query, args);
    }

    public Cursor getListStudentByClass(int class_id) {
        String query = "SELECT * " +
                "FROM " + DBHelper.STUDENT_TABLE +
                " WHERE " + DBHelper.STUDENT_ID + " in (SELECT " + DBHelper.SCORE_STUDENT +
                " FROM " + DBHelper.SCORE_TABLE +
                " WHERE " + DBHelper.SCORE_CLASS + " = ?)";
        String[] arg = {Integer.toString(class_id)};
        return db.rawQuery(query, arg);
    }
    //STUDENT

    //thêm một sinh viên
    public long add_student(Student student) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.STUDENT_NAME, student.getName());
        values.put(DBHelper.STUDENT_GENDER, student.isGender());
        values.put(DBHelper.STUDENT_BIRTH, spdf.format(student.getBirth()));
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
        values.put(DBHelper.STUDENT_BIRTH, spdf.format(student.getBirth()));
        values.put(DBHelper.STUDENT_ADDRESS, student.getAddress());
        values.put(DBHelper.STUDENT_PHONE, student.getPhone());
        values.put(DBHelper.STUDENT_DEPARTMENT, student.getDepartment());
        values.put(DBHelper.STUDENT_YEAR, student.getSchool_year());
        String clause = DBHelper.STUDENT_ID + " = ?";
        String[] args = {String.valueOf(student.getId())};
        return db.update(DBHelper.STUDENT_TABLE, values, clause, args);
    }

    public int update_lecture(Lecture lecture) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.LECTURE_ID, lecture.getId());
        values.put(DBHelper.LECTURE_NAME, lecture.getName());
        values.put(DBHelper.LECTURE_GENDER, lecture.isGender());
        values.put(DBHelper.LECTURE_BIRTH, lecture.getBirth());
        values.put(DBHelper.LECTURE_ADDRESS, lecture.getAddress());
        values.put(DBHelper.LECTURE_PHONE, lecture.getPhone());
        values.put(DBHelper.LECTURE_DEPARTMENT, lecture.getDepartment());

        String clause = DBHelper.LECTURE_ID + " = ?";
        String[] arg = {Integer.toString(lecture.getId())};

        return db.update(DBHelper.LECTURE_TABLE, values, clause, arg);
    }

    public int delete_lecture(int lecture_id) {

        String clause = DBHelper.LECTURE_ID + " = ?";
        String[] args = {Integer.toString(lecture_id)};

        db.setForeignKeyConstraintsEnabled(true);
        return db.delete(DBHelper.LECTURE_TABLE, clause, args);
    }

    //xóa một sinh viên
    public long delete_student(Student student) {
        return db.delete(DBHelper.STUDENT_TABLE, DBHelper.STUDENT_ID + " = " + "'" + student.getId() + "'", null);
    }

    //lấy toàn bộ danh sách sinh viên
    public ArrayList<Student> get_list_students() throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, null, null, null, null, DBHelper.STUDENT_ID);
        return getListStudentByCursor(cursor);
    }

    //lấy một sinh viên theo mã sinh viên
    public Student getStudentById(int studentId) throws ParseException {
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
        String[] selectionArgs = {String.valueOf(studentId)};

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

    // lấy danh sách sinh viên theo tên sinh viên
    public ArrayList<Student> get_list_students_by_name(String name) throws ParseException {
        ArrayList<Student> listStudent = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.STUDENT_TABLE + " WHERE " + DBHelper.STUDENT_NAME + " LIKE ?;";
        String[] args = new String[]{"%" + name + "%"};
        Cursor cursor = db.rawQuery(query, args);
        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo giới tính sinh viên
    public ArrayList<Student> get_list_students_by_gender(boolean gender) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        int genderValue = gender ? 1 : 0;

        String selection = DBHelper.STUDENT_GENDER + " = ?";
        String[] selectionArgs = {String.valueOf(genderValue)};

        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, selection, selectionArgs, null, null, DBHelper.STUDENT_ID);

        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo ngày sinh sinh viên
    public ArrayList<Student> get_list_students_by_birth(String birth) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, DBHelper.STUDENT_BIRTH + " LIKE ?", new String[]{"%" + birth + "%"}, null, null, DBHelper.STUDENT_ID);
        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo địa chỉ sinh viên
    public ArrayList<Student> get_list_students_by_address(String address) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, DBHelper.STUDENT_ADDRESS + " LIKE ?", new String[]{"%" + address + "%"}, null, null, DBHelper.STUDENT_ID);
        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo số điện thoại
    public ArrayList<Student> get__list_students_by_phone(String phoneNumber) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.STUDENT_TABLE, null, DBHelper.STUDENT_PHONE + " LIKE ?", new String[]{"%" + phoneNumber + "%"}, null, null, DBHelper.STUDENT_ID);
        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo khoa
    public ArrayList<Student> get_list_students_by_department(String department) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.STUDENT_TABLE + " WHERE " + DBHelper.STUDENT_DEPARTMENT + " LIKE ?;";
        String[] args = {"%" + department + "%"};

        Cursor cursor = db.rawQuery(query, args);

        return getListStudentByCursor(cursor);
    }


    // lấy danh sách sinh viên theo niên khóa
    public ArrayList<Student> get_list_students_by_schoolyear(String schoolYear) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.STUDENT_TABLE + " WHERE " + DBHelper.STUDENT_YEAR + " LIKE ?;";
        String[] args = {"%" + schoolYear + "%"};

        Cursor cursor = db.rawQuery(query, args);

        return getListStudentByCursor(cursor);
    }

    // lấy danh sách sinh viên theo mã sinh viên(các sinh viên có mã tương đối giống nhau)
    public ArrayList<Student> get_list_students_by_id(String studentId) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.STUDENT_TABLE + " WHERE " + DBHelper.STUDENT_ID + " LIKE ?;";
        String[] args = {"%" + studentId + "%"};

        Cursor cursor = db.rawQuery(query, args);

        return getListStudentByCursor(cursor);
    }

    //lấy bảng điểm theo mã sinh viên và mã lớp
    public Score getScoreByClassAndStudent(int classId, int studentId) {
        Score score = null;

        String query = "SELECT * FROM " + DBHelper.SCORE_TABLE +
                " WHERE " + DBHelper.SCORE_CLASS + " = ? AND " + DBHelper.SCORE_STUDENT + " = ?;";

        String[] args = {String.valueOf(classId), String.valueOf(studentId)};

        Cursor cursor = db.rawQuery(query, args);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.SCORE_ID);
            int studentIdIndex = cursor.getColumnIndex(DBHelper.SCORE_STUDENT);
            int classIdIndex = cursor.getColumnIndex(DBHelper.SCORE_CLASS);
            int midGradeIndex = cursor.getColumnIndex(DBHelper.SCORE_MIDGRADE);
            int finalGradeIndex = cursor.getColumnIndex(DBHelper.SCORE_FINALGRADE);

            if (idIndex >= 0 && studentIdIndex >= 0 && classIdIndex >= 0 && midGradeIndex >= 0 && finalGradeIndex >= 0) {
                int id = cursor.getInt(idIndex);
                Double midGrade = Double.valueOf(cursor.getFloat(midGradeIndex));
                Double finalGrade = Double.valueOf(cursor.getFloat(finalGradeIndex));
                if (cursor.getString(midGradeIndex) == null && cursor.getString(finalGradeIndex) == null) {
                    midGrade = -1.0;
                    finalGrade = -1.0;
                }
                score = new Score(id, studentId, classId, midGrade, finalGrade);
            }
            cursor.close();
        }
        return score;
    }

    //lấy môn học theo mã lớp
    public Subject getSubjectByClass(int classId) {
        Subject subject = null;

        String query = "SELECT * FROM " + DBHelper.SUBJECT_TABLE +
                " WHERE " + DBHelper.SUBJECT_ID + " IN (SELECT " + DBHelper.CLASS_SUBJECT + " FROM " + DBHelper.CLASS_TABLE +
                " WHERE " + DBHelper.CLASS_ID + " = ?);";

        String[] args = {String.valueOf(classId)};

        Cursor cursor = db.rawQuery(query, args);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.SUBJECT_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.SUBJECT_NAME);
            int creditIndex = cursor.getColumnIndex(DBHelper.SUBJECT_CREDIT);
            int midGracePercentIndex = cursor.getColumnIndex(DBHelper.SUBJECT_MIDGRADEPERCENT);
            int finalGracePercentIndex = cursor.getColumnIndex(DBHelper.SUBJECT_FINALGRADEPERCENT);

            if (idIndex >= 0 && nameIndex >= 0 && creditIndex >= 0 && midGracePercentIndex >= 0 && finalGracePercentIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                int credit = cursor.getInt(creditIndex);
                float midGracePercent = cursor.getFloat(midGracePercentIndex);
                float finalGracePercent = cursor.getFloat(finalGracePercentIndex);

                subject = new Subject(id, name, credit, midGracePercent, finalGracePercent);
            }

            cursor.close();
        }
        return subject;
    }

    public long updateScore(Score score) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCORE_ID, score.getId());
        values.put(DBHelper.SCORE_STUDENT, score.getIdStudent());
        values.put(DBHelper.SCORE_CLASS, score.getIdClass());
        values.put(DBHelper.SCORE_MIDGRADE, score.getMidScore());
        values.put(DBHelper.SCORE_FINALGRADE, score.getEndScore());
        String clause = DBHelper.SCORE_ID + " = ?";
        String[] args = {Integer.toString(score.getId())};

        return db.update(DBHelper.SCORE_TABLE, values, clause, args);
    }

    public ArrayList<Student> getListStudentByCursor(Cursor cursor) throws ParseException {
        ArrayList<Student> studentsList = new ArrayList<>();
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
}


