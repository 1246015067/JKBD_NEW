package com.example.ykc.jkbd;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bean.reslust;
import com.example.ykc.jkbd.bzi.ExamBiz;
import com.example.ykc.jkbd.bzi.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ykc on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tv_load;
    LinearLayout layoutmloading;
    ProgressBar dialog;
    ImageView mImageView;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    boolean isLoadExamInfoReceiver=false;
    boolean isLoadQuestionsReceiver=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestiobnBroadcast mLoadQuestiobnBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestiobnBroadcast=new LoadQuestiobnBroadcast();
        setLIstener();
        initView();
        biz=new ExamBiz();
        loadData();
    }

    private void setLIstener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestiobnBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutmloading.setEnabled(false);
        dialog.setVisibility(View.GONE);
        tv_load.setText("下载数据中...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutmloading= (LinearLayout) findViewById(R.id.layout_loding);
        dialog= (ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo = (TextView) findViewById(R.id.tv_exam);
        tvExamTitle = (TextView) findViewById(R.id.tv_title);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        tv_load= (TextView) findViewById(R.id.tv_load);
        mImageView=(ImageView)findViewById(R.id.tv_image);
        layoutmloading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
    }

    private void initData() {
        if(isLoadExamInfoReceiver &&isLoadQuestionsReceiver) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutmloading.setVisibility(View.GONE);
                information examInfo = ExamApplication.getInsance().getIn();
                if (examInfo != null) {
                    showData(examInfo);
                }
                List<Question> questionList = ExamApplication.getInsance().getQuestionList();
                if (questionList != null) {
                    showExam(questionList);
                }
            }
            else {
                layoutmloading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败，点击重新下载");
            }
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
            Picasso.with(ExamActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);

        }
    }

    private void showData(information examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestiobnBroadcast!=null){
            unregisterReceiver(mLoadQuestiobnBroadcast);
        }
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("mLoadExamBroadcast","mLoadExamBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadExamInfo=true;
            }
            isLoadExamInfoReceiver=true;
            initData();
        }
    }

    class LoadQuestiobnBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("mLoadQuestiobnBroadcast","mLoadQuestiobnBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadQuestions=true;
            }
            isLoadQuestionsReceiver=true;
            initData();
        }
    }

}
