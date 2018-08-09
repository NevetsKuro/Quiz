/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import globals.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wrtrg2
 */
public class getResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Connection con=DatabaseConnectionFactory.createConnection();
          Statement st= null;
        try {
           
          st=con.createStatement();
            HttpSession session=req.getSession();
            User user = ((User)req.getSession().getAttribute("emp"));
            int quiz_time = Integer.parseInt(String.valueOf(req.getSession().getAttribute("quiz_no_of_questions")));
            String cor_marks = ((String)req.getSession().getAttribute("correct_ans_mark"));
            int tmarks = quiz_time * Integer.valueOf(cor_marks);
            String empid = user.getUserid();
            
            String sql = "Select * from mst_result where emp_code="+empid;
            ResultSet rs = st.executeQuery(sql);
            
            HashMap<String,String> hmResult = new HashMap<String,String>();
            hmResult.put("total_marks",String.valueOf(tmarks));
            while(rs.next()){
                hmResult.put("marks",String.valueOf( rs.getInt(2)));
                hmResult.put("timeTaken",String.valueOf( rs.getInt(3)));
            }
            
            String sql1 = "Select MAX(MARKS) from mst_result";
            ResultSet rs1 = st.executeQuery(sql1);
            
            while(rs1.next()){
                hmResult.put("highest_marks",String.valueOf( rs1.getInt(1)));
            }
            //con.close();
            Gson gson = new Gson();
            gson.toJson(hmResult, resp.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }

}
