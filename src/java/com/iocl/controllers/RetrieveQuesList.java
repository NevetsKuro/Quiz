package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.questions;
import globals.User;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

public class RetrieveQuesList extends HttpServlet {

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        Connection con=DatabaseConnectionFactory.createConnection();
        try {
            
            Statement st=con.createStatement();

            HttpSession session=request.getSession();
            User user = ((User)session.getAttribute("emp"));
            String empid = user.getUserid();

            String sql = "Select * from ans_details where emp_code="+empid;
            ResultSet rs = st.executeQuery(sql);
            List<questions> hmAns = new ArrayList<questions>(); 
            questions q;
            while (rs.next()) {
                q = new questions();
                q.setQuesNo(String.valueOf(rs.getInt(2)));
                q.setQuesAns(String.valueOf(rs.getInt(3)));
                hmAns.add(q);
            }
            con.close();
            Gson gson = new Gson();
            gson.toJson(hmAns, response.getWriter());
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }
}
