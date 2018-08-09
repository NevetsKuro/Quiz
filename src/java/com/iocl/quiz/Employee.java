package com.iocl.quiz;


public class Employee {

    String emp_code;
    String emp_name;
    String loc_code;
    String emp_design;
    String company_code;
    String role;
    
    public Employee(){
    
    }

    public Employee(String emp_code, String emp_name, String loc_code, String emp_design, String company_code, String role) {
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.loc_code = loc_code;
        this.emp_design = emp_design;
        this.company_code = company_code;
        this.role = role;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getEmp_design() {
        return emp_design;
    }

    public void setEmp_design(String emp_design) {
        this.emp_design = emp_design;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }
    
}
