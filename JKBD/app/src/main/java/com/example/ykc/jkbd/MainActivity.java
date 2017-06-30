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

        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }
}
