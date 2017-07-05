package com.example.ykc.jkbd.bzi;

import com.example.ykc.jkbd.bean.Question;

/**
 * Created by ykc on 2017/7/2.
 */

public interface IExamBiz {
    void beginExam();
    Question getQuestion();
    Question nextQuestion();
    Question preQuestion();
    Question getQuestion(int index);
    int commitExam();
    String getExamIndex();
}
