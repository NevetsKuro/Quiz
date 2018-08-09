/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.quiz;

/**
 *
 * @author wrtrg2
 */
public class questions {
    String quesNo;
    String quesAns;

    public questions(String quesNo, String quesAns) {
        this.quesNo = quesNo;
        this.quesAns = quesAns;
    }

    public questions() {
    }

    public String getQuesNo() {
        return quesNo;
    }

    public void setQuesNo(String quesNo) {
        this.quesNo = quesNo;
    }

    public String getQuesAns() {
        return quesAns;
    }

    public void setQuesAns(String quesAns) {
        this.quesAns = quesAns;
    }
    
}
