package com.example.ykc.jkbd.bean;

import java.util.List;

/**
 * Created by ykc on 2017/6/28.
 */

public class reslust {
    /**
     * error_code : 0
     * reason : ok
     * result : []
     */

    private int error_code;
    private String reason;
    private List<Question> questions;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Question> getQuestion() {
        return questions;
    }

    public void setQuestion(List<Question> questions) {
        this.questions = questions;
    }
}
