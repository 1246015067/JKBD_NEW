package com.example.ykc.jkbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ykc.jkbd.bean.information;
import com.example.ykc.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        finish();
    }

    public void text(View view) {

        OkHttpUtils<information>utils=new OkHttpUtils<>(getApplication());
        String uru="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uru).targetClass(information.class)
            .execute(new OkHttpUtils.OnCompleteListener<information>() {
                         @Override
                         public void onSuccess(information result) {
                             Log.e("main","result="+result);
                         }

                         @Override
                         public void onError(String error) {
                             Log.e("main","error="+error);

                         }
                     });

        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }
}
