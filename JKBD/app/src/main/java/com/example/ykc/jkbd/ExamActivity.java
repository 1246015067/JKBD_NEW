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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    TextView tvExamInfo,tvExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tv_load,tv_no;
    CheckBox cb_01,cb_02,cb_03,cb_04;
    CheckBox[] cbs=new CheckBox[4];
    LinearLayout layoutmloading,layout03,layout04;
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
        layout03= (LinearLayout) findViewById(R.id.layout_03);
        layout04= (LinearLayout) findViewById(R.id.layout_04);
        dialog= (ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo = (TextView) findViewById(R.id.tv_exam);
        tvExamTitle = (TextView) findViewById(R.id.tv_title);
        tv_no= (TextView) findViewById(R.id.tv_exam_no);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        cb_01= (CheckBox) findViewById((R.id.cb_01));
        cb_02= (CheckBox) findViewById((R.id.cb_02));
        cb_03= (CheckBox) findViewById((R.id.cb_03));
        cb_04= (CheckBox) findViewById((R.id.cb_04));
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        cbs[0]=cb_01;
        cbs[1]=cb_02;
        cbs[2]=cb_03;
        cbs[3]=cb_04;
        tv_load= (TextView) findViewById(R.id.tv_load);
        mImageView=(ImageView)findViewById(R.id.tv_image);
        layoutmloading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
        cb_01.setOnCheckedChangeListener(listener);
        cb_02.setOnCheckedChangeListener(listener);
        cb_03.setOnCheckedChangeListener(listener);
        cb_04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener=new  CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };
    private void initData() {
        if(isLoadExamInfoReceiver &&isLoadQuestionsReceiver) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutmloading.setVisibility(View.GONE);
                information examInfo = ExamApplication.getInsance().getIn();
                if (examInfo != null) {
                    showData(examInfo);
                }
                  showExam(biz.getQuestion());
            }
            else {
                layoutmloading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showExam(Question questionList) {

        if (questionList!=null){
            tvExamTitle.setText(questionList.getQuestion());
            tv_no.setText(biz.getExamIndex());
            tv0p1.setText(questionList.getItem1());
            tv0p2.setText(questionList.getItem2());
            tv0p3.setText(questionList.getItem3());
            tv0p4.setText(questionList.getItem4());

            layout03.setVisibility(questionList.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb_03.setVisibility(questionList.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout04.setVisibility(questionList.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb_04.setVisibility(questionList.getItem4().equals("")?View.GONE:View.VISIBLE);
            if(questionList.getUrl()!=null && !questionList.getUrl().equals("")) {
                Picasso.with(ExamActivity.this)
                        .load(questionList.getUrl())
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }

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

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
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
