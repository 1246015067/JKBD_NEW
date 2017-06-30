package com.example.ykc.jkbd;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;

import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.bean.reslust;
import com.example.ykc.jkbd.utils.OkHttpUtils;
import com.example.ykc.jkbd.utils.ResultUtils;

import java.util.List;

import static android.R.id.list;

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
        new Thread(new Runnable() {
            @Override
            public void run() {


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

                OkHttpUtils<String> utils1=new OkHttpUtils<String>(instance);
                String utils2="";
                utils1.url(utils2);
                utils1.targetClass(String.class);
                utils1.execute(new OkHttpUtils.OnCompleteListener<String>() {

                    @Override
                    public void onSuccess(String result) {
                        reslust re = ResultUtils.getListResultFromJson(result);
                        if (re != null && re.getError_code() == 0) {
                           List<information> list=re.getResults();
                            if (list != null && list.size() > 0) {
                                informationList = list;
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                            Log.e("main","error="+error);
                    }
                });

            }
        }).start();

    }
}
