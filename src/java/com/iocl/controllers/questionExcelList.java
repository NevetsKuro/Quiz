/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.questionExcelModel;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class questionExcelList extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Gson gson = new Gson();
        String QuestionJSON = request.getParameter("json");
        
        Type listType =  new TypeToken<List<questionExcelModel>>(){}.getType();
        List<questionExcelModel> qtnExcelList = gson.fromJson(QuestionJSON,listType);
        
        Connection con = null;
            Statement st;
            int count = 0;
            String sql2 = "";
            System.out.println(qtnExcelList);
            try{
                con = DatabaseConnectionFactory.createConnection();
                st = con.createStatement();
                
//                String sql1 = "DELETE * FROM emp_master";
//                st.execute(sql1);
                
                for (questionExcelModel qtnList : qtnExcelList) {
                    String sql = "Select MAX(QUSE_ID) from QUESTION_MASTERU";
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        int a = rs.getInt(1);
                        count= a+1;
                    }
                   // String sql1 = "Insert into AMCSPARES (SPARE_ID,ITEM,MAKE,MODEL,SERIAL_NO,RAM,HDD,PRINTER_TYPE,NO_OF_PORTS,VALUE,DATE_IN,LOCATION,STATUS,ASSIGNED_TO,ASSIGNED_DATE,COMPLAINT_ID,ASSET_ID,ITEM_PARENTS,ASSET_STATUS,PART_REMARKS) values (?,?,?,?,?,?,?,?,?,?,TO_DATE(?, 'YYYY--MM--DD'),?,?,?,?,?,?,?,?,?);";
                    sql2 = "Insert into QUESTION_MASTERU (QUSE_ID,QUES_DESC,OPTION1,OPTION2,OPTION3,OPTION4,CORRECT_OP,UPDATE_FLAG) values ("+count+",'"+qtnList.getQUES_DESC() +"','"+qtnList.getOPTION1()+"','"+qtnList.getOPTION2() +"','"+ qtnList.getOPTION3() +"','"+qtnList.getOPTION4() +"','"+qtnList.getCORRECT_OP().charAt(0) +"','"+qtnList.getUPDATE_FLAG() +"')";
                    //sql2="Insert into QUESTION_MASTERU (QUSE_ID,QUES_DESC,OPTION1,OPTION2,OPTION3,OPTION4,CORRECT_OP,UPDATE_FLAG) values (65,'Test question','test op1','test op2','test op3','test op4','1','A')";
                    System.out.println(sql2);
                    st.executeQuery(sql2);
                }
                response.setContentType("application/json");
                response.getWriter().write("success");
                con.close();
            } catch (SQLException e ) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    try { con.close(); } catch (Exception e) {  } 
                }
            }
        
    }

    
}
