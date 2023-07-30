package com.example.studentmanagement.information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pojo.Subject;
import com.example.studentmanagement.R;

public class SubjectInformation extends AppCompatActivity {

    TextView tvId, tvName, tvCredit, tvClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_infomation);

        tvId = (TextView) findViewById(R.id.tvSubjectInfoID);
        tvName = (TextView) findViewById(R.id.tvSubjectInfoName);
        tvCredit = (TextView) findViewById(R.id.tvSubjectInfoCredit);
        tvClass = (TextView) findViewById(R.id.tvSubjectInfoClass);


        Bundle bundle = getIntent().getExtras();
        Subject subject = (Subject) bundle.get("Subject");

        tvId.setText("ID: " + Integer.toString(subject.getId()));
        tvName.setText("Tên môn học: " + subject.getName());
        tvCredit.setText("Số tín chỉ: " + Integer.toString(subject.getCredit()));
        tvClass.setText("Tỉ lệ điểm(GK:CK): " + Double.toString(subject.getMidGradePercent()) + ":" + Double.toString(subject.getFinalGradePercent()));
    }
}