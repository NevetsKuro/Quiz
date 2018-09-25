package com.iocl.quiz;


public class EmpResult2 {

    String emp_name;
    String emp_code;
    String loc_code;
    String marks;
    String duration;
    
    public EmpResult2(){
    
    }

    public EmpResult2(String emp_name,String emp_code , String loc_code, String marks, String duration) {
        this.emp_name = emp_name;
        this.emp_code = emp_code;
        this.loc_code = loc_code;
        this.marks = marks;
        this.duration = duration;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getLoc_code() {
        return loc_code;
    }

    public void setLoc_code(String loc_code) {
        this.loc_code = loc_code;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    
    
}
