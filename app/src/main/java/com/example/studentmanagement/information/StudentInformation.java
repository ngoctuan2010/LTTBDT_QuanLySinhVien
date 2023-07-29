package com.example.studentmanagement.information;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Class;
import com.example.pojo.Score;
import com.example.pojo.Student;
import com.example.pojo.Subject;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

public class StudentInformation extends AppCompatActivity {

    TextView txtNameClass, txtQuantity, txtSchoolyear, txtIdStudent, txtStudentName, txtBirth, txtScore10, txtScore4;
    EditText edtMidScore, edtEndScore;
    Button btnSave;
    QLSVDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        txtNameClass = findViewById(R.id.txtClassName);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtSchoolyear = findViewById(R.id.txtSchoolyear);
        txtIdStudent = findViewById(R.id.txtIdStudent);
        txtStudentName = findViewById(R.id.txtStudentName);
        txtBirth = findViewById(R.id.txtBirth);
        txtScore10 = findViewById(R.id.txtScore10);
        txtScore4 = findViewById(R.id.txtScore4);
        edtMidScore = findViewById(R.id.edtMidScore);
        edtEndScore = findViewById(R.id.edtEndScore);
        btnSave = findViewById(R.id.btnSave);

        Bundle bundle = getIntent().getExtras();
        Student student = (Student) bundle.getSerializable("student");
        Class aClass = (Class) bundle.getSerializable("class");
        database = new QLSVDatabase(StudentInformation.this);

        Score score = database.getScoreByClassAndStudent(student.getId(), aClass.getId());

        txtNameClass.setText(aClass.getName());
        txtQuantity.setText(aClass.getQuantity());
        txtSchoolyear.setText(aClass.getYear());
        txtIdStudent.setText(student.getId());
        txtStudentName.setText(student.getName());
        txtBirth.setText(student.getBirth().toString());
        edtMidScore.setText(String.valueOf(score.getMidScore()));
        edtEndScore.setText(String.valueOf(score.getEndScore()));

        Subject subject = database.getSubjectByClass(aClass.getId());
        double percentMidGrade = subject.getMidGradePercent();
        double percentFinalGrade = subject.getFinalGradePercent();
        double gradeAverage = score.getMidScore() * percentMidGrade + score.getEndScore() * percentFinalGrade;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtScore4.getText().toString().isEmpty() && txtScore10.getText().toString().isEmpty())
                    return;
                score.setMidScore(Double.parseDouble(edtMidScore.getText().toString()));
                score.setMidScore(Double.parseDouble(edtEndScore.getText().toString()));
                if (database.updateScore(score) == -1) {
                    Toast.makeText(StudentInformation.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    Toast.makeText(StudentInformation.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        edtEndScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!checkEmty() && checkInput()) {
                    txtScore10.setText(String.valueOf(gradeAverage));
                    txtScore4.setText(String.valueOf(gradeAverage * 0.4));
                }
            }
        });
        edtMidScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!checkEmty() && checkInput()) {
                    txtScore10.setText(String.valueOf(gradeAverage));
                    txtScore4.setText(String.valueOf(gradeAverage * 0.4));
                }
            }
        });
    }

    public boolean checkEmty() {
        if (!edtMidScore.getText().toString().isEmpty() && !edtEndScore.getText().toString().isEmpty())
            return false;
        return true;
    }

    public boolean checkInput() {
        String string1 = edtMidScore.getText().toString();
        String string2 = edtEndScore.getText().toString();
        float midScore = Float.parseFloat(string1);
        float endScore = Float.parseFloat(string2);
        return (midScore >= 0 && midScore <= 10) && (endScore >= 0 && endScore <= 10);
    }
}
