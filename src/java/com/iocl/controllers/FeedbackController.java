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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FeedbackController extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String name = request.getParameter("fname");
        String rating = request.getParameter("fratings");
        String comment = request.getParameter("fcomment");
        
        HttpSession session=request.getSession();
        Statement st = null;
        PreparedStatement ps = null;
        Connection con = DatabaseConnectionFactory.createConnection();
        
        con = DatabaseConnectionFactory.createConnection();
        User user = ((User)request.getSession().getAttribute("emp"));
        String empid = user.getUserid();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String timeStamp = format.format(new Date());
        String query = "INSERT INTO FEEDBACK(EMP_CODE,NAME,LOGINSTAMP,RATING,COMMENTS) VALUES ('"+empid+"','"+name+"','"+timeStamp+"','"+rating+"','"+comment+"')";
        try {
            st=con.createStatement();
            st.executeUpdate(query);
            con.close();
            new Gson().toJson("success", response.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }

}
