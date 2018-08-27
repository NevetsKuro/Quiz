/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class employeeController extends HttpServlet {
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        
        String action = request.getParameter("action");
        String json = request.getParameter("json");
        Gson gson = new Gson();
        Employee emp = gson.fromJson(json,Employee.class);
        
        Connection con;
        Statement st;
        con=DatabaseConnectionFactory.createConnection();
        
        if(action.equals("retrieve")){
            try {
                st=con.createStatement();
                String sql = "Select * from emp_master";
                ResultSet rs = st.executeQuery(sql);

                List<Employee> empList = new ArrayList<Employee>();
                Employee emp1;
                
                while(rs.next()){
                    emp1 = new Employee();
                    emp1.setEmp_code(rs.getString(1));
                    emp1.setEmp_name(rs.getString(2));
                    emp1.setLoc_code(rs.getString(3));
                    emp1.setEmp_design(rs.getString(4));
                    emp1.setCompany_code(rs.getString(5));
                    emp1.setRole(rs.getString(6));
                    empList.add(emp1);
                }
                
                gson.toJson(empList, response.getWriter());
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    try { con.close(); } catch (Exception e) {  } 
                }
            }
        }else if(action.equals("retest")){
        
            try {
                st=con.createStatement();
                
                String emp_code = request.getParameter("id");
                
                String sql1 = "Delete from login_details where emp_code="+emp_code;
                String sql2 = "Delete from logout_details where emp_code="+emp_code;
                String sql3 = "Delete from mst_result where emp_code="+emp_code;
                String sql4 = "Delete from ans_details where emp_code="+emp_code;
                
                st.execute(sql1);
                st.execute(sql2);
                st.execute(sql3);
                st.execute(sql4);
                System.out.println("Entries Deleted");
                gson.toJson("success", response.getWriter());
                con.commit();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    try { con.close(); } catch (Exception e) {  } 
                }
            }
            
        }else if(action.equals("roleChange")){
        
            try {
                st=con.createStatement();
                
                String emp_code = request.getParameter("id");
                String role = request.getParameter("role");
                
                if(role.equals("ADMIN")){
                    role = "USER";
                }else if(role.equals("USER")){
                    removeTestDetails(request,response,emp_code);
                    role = "ADMIN";
                }
                
                String sql5 = "Update emp_master set role='"+role+"' where emp_code="+emp_code;
                
                st.execute(sql5);
                System.out.println("Entry Updated");
                gson.toJson(emp_code, response.getWriter());
                con.commit();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    try { con.close(); } catch (Exception e) {  } 
                }
            }

            
        }
    }

    private void removeTestDetails(HttpServletRequest request, HttpServletResponse response, String emp_code) {
        Connection con;
        Statement st;
        con=DatabaseConnectionFactory.createConnection();
        try {        
                st=con.createStatement();
                
                if(emp_code.length()>5){
                    String sql6 = "Delete from logout_details where emp_code="+emp_code;
                    String sql7 = "Delete from mst_result where emp_code="+emp_code;
                    String sql8 = "Delete from ans_details where emp_code="+emp_code;
                    String sql9 = "Delete from login_details where emp_code="+emp_code;

                    st.execute(sql6);
                    st.execute(sql7);
                    st.execute(sql8);
                    st.execute(sql9);
                    System.out.println("Entry Deleted of "+emp_code);
                }
                
                con.commit();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    try { con.close(); } catch (Exception e) {  } 
                }
            }
    }
}
