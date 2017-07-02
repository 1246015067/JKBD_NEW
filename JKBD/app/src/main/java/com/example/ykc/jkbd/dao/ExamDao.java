package com.example.ykc.jkbd.dao;

import android.util.Log;

import com.example.ykc.jkbd.ExamActivity;
import com.example.ykc.jkbd.ExamApplication;
import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bean.reslust;
import com.example.ykc.jkbd.utils.OkHttpUtils;
import com.example.ykc.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by ykc on 2017/7/2.
 */

public class ExamDao implements IExamDao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<information> utils=new OkHttpUtils<>(ExamApplication.getInsance());
        String uru="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uru).targetClass(information.class)
                .execute(new OkHttpUtils.OnCompleteListener<information>() {
                    @Override
                    public void onSuccess(information result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInsance().setIn(result);

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);

                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String> utils1=new OkHttpUtils<>(ExamApplication.getInsance());
        String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {

                    @Override
                    public void onSuccess(String jsonStr) {
                        reslust re = ResultUtils.getListResultFromJson(jsonStr);
                        if (re != null && re.getError_code() == 0) {
                            List<Question> list=re.getQuestion();
                            int i=re.getError_code();
                            Log.e("main","error="+i);
                            Log.e("main","error="+list);
                            if (list != null && list.size() > 0)
                                ExamApplication.getInsance().setInformationList(list);

                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });

    }
}
