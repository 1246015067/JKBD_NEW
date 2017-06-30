package com.example.ykc.jkbd;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ykc.jkbd.bean.information;

/**
 * Created by ykc on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initData();

    }

    private void initData() {
        information in = ExamApplication.getInsance().getIn();
        if(in!=null){
            showData(in);
        }
    }

    private void showData(information in) {

    }
}
