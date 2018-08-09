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
public class EmpResults {
    String emp_code;
    String resultMarks;
    String timeTaken;
    String hasSubmitted;

    public EmpResults() {
    }

    public EmpResults(String emp_code, String resultMarks, String timeTaken, String hasSubmitted) {
        this.emp_code = emp_code;
        this.resultMarks = resultMarks;
        this.timeTaken = timeTaken;
        this.hasSubmitted = hasSubmitted;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getResultMarks() {
        return resultMarks;
    }

    public void setResultMarks(String resultMarks) {
        this.resultMarks = resultMarks;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getHasSubmitted() {
        return hasSubmitted;
    }

    public void setHasSubmitted(String hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }
    
    
}
