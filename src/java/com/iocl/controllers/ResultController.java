/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.EmpResult2;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wrtrg2
 */
public class ResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url");
        if(url.equalsIgnoreCase("upload")){
            request.getRequestDispatcher("jsps/UploadExcel.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("result")){
            request.getRequestDispatcher("jsps/resultsList.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("Finish")){
            request.getRequestDispatcher("jsps/result.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("Masters")){
            request.getRequestDispatcher("jsps/masters.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("empList")){
            request.getRequestDispatcher("jsps/empList.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("Instructions")){
            request.getRequestDispatcher("jsps/instructions.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("feedback")){
            request.getRequestDispatcher("jsps/Feedback.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("downloadExcel")){
            ArrayList<EmpResult2> empRList = new ArrayList<EmpResult2>();
            empRList = downloadExcel(request,response);
            new Gson().toJson(empRList, response.getWriter());
        }
    }

    private ArrayList<EmpResult2> downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        Statement st = null;
        PreparedStatement ps = null;
        Connection con = DatabaseConnectionFactory.createConnection();
        ResultSet rs = null;
        ArrayList<EmpResult2> empRList2 = new ArrayList<EmpResult2>();
        
        con = DatabaseConnectionFactory.createConnection();
        String query = "select m.emp_name, m.emp_code, m.loc_code, r.marks, r.time_duration from mst_result r inner join emp_master m on m.emp_code = r.emp_code order by marks desc,TIME_DURATION asc";
        try{
            st=con.createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                EmpResult2 emps= new EmpResult2();
                emps.setEmp_name(rs.getString(1));
                emps.setEmp_code(rs.getString(2));
                emps.setLoc_code(rs.getString(3));
                emps.setMarks(String.valueOf(rs.getInt(4)));
                emps.setDuration(String.valueOf(rs.getInt(5)));
                empRList2.add(emps);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
        return empRList2;
    }
}
