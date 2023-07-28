package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.Class;
import com.example.pojo.Student;
import com.example.pojo.Subject;
import com.example.pojo.User;
import com.example.service.QLSVDatabase;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStatistical extends AppCompatActivity {

    TextView tvUserTotal, tvUser, tvAdmin, tvLectureTotal, tvStudentTotal, tvStudentInYear, tvSubjectTotal, tvClassTotal, tvClassYear;

    EditText edtSubjectYear, edtClassYear, edtStudentYear;

    AutoCompleteTextView acClassScore;

    Button btnSubjectRun, btnClassRun;
    Spinner spSubject;

    PieChart chartUser, chartLecture, chartStudent;

    LineChart chartSubject;


    BarChart chartClass, chartScore;

    QLSVDatabase db;

    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Class> classes = new ArrayList<>();
    ArrayAdapter subjectAdapter;
    ArrayAdapter classAdaper;

    int score_class_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statictical);
        db = new QLSVDatabase(this);
        int now_year = Calendar.getInstance().get(Calendar.YEAR);
        mappingView();
        userStatistical();
        lectureStatiscital();
        lectureStatisticalChart();
        try {
            studentStatistical();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        studentStatisticalChart("2022-2023");
        edtStudentYear.setText("2022-2023");

        subjectStatistical();
        classStatistical();


        edtClassYear.setText(Integer.toString(now_year));
        classStatisticalChart(now_year);

        subjectAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, subjects);
        spSubject.setAdapter(subjectAdapter);
        Cursor c1 = db.get_list_subject();
        if(c1.getCount() > 0){
            initSubjectList(c1);
            spSubject.setSelection(0);
            int first_subject = ((Subject) spSubject.getSelectedItem()).getId();
            subjectStatisticalChart(first_subject,now_year);
            edtSubjectYear.setText(Integer.toString(now_year));
        }

        btnSubjectRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject subject = (Subject) spSubject.getSelectedItem();
                int subject_id = subject.getId();
                String year = edtSubjectYear.getText().toString();
                if(year != null && !year.isEmpty()){
                    subjectStatisticalChart(subject_id, Integer.parseInt(year));
                }
            }
        });

        btnClassRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = edtClassYear.getText().toString();
                if(year != null && !year.isEmpty()){
                    classStatisticalChart(Integer.parseInt(year));
                }
            }
        });

        classAdaper = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, classes);
        acClassScore.setAdapter(classAdaper);
        Cursor c2 = db.get_list_class();
        initClassList(c2);

        acClassScore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                score_class_id = ((Class) parent.getItemAtPosition(position)).getId();
                scoreStatisticalChart(score_class_id);
            }
        });
        acClassScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                score_class_id = -1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void mappingView(){
        // User Statistical
        tvUserTotal = (TextView) findViewById(R.id.tvASUserTotal);
        tvUser = (TextView) findViewById(R.id.tvASUser);
        tvAdmin = (TextView) findViewById(R.id.tvASAdmin);
        chartUser = (PieChart) findViewById(R.id.pcUserStatistical);

        // Lecture Statistical
        chartLecture = (PieChart) findViewById(R.id.chartLecture);
        tvLectureTotal = (TextView) findViewById(R.id.tvASLectuireTotal);

        // Student Statistical
        chartStudent = (PieChart) findViewById(R.id.chartStudent);
        tvStudentTotal = (TextView) findViewById(R.id.tvASStudentTotal);
        tvStudentInYear = (TextView) findViewById(R.id.tvASStudentInYear);
        edtStudentYear = (EditText) findViewById(R.id.edtASStudentYear);

        //Subject Statistical
        tvSubjectTotal = (TextView) findViewById(R.id.tvASTotalSubject);
        edtSubjectYear = (EditText) findViewById(R.id.edtASSubjectYear);
        btnSubjectRun = (Button) findViewById(R.id.btnASSubjectRun);
        spSubject = (Spinner) findViewById(R.id.spASSubject);
        chartSubject = (LineChart) findViewById(R.id.chartSubject);

        //Class Statistical
        chartClass = (BarChart) findViewById(R.id.chartClass);
        edtClassYear = (EditText) findViewById(R.id.edtASClassYear);
        btnClassRun = (Button) findViewById(R.id.btnASClassRun);
        tvClassTotal = (TextView) findViewById(R.id.tvASClassTotal);
        tvClassYear = (TextView) findViewById(R.id.tvASClassYear);

        //Score Statistical
        chartScore = (BarChart) findViewById(R.id.chartScore);
        acClassScore = (AutoCompleteTextView) findViewById(R.id.acASScoreClass);
    }

    private void initSubjectList(Cursor c){
        subjects.clear();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int credit = c.getInt(2);
            Subject subject = new Subject(id, name, credit);
            subjects.add(subject);
        }
        subjectAdapter.notifyDataSetChanged();
        c.close();
    }

    private void initClassList(Cursor c){
        classes.clear();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int subject_id = c.getInt(2);
            Class _class = new Class(id, name, subject_id);
            classes.add(_class);
        }
        classAdaper.notifyDataSetChanged();
        c.close();
    }

    private Map<String, Integer> getDataByDepartment(Cursor c){
        Map<String, Integer> map = new HashMap<>();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            String key = c.getString(0);
            int value = c.getInt(1);
            if(key != null) {
                map.put(key, value);
            }
        }
        c.close();
        return map;
    }

    private Map<Integer, Integer> getDataByNumber(Cursor c){
        Map<Integer, Integer> map = new HashMap<>();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            int valueX = c.getInt(0);
            int valueY = c.getInt(1);
            map.put(valueX, valueY);
        }
        c.close();
        return map;
    }

    private void setUpPieChart(PieChart chart){
        chart.setDrawHoleEnabled(false);
        chart.setEntryLabelTextSize(10);
        chart.setEntryLabelColor(Color.BLACK);
        chart.getDescription().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        chart.animateY(1400, Easing.EaseInOutBounce);

    }

    private void drawPieChart(PieChart chart, Map<String, Integer> datas){
        List<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String, Integer> data : datas.entrySet()){
            entries.add(new PieEntry(data.getValue(), data.getKey()));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS)
            colors.add(color);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(10f);

        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.invalidate();
    }

    private void setUpLineChart(LineChart chart){
        // X
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);
        xAxis.setXOffset(1f);
        xAxis.setLabelCount(25);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);

        //Y Left
        YAxis leftyAxis = chart.getAxisLeft();
        leftyAxis.setAxisMinimum(0);
        leftyAxis.setGranularity(1f);

        //Y Right
        chart.getAxisRight().setEnabled(false);

        //Description
        chart.getDescription().setEnabled(false);

        chart.animateY(1200, Easing.EaseInOutBounce);
    }

    private void drawLineChart(LineChart chart, Map<Integer, Integer> datas){
        List<Entry> entries = new ArrayList<Entry>();

        for(int i = 1; i <= 12;i++){
            if(datas.containsKey(i)){
                entries.add(new Entry(i, datas.get(i)));
            }else{
                entries.add(new Entry(i, 0));
            }
        }


        LineDataSet dataSet = new LineDataSet(entries, "");

        String labelStr = spSubject.getSelectedItem().toString();
        if(labelStr != null && !labelStr.isEmpty())
            dataSet.setLabel(labelStr);

        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }

    private void setUpBarChart(BarChart chart){
        chart.getDescription().setEnabled(false);

        //X
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(13);

        //Y
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setGranularity(1f);
        chart.getAxisRight().setEnabled(false);
    }

    private void drawBarChart(BarChart chart, Map<Integer, Integer> datas, String label){
        List<BarEntry> entries = new ArrayList<>();
        for(int i = 1; i <= 12;i++){
            if(datas.containsKey(i)){
                entries.add(new BarEntry(i, datas.get(i)));
            }else{
                entries.add(new BarEntry(i, 0));
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, label);
        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }

    //User func
    private void userStatistical(){
        Cursor c = db.AccountQuantityStatistical();
        if(c.getCount() > 0){
            Map<String, Integer> statistical = getDataAccount(c);

            // Data
            int qUser = statistical.get(User.ROLE.values()[0].toString());
            tvUser.setText("User: " + Integer.toString(qUser));
            int qAdmin = statistical.get(User.ROLE.values()[1].toString());
            tvAdmin.setText("Admin: " + Integer.toString(qAdmin));
            int total = qUser + qAdmin;
            tvUserTotal.setText("Tổng cộng: " + total);

            //Chart
            setUpPieChart(chartUser);
            drawPieChart(chartUser, statistical);
        }


    }

    private Map<String, Integer> getDataAccount(Cursor c){
        Map<String, Integer> map = new HashMap<>();
        c.moveToPosition(-1);
        while(c.moveToNext()){
            String key = User.ROLE.values()[c.getInt(0)].toString();
            int value = c.getInt(1);
            map.put(key, value);
        }
        c.close();
        return map;
    }

    //Lecture func
    private void lectureStatisticalChart(){
        Cursor c = db.LectureDepartmentStatistical();
        if(c.getCount() > 0){
            Map<String, Integer> data = getDataByDepartment(c);
            setUpPieChart(chartLecture);
            drawPieChart(chartLecture, data);
        }
    }

    private void lectureStatiscital(){
        Cursor c = db.getListLecture();
        tvLectureTotal.setText("Tổng cộng: " + c.getCount());
    }


    //Student func
    private void studentStatisticalChart(String year){
        Cursor c = db.StudentDepartmentStatistical(year);
        if(c.getCount() > 0){
            Map<String, Integer> data = getDataByDepartment(c);
            setUpPieChart(chartStudent);
            drawPieChart(chartStudent,data);

            int total = 0;
            for(Map.Entry<String, Integer> d: data.entrySet()){
                total += d.getValue();
            }

            tvStudentInYear.setText("Có " + total + " sinh viên");
        }
    }

    private void studentStatistical() throws ParseException {
        ArrayList<Student> c = db.get_list_students();
        tvStudentTotal.setText("Tổng cộng: " + c.size());
    }

    //Subject func
    private void subjectStatisticalChart(int subject_id, int year){
        Cursor c = db.SubjectInYearStatistical(subject_id, year);
        Map<Integer, Integer> data = getDataByNumber(c);
        setUpLineChart(chartSubject);
        drawLineChart(chartSubject, data);
    }

    private void subjectStatistical(){
        Cursor c = db.get_list_subject();
        c.moveToFirst();
        tvSubjectTotal.setText("Tổng cộng: " + Integer.toString(c.getCount()) + " môn học");
    }

    // Class func
    private void classStatisticalChart(int year){
        Cursor c = db.ClassQuantityInYearStatistical(year);
        Map<Integer, Integer> data = getDataByNumber(c);
        setUpBarChart(chartClass);
        drawBarChart(chartClass, data, "Số lượng lớp học trong năm");

        int total = 0;
        for(int i = 1;i <= 12;i++){
            if(data.containsKey(i))
                total += data.get(i).intValue();
        }

        tvClassYear.setText("Số lớp học đã mở trong năm " + edtClassYear.getText().toString() + ": " + Integer.toString(total).toString());
    }

    private void classStatistical(){
        Cursor c = db.get_list_class();
        c.moveToFirst();
        tvClassTotal.setText("Tổng cộng: " + Integer.toString(c.getCount()) + " lớp học đã được mở");
    }

    // Score func

    private void scoreStatisticalChart(int class_id){
        Cursor c = db.ScoreInClassStatistical(class_id);
        if(c.getCount() > 0){
            Map<Integer, Integer> data = getDataByNumber(c);
            setUpBarChart(chartScore);
            drawBarChart(chartScore, data, "Số lượng sinh viên");
        }

    }
}