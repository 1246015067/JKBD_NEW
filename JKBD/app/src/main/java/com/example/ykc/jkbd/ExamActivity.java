package com.example.ykc.jkbd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bzi.ExamBiz;
import com.example.ykc.jkbd.bzi.IExamBiz;
import com.example.ykc.jkbd.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ykc on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
//    TextView tvExamInfo, tvExamTitle, tv0p1, tv0p2, tv0p3, tv0p4, tv_load, tv_no, tv_time;
//    CheckBox cb_01, cb_02, cb_03, cb_04;
    CheckBox[] cbs = new CheckBox[4];
    TextView[] tv0ps=new TextView[4];
//    LinearLayout layoutmloading;
//    //ProgressBar dialog;
//    ImageView mImageView;
    IExamBiz biz;
    //Gallery gallery;
    QuestionAdapter Adapter;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;

    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestiobnBroadcast mLoadQuestiobnBroadcast;
    @BindView(R.id.load_dialog) ProgressBar dialog;
    @BindView(R.id.tv_load)   TextView tv_load;
    @BindView(R.id.layout_loding)  LinearLayout layoutmloading;
    //@BindView(R.id.imageView)  ImageView mImageView;
    @BindView(R.id.tv_exam)  TextView tvExamInfo;
    @BindView(R.id.tv_time)  TextView tv_time;
    @BindView(R.id.tv_exam_no)  TextView tv_no;
    @BindView(R.id.tv_title)  TextView tvExamTitle;
    @BindView(R.id.tv_image)  ImageView mImageView;
    @BindView(R.id.tv_op1)  TextView tv0p1;
    @BindView(R.id.tv_op2)  TextView tv0p2;
    @BindView(R.id.tv_op3)   TextView tv0p3;
    @BindView(R.id.layout_03)  LinearLayout layout03;
    @BindView(R.id.tv_op4)  TextView tv0p4;
    @BindView(R.id.layout_04)  LinearLayout layout04;
    @BindView(R.id.cb_01)  CheckBox cb_01;
    @BindView(R.id.cb_02)   CheckBox cb_02;
    @BindView(R.id.cb_03)   CheckBox cb_03;
    @BindView(R.id.cb_04)   CheckBox cb_04;
    @BindView(R.id.gallery)   Gallery gallery;
    @BindView(R.id.next)   Button next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ButterKnife.bind(this);

        mLoadExamBroadcast = new LoadExamBroadcast();
        mLoadQuestiobnBroadcast = new LoadQuestiobnBroadcast();
        setLIstener();
        initView();
        biz = new ExamBiz();
        loadData();
    }

    private void setLIstener() {
        registerReceiver(mLoadExamBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestiobnBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
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
//        layoutmloading = (LinearLayout) findViewById(R.id.layout_loding);
//        layout03 = (LinearLayout) findViewById(R.id.layout_03);
//        layout04 = (LinearLayout) findViewById(R.id.layout_04);
//        dialog = (ProgressBar) findViewById(R.id.load_dialog);
//        tvExamInfo = (TextView) findViewById(R.id.tv_exam);
//        tvExamTitle = (TextView) findViewById(R.id.tv_title);
//        tv_no = (TextView) findViewById(R.id.tv_exam_no);
//        tv0p1 = (TextView) findViewById(R.id.tv_op1);
//        tv0p2 = (TextView) findViewById(R.id.tv_op2);
//        tv0p3 = (TextView) findViewById(R.id.tv_op3);
//        gallery = (Gallery) findViewById(R.id.gallery);
//        cb_01 = (CheckBox) findViewById((R.id.cb_01));
//        cb_02 = (CheckBox) findViewById((R.id.cb_02));
//        cb_03 = (CheckBox) findViewById((R.id.cb_03));
//        cb_04 = (CheckBox) findViewById((R.id.cb_04));
//        tv0p4 = (TextView) findViewById(R.id.tv_op4);
       cbs[0] = cb_01;
       cbs[1] = cb_02;
       cbs[2] = cb_03;
       cbs[3] = cb_04;
        tv0ps[0]=tv0p1;
        tv0ps[1]=tv0p2;
        tv0ps[2]=tv0p3;
        tv0ps[3]=tv0p4;


//        tv_load = (TextView) findViewById(R.id.tv_load);
//        mImageView = (ImageView) findViewById(R.id.tv_image);
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
    @OnClick(R.id.load_dialog) void onLoadClick(){
        loadData();
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
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
        if (isLoadExamInfoReceiver && isLoadQuestionsReceiver) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutmloading.setVisibility(View.GONE);
                information examInfo = ExamApplication.getInsance().getIn();
                if (examInfo != null) {
                    showData(examInfo);
                    initTime(examInfo);
                }
                initGallery();
                showExam(biz.getQuestion());

            } else {
                layoutmloading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void initGallery() {
        Adapter = new QuestionAdapter(this);
        gallery.setAdapter(Adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gallery", "gallery item position+" + position);
                saveUeserAnswer();
                showExam(biz.getQuestion(position));
            }
        });
    }

    private void initTime(information examInfo) {
        int sumTime = examInfo.getLimitTime() * 60 * 1000;
        // int sumTime=10*1000;
        final long overTime = sumTime + System.currentTimeMillis();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = overTime - System.currentTimeMillis();
                final long min = (int) (l / 1000 / 60);
                final long scc = (int) (l / 1000 % 60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("剩余时间：" + min + "分" + scc + "秒");
                    }
                });
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                ;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        }, sumTime);
    }


    private void showExam(Question questionList) {

        if (questionList != null) {
            tvExamTitle.setText(questionList.getQuestion());
            tv_no.setText(biz.getExamIndex());
            tv0p1.setText(questionList.getItem1());
            tv0p2.setText(questionList.getItem2());
            tv0p3.setText(questionList.getItem3());
            tv0p4.setText(questionList.getItem4());
            tv_time = (TextView) findViewById(R.id.tv_time);
            layout03.setVisibility(questionList.getItem3().equals("") ? View.GONE : View.VISIBLE);
            cb_03.setVisibility(questionList.getItem3().equals("") ? View.GONE : View.VISIBLE);
            layout04.setVisibility(questionList.getItem4().equals("") ? View.GONE : View.VISIBLE);
            cb_04.setVisibility(questionList.getItem4().equals("") ? View.GONE : View.VISIBLE);
            if (questionList.getUrl() != null && !questionList.getUrl().equals("")) {
                Picasso.with(ExamActivity.this)
                        .load(questionList.getUrl())
                        .into(mImageView);
            } else {
                mImageView.setVisibility(View.GONE);
            }
            resetDptions();
            String userAnswer = questionList.getUserAnswer();
            if (userAnswer != null && !userAnswer.equals("")) {
                int usercb = Integer.parseInt(userAnswer) - 1;
                cbs[usercb].setChecked(true);
                setOptions(true);
                setAnswerTextColor(userAnswer,questionList.getAnswer());
            } else {
                setOptions(false);
                setOptionsColor();
            }
        }
    }

    private void setOptionsColor() {
        for (TextView tv0p:tv0ps) {
            tv0p.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void setAnswerTextColor(String userAnswer, String answer) {
        int ra=Integer.parseInt(answer)-1;
        for(int i=0;i<tv0ps.length;i++){
            if(i==ra) {

                tv0ps[i].setTextColor(getResources().getColor(R.color.greend));
            }else {
                if(!userAnswer.equals(answer)){
                    int ua=Integer.parseInt(userAnswer)-1;
                    if(i==ua){
                        tv0ps[i].setTextColor(getResources().getColor(R.color.red));
                    }
                    else {
                        tv0ps[i].setTextColor(getResources().getColor(R.color.black));
                    }
                }
            }
        }
    }

    private void setOptions(boolean hasAnswer) {
        for (CheckBox cb : cbs) {
            cb.setEnabled(!hasAnswer);
        }
    }

    private void resetDptions() {
        for (CheckBox d : cbs) {
            d.setChecked(false);
        }
    }

    private void saveUeserAnswer() {
        for (int i = 0; i < cbs.length; i++) {
            if (cbs[i].isChecked()) {
                biz.getQuestion().setUserAnswer(String.valueOf(i + 1));
                Adapter.notifyDataSetChanged();
                return;
            }
        }
        biz.getQuestion().setUserAnswer("");
        Adapter.notifyDataSetChanged();
    }

    private void showData(information examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast != null) {
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestiobnBroadcast != null) {
            unregisterReceiver(mLoadQuestiobnBroadcast);
        }
    }

    public void preExam(View view) {
        saveUeserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUeserAnswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        saveUeserAnswer();
        int s = biz.commitExam();
        View inflate = View.inflate(this, R.layout.layout_result, null);
        TextView tvResult = (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n" + s + "分！");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.exam_commit32x32)
                .setTitle("交卷")
                // .setMessage("你的分数为\n"+s+"分！")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
    }

    class LoadExamBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("mLoadExamBroadcast", "mLoadExamBroadcast,isSuccess=" + isSuccess);
            if (isSuccess) {
                isLoadExamInfo = true;
            }
            isLoadExamInfoReceiver = true;
            initData();
        }
    }

    class LoadQuestiobnBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("mLoadQuestiobnBroadcast", "mLoadQuestiobnBroadcast,isSuccess=" + isSuccess);
            if (isSuccess) {
                isLoadQuestions = true;
            }
            isLoadQuestionsReceiver = true;
            initData();
        }
    }

}
