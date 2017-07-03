package com.example.ykc.jkbd.bzi;

import com.example.ykc.jkbd.ExamApplication;
import com.example.ykc.jkbd.bean.Question;
import com.example.ykc.jkbd.dao.ExamDao;
import com.example.ykc.jkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by ykc on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex=0;
    List<Question> questionlist=null;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadQuestionLists();

    }

    @Override
    public Question getQuestion() {
        questionlist=ExamApplication.getInsance().getQuestionList();
        if(questionlist!=null) {
            return questionlist.get(examIndex);
        }
        else {
            return null;
        }
    }

    @Override
    public Question nextQuestion() {
        if(questionlist!=null && examIndex<questionlist.size()-1) {
            examIndex++;
            return questionlist.get(examIndex);
        }
        else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if(questionlist!=null && examIndex>0) {
            examIndex--;
            return questionlist.get(examIndex);
        }
        else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
