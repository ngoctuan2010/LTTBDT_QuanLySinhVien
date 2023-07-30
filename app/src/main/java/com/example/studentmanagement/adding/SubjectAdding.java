package com.example.studentmanagement.adding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pojo.Subject;
import com.example.service.QLSVDatabase;
import com.example.studentmanagement.R;

public class SubjectAdding extends AppCompatActivity {

    EditText edtSubjectName, edtSubjectCredit, edtSubjectMid, edtSubjectFinal;
    Button btnSubjectAdd, btnSubjectUpdate;

    TextView tvTitle;

    QLSVDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subject_adding);
        db = new QLSVDatabase(this);

        edtSubjectCredit = (EditText) findViewById(R.id.edtClassAddingQuantity);
        edtSubjectName = (EditText) findViewById(R.id.edtClassAddingStarted);
        edtSubjectMid = (EditText)findViewById(R.id.edtSubjectAddingMid);
        edtSubjectFinal = (EditText)findViewById(R.id.edtSubjectAddingFinal);

        btnSubjectAdd = (Button) findViewById(R.id.btnClassAdd);
        btnSubjectUpdate = (Button) findViewById(R.id.btnSubjectEdit);

        edtSubjectCredit = (EditText) findViewById(R.id.edtLectureAddingName);
        edtSubjectName = (EditText) findViewById(R.id.edtUserAddingUsername);
        btnSubjectAdd = (Button) findViewById(R.id.btnLectureAdd);
        btnSubjectUpdate = (Button) findViewById(R.id.btnLectureEdit);
        edtSubjectMid = (EditText) findViewById(R.id.edtSubjectAddingMid);
        edtSubjectFinal = (EditText) findViewById(R.id.edtSubjectAddingFinal);
        tvTitle = (TextView) findViewById(R.id.tvTitleSubjectDialog);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btnSubjectAdd.setVisibility(View.GONE);
            btnSubjectUpdate.setVisibility(View.VISIBLE);
            tvTitle.setText("Sửa môn học");
            Subject subject = (Subject) bundle.getSerializable("Subject");
            edtSubjectName.setText(subject.getName());
            edtSubjectCredit.setText(Integer.toString(subject.getCredit()));
            edtSubjectMid.setText(Double.toString(subject.getMidGracePercent()));
            edtSubjectFinal.setText(Double.toString(subject.getFinalGracePercent()));

            btnSubjectUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateInput()) {
                        String name = edtSubjectName.getText().toString();
                        int credit = Integer.parseInt(edtSubjectCredit.getText().toString());
                        double _mid = Double.parseDouble(edtSubjectMid.getText().toString());
                        double _final = Double.parseDouble(edtSubjectFinal.getText().toString());

                        if (_mid + _final != 10) {
                            edtSubjectMid.setError("Tỉ lệ chưa phù hợp");
                            edtSubjectFinal.setError("Tỉ lệ chưa phù hợp");
                            return;
                        }

                        subject.setName(name);
                        subject.setCredit(credit);
                        subject.setMidGracePercent(_mid);
                        subject.setFinalGracePercent(_final);

                        if (db.getDiffSubjectByName(subject.getId(), subject.getName()).getCount() > 0) {
                            Toast.makeText(SubjectAdding.this, "Môn học đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (db.update_subject(subject) > 0) {
                            Toast.makeText(SubjectAdding.this, "Bạn đã sửa môn học thành công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(SubjectAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();
                    } else {
                        requiredInput();
                    }
                }
            });

        } else {
            btnSubjectUpdate.setVisibility(View.GONE);
            btnSubjectAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateInput()) {
                        String name = edtSubjectName.getText().toString();
                        int credit = Integer.parseInt(edtSubjectCredit.getText().toString());
                        double _mid = Double.parseDouble(edtSubjectMid.getText().toString());
                        double _final = Double.parseDouble(edtSubjectFinal.getText().toString());

                        if (_mid + _final != 10) {
                            edtSubjectMid.setError("Tỉ lệ chưa phù hợp");
                            edtSubjectFinal.setError("Tỉ lệ chưa phù hợp");
                            return;
                        }

                        if (db.getSubjectByName(name).getCount() > 0) {
                            Toast.makeText(SubjectAdding.this, "Môn học đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Subject s = new Subject(-1, name, credit, _mid, _final);
                        if (db.add_subject(s) > 0) {
                            Toast.makeText(SubjectAdding.this, "Bạn đã thêm môn học thành công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(SubjectAdding.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        requiredInput();
                    }
                }
            });
        }

    }

    private boolean validateInput() {
        String subjectName = edtSubjectName.getText().toString();
        String subjectCredit = edtSubjectCredit.getText().toString();

        String _mid = edtSubjectMid.getText().toString();
        String _final = edtSubjectFinal.getText().toString();


        return (subjectName != null && !subjectName.isEmpty() &&
                subjectCredit != null && !subjectCredit.isEmpty() &&
                _mid != null && !_mid.isEmpty() &&
                _final != null && !_final.isEmpty());
    }

    private void requiredInput() {
        String subjectName = edtSubjectName.getText().toString();
        String subjectCredit = edtSubjectCredit.getText().toString();
        String _mid = edtSubjectMid.getText().toString();
        String _final = edtSubjectFinal.getText().toString();

        String err_msg = getResources().getString(R.string.inputRequired);

        if (!(subjectName != null && !subjectName.isEmpty()))
            edtSubjectName.setError(err_msg);
        if (!(subjectCredit != null && !subjectCredit.isEmpty()))
            edtSubjectCredit.setError(err_msg);
        if (!(_mid != null && !_mid.isEmpty()))
            edtSubjectMid.setError(err_msg);
        if (!(_final != null && !_final.isEmpty()))
            edtSubjectFinal.setError(err_msg);
    }
}