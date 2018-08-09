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
public class questionExcelModel {
    String QUES_DESC;
    String OPTION1;
    String OPTION2;
    String OPTION3;
    String OPTION4;
    String CORRECT_OP;
    String UPDATE_FLAG;

    public questionExcelModel() {
    }

    public questionExcelModel(String QUES_DESC, String OPTION1, String OPTION2, String OPTION3, String OPTION4, String CORRECT_OP, String UPDATE_FLAG) {
        this.QUES_DESC = QUES_DESC;
        this.OPTION1 = OPTION1;
        this.OPTION2 = OPTION2;
        this.OPTION3 = OPTION3;
        this.OPTION4 = OPTION4;
        this.CORRECT_OP = CORRECT_OP;
        this.UPDATE_FLAG = UPDATE_FLAG;
    }

    public String getQUES_DESC() {
        return QUES_DESC;
    }

    public void setQUES_DESC(String QUES_DESC) {
        this.QUES_DESC = QUES_DESC;
    }

    public String getOPTION1() {
        return OPTION1;
    }

    public void setOPTION1(String OPTION1) {
        this.OPTION1 = OPTION1;
    }

    public String getOPTION2() {
        return OPTION2;
    }

    public void setOPTION2(String OPTION2) {
        this.OPTION2 = OPTION2;
    }

    public String getOPTION3() {
        return OPTION3;
    }

    public void setOPTION3(String OPTION3) {
        this.OPTION3 = OPTION3;
    }

    public String getOPTION4() {
        return OPTION4;
    }

    public void setOPTION4(String OPTION4) {
        this.OPTION4 = OPTION4;
    }

    public String getCORRECT_OP() {
        return CORRECT_OP;
    }

    public void setCORRECT_OP(String CORRECT_OP) {
        this.CORRECT_OP = CORRECT_OP;
    }

    public String getUPDATE_FLAG() {
        return UPDATE_FLAG;
    }

    public void setUPDATE_FLAG(String UPDATE_FLAG) {
        this.UPDATE_FLAG = UPDATE_FLAG;
    }
    
    
    
}
