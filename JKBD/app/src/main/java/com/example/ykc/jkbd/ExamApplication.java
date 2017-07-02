package com.example.ykc.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bean.reslust;
import com.example.ykc.jkbd.bzi.ExamBiz;
import com.example.ykc.jkbd.bzi.IExamBiz;
import com.example.ykc.jkbd.utils.OkHttpUtils;
import com.example.ykc.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication  extends Application{
    information in;
    private static ExamApplication instance;
    List<Question> QuestionList;
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
        initData();
    }

    public information getIn() {
        return in;
    }

    public void setIn(information in) {
        this.in = in;
    }

    public List<Question> getQuestionList() {
        return QuestionList;
    }

    public void setInformationList(List<Question> QuestionList) {
        this.QuestionList = QuestionList;
    }
    public  static  ExamApplication getInsance(){
        return  instance;
    }
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
//
//        OkHttpUtils<information> utils=new OkHttpUtils<>(instance);
//        String uru="http://101.251.196.90:8080/JztkServer/examInfo";
//        utils.url(uru).targetClass(information.class)
//                .execute(new OkHttpUtils.OnCompleteListener<information>() {
//                    @Override
//                    public void onSuccess(information result) {
//                        Log.e("main","result="+result);
//                        in=result;
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.e("main","error="+error);
//
//                    }
//                });
//
//                OkHttpUtils<String> utils1=new OkHttpUtils<>(instance);
//                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
//                utils1.url(url2)
//                        .targetClass(String.class)
//                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
//
//                    @Override
//                    public void onSuccess(String jsonStr) {
//                        reslust re = ResultUtils.getListResultFromJson(jsonStr);
//                        if (re != null && re.getError_code() == 0) {
//                           List<Question> list=re.getQuestion();
//                            int i=re.getError_code();
//                            Log.e("main","error="+i);
//                            Log.e("main","error="+list);
//                            if (list != null && list.size() > 0)
//                                QuestionList = list;
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                            Log.e("main","error="+error);
//                    }
//                });

            }
        }).start();

    }
}
