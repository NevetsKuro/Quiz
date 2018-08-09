package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.EmpResults;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class empResultDetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        Connection con = null;
        try {
            con = DatabaseConnectionFactory.createConnection();
            Statement st = con.createStatement();
            
            ArrayList<EmpResults> empR = new ArrayList<EmpResults>();
            
            String sql = "Select * from MST_RESULT";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                EmpResults e = new EmpResults();
                e.setEmp_code(rs.getString(1));
                e.setResultMarks(String.valueOf(rs.getInt(2)));
                e.setTimeTaken(String.valueOf(rs.getInt(3)));
                e.setHasSubmitted(rs.getString(4));
                empR.add(e);
            }
            con.close();
            new Gson().toJson(empR, response.getWriter());
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }   
    }
}
