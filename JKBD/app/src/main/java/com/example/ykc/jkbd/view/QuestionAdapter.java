package com.example.ykc.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ykc.jkbd.ExamApplication;
import com.example.ykc.jkbd.R;
import com.example.ykc.jkbd.bean.Question;

import java.util.List;

/**
 * Created by ykc on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter{
    Context mContext;
    List<Question> examList;
    public QuestionAdapter(Context context){
        mContext=context;
        examList= ExamApplication.getInsance().getQuestionList();
    }
    @Override
    public int getCount() {
        return examList==null?0:examList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mContext, R.layout.item_question,null);
        TextView tvNO= (TextView) view.findViewById(R.id.tv_no);
        ImageView ivQuestion= (ImageView) view.findViewById(R.id.tv_ques);
        String a=examList.get(position).getUserAnswer();
        String ra=examList.get(position).getAnswer();
        if(a!=null&& !a.equals("")){
            ivQuestion.setImageResource(a.equals(ra)
                    ?R.drawable.answer24x24
                        :R.drawable.error_24px_11);
        }else {
            ivQuestion.setImageResource(R.drawable.question);
        }

        tvNO.setText("第"+(position+1)+"题");
        return view;
    }
}
