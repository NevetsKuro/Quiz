/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.EmpResults;
import com.iocl.quiz.questionExcelModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class questionDetails extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        Connection con = null;
        try {
            con = DatabaseConnectionFactory.createConnection();
            Statement st = con.createStatement();
            
            ArrayList<questionExcelModel> empQ = new ArrayList<questionExcelModel>();
            
            String sql = "Select * from question_masteru";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                questionExcelModel e = new questionExcelModel();
                e.setQUES_DESC(rs.getString(2));
                e.setOPTION1(rs.getString(3));
                e.setOPTION2(rs.getString(4));
                e.setOPTION3(rs.getString(5));
                e.setOPTION4(rs.getString(6));
                e.setCORRECT_OP(rs.getString(7));
                empQ.add(e);
            }
            con.close();
            new Gson().toJson(empQ, response.getWriter());
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }

}
