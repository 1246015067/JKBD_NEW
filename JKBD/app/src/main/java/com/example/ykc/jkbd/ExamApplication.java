package com.example.ykc.jkbd;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication  extends Application{
    information in;
    List<information>informationList;
    private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        
        initData();
    }

    public information getIn() {
        return in;
    }

    public void setIn(information in) {
        this.in = in;
    }

    public List<information> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<information> informationList) {
        this.informationList = informationList;
    }
    public  static  ExamApplication getInsance(){
        return  instance;
    }
    private void initData() {

        OkHttpUtils<information> utils=new OkHttpUtils<>(instance);
        String uru="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uru).targetClass(information.class)
                .execute(new OkHttpUtils.OnCompleteListener<information>() {
                    @Override
                    public void onSuccess(information result) {
                        Log.e("main","result="+result);
                        in=result;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);

                    }
                });


    }
}
