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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OraclePreparedStatement;


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
                
                String sql1 = "DELETE FROM QUESTION_MASTERU";
                st.execute(sql1);
                
                int totalQuestion = qtnExcelList.size();
                String sqln = "update control_tableu set p_value = "+totalQuestion+" where param = 'total_no_questions'";
                st.execute(sqln);
                
//                for (questionExcelModel qtnList : qtnExcelList) {
//                    String sql = "Select MAX(QUSE_ID) from QUESTION_MASTERU";
//                    ResultSet rs = st.executeQuery(sql);
//                    while(rs.next()){
//                        int a = rs.getInt(1);
//                        count= a+1;
//                    }
//                    
//                    if(qtnList.getQUES_DESC()!=null||!qtnList.getQUES_DESC().equals("")){
//                        sql2 = "Insert into QUESTION_MASTERU (QUSE_ID,QUES_DESC,OPTION1,OPTION2,OPTION3,OPTION4,CORRECT_OP,UPDATE_FLAG) values ("+count+",'"+qtnList.getQUES_DESC() +"','"+qtnList.getOPTION1()+"','"+qtnList.getOPTION2() +"','"+ qtnList.getOPTION3() +"','"+qtnList.getOPTION4() +"','"+qtnList.getCORRECT_OP().charAt(0) +"','"+qtnList.getUPDATE_FLAG() +"')";
//                    }else{
//                        System.out.println(count+" is invalid");
//                    }
//                    //sql2="Insert into QUESTION_MASTERU (QUSE_ID,QUES_DESC,OPTION1,OPTION2,OPTION3,OPTION4,CORRECT_OP,UPDATE_FLAG) values (65,'Test question','test op1','test op2','test op3','test op4','1','A')";
//                    System.out.println(sql2);
//                    st.executeQuery(sql2);
//                }

                for (questionExcelModel qtnList : qtnExcelList) {
                    String sql = "Select MAX(QUSE_ID) from QUESTION_MASTERU";
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        int a = rs.getInt(1);
                        count= a+1;
                    }
                    
                    if(qtnList.getQUES_DESC()!=null||!qtnList.getQUES_DESC().equals("")){
                        sql2 = "Insert into QUESTION_MASTERU (QUSE_ID,QUES_DESC,OPTION1,OPTION2,OPTION3,OPTION4,CORRECT_OP,UPDATE_FLAG) values (?,?,?,?,?,?,?,?)";
                    }else{
                        System.out.println(count+" is invalid");
                    }
                    PreparedStatement pstmt = con.prepareStatement(sql2);
                    pstmt.setInt(1, count);
                    ((OraclePreparedStatement) pstmt).setFormOfUse(2, OraclePreparedStatement.FORM_NCHAR);
                    pstmt.setString(2, qtnList.getQUES_DESC());
                    ((OraclePreparedStatement) pstmt).setFormOfUse(3, OraclePreparedStatement.FORM_NCHAR);
                    pstmt.setString(3, qtnList.getOPTION1());
                    ((OraclePreparedStatement) pstmt).setFormOfUse(4, OraclePreparedStatement.FORM_NCHAR);
                    pstmt.setString(4, qtnList.getOPTION2());
                    ((OraclePreparedStatement) pstmt).setFormOfUse(5, OraclePreparedStatement.FORM_NCHAR);
                    pstmt.setString(5, qtnList.getOPTION3());
                    ((OraclePreparedStatement) pstmt).setFormOfUse(6, OraclePreparedStatement.FORM_NCHAR);
                    pstmt.setString(6, qtnList.getOPTION4());
                    pstmt.setString(7, qtnList.getCORRECT_OP());
                    pstmt.setString(8, qtnList.getUPDATE_FLAG());
                    pstmt.executeUpdate();
                }




                response.setContentType("application/json");
                response.getWriter().write("success");
                con.close();
            } catch (SQLException e ) {
                e.printStackTrace();
            }finally{
                if (con != null) {
                    
                    try { con.close();} catch (Exception e) {  } 
                }
            }
    }
}
