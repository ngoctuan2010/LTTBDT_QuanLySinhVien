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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class StudentInformation extends AppCompatActivity {

    TextView txtNameClass, txtQuantity, txtSchoolyear, txtIdStudent, txtStudentName, txtBirth, txtScore10, txtScore4;
    EditText edtMidScore, edtEndScore;
    Button btnSave;
    QLSVDatabase database;
    SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
    double percentMidGrade, percentFinalGrade;

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
        edtMidScore.requestFocus();

        Bundle bundle = getIntent().getExtras();
        Student student = (Student) bundle.getSerializable("student");
        Class aClass = (Class) bundle.getSerializable("class");
        showInformation(student, aClass);

        database = new QLSVDatabase(StudentInformation.this);
        Score score = database.getScoreByClassAndStudent(student.getId(), aClass.getId());
        showScore(score);

        Subject subject = database.getSubjectByClass(aClass.getId());
        percentMidGrade = subject.getMidGradePercent();
        percentFinalGrade = subject.getFinalGradePercent();
        showAverageScoreOnCreate();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtScore4.getText().toString().isEmpty() && txtScore10.getText().toString().isEmpty()) {
                    Toast.makeText(StudentInformation.this, "Điểm không hợp lệ.", Toast.LENGTH_SHORT).show();
                    return;
                }
                score.setMidScore(Double.parseDouble(edtMidScore.getText().toString().trim()));
                score.setEndScore(Double.parseDouble(edtEndScore.getText().toString().trim()));
                if (database.updateScore(score) == -1) {
                    Toast.makeText(StudentInformation.this, "Sửa thất bại.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(StudentInformation.this, "Sửa thành công.", Toast.LENGTH_SHORT).show();
            }
        });
        showAverageScore(edtMidScore, percentMidGrade, percentFinalGrade);
        showAverageScore(edtEndScore, percentMidGrade, percentFinalGrade);
    }

    public void showAverageScoreOnCreate() {
        if (!checkEmty() && checkInput()) {
            double m = Double.parseDouble(edtMidScore.getText().toString().trim());
            double e = Double.parseDouble(edtEndScore.getText().toString().trim());
            double s10 = m * percentMidGrade * 0.1 + e * percentFinalGrade * 0.1;
            double s4 = s10 * 0.4;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            txtScore10.setText(decimalFormat.format(s10));
            txtScore4.setText(decimalFormat.format(s4));
        } else {
            txtScore10.setText("");
            txtScore4.setText("");
        }
    }

    public boolean checkEmty() {
        if (!edtMidScore.getText().toString().trim().isEmpty() && !edtEndScore.getText().toString().trim().isEmpty())
            return false;
        return true;
    }

    public boolean checkInput() {
        String string1 = edtMidScore.getText().toString().trim();
        String string2 = edtEndScore.getText().toString().trim();
        float midScore = Float.parseFloat(string1);
        float endScore = Float.parseFloat(string2);
        return (midScore >= 0 && midScore <= 10) && (endScore >= 0 && endScore <= 10);
    }

    public void showInformation(Student student, Class cl) {
        if (student != null && cl != null) {
            txtNameClass.setText(cl.getName());
            txtQuantity.setText(String.valueOf(cl.getQuantity()));
            txtSchoolyear.setText(cl.getYear());
            txtIdStudent.setText(String.valueOf(student.getId()));
            txtStudentName.setText(student.getName());
            txtBirth.setText(spdf.format(student.getBirth()));
        }
    }

    public void showScore(Score score) {
        if (score != null) {
            if (score.getMidScore() == -1 && score.getEndScore() == -1) {
                edtMidScore.setText("");
                edtEndScore.setText("");
            } else {
                edtMidScore.setText(String.valueOf(score.getMidScore()));
                edtEndScore.setText(String.valueOf(score.getEndScore()));
            }
        }
    }

    public void showAverageScore(EditText editText, double percentMidGrade, double percentFinalGrade) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showAverageScoreOnCreate();
            }
        });
    }
}
