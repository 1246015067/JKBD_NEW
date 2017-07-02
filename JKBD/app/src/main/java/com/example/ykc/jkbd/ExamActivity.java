package com.example.ykc.jkbd;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bean.reslust;

import java.util.List;

/**
 * Created by ykc on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tv0p1,tv0p2,tv0p3,tv0p4;
    ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tvExamInfo = (TextView) findViewById(R.id.tv_exam);
        tvExamTitle = (TextView) findViewById(R.id.tv_title);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
//        Log.e("main","error="+tv0p1);
    }

    private void initData() {
        information examInfo =ExamApplication.getInsance().getIn();
        if (examInfo!=null){
            showData(examInfo);
        }
        List<Question> questionList= ExamApplication.getInsance().getQuestionList();
        if (questionList!=null ){
            showExam(questionList);
        }
    }

    private void showExam(List<Question> questionList) {
        Question exam = questionList.get(0);
        if (exam!=null){
            tvExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());

        }
    }

    private void showData(information examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }
}
