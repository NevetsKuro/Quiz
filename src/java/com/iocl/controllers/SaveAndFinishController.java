/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.QuizQuestion;
import globals.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iocl.quiz.questions;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/saveorfinish")
public class SaveAndFinishController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Connection con = null;
        int noOfQuestions = 0;
        List<String> list;        
        HashMap<String,Integer> hmlist;
        int quesNumber ;
        try {
            HttpSession session = request.getSession();
            ArrayList<QuizQuestion> quizList = ((ArrayList<QuizQuestion>) session.getAttribute("ques_list_details")); // question with details
            noOfQuestions = quizList.size();
            
            String myJsonData = request.getParameter("json"); // list of objects [{ quesNo:"", quesAns:""  }]
            Gson gson = new Gson();
            List<questions> qlist;
            Type listType =  new TypeToken<List<questions>>(){}.getType();
            qlist = gson.fromJson(myJsonData,listType);
            
            String action = request.getParameter("action"); // Save or Submit
            con = DatabaseConnectionFactory.createConnection();
            Statement st = con.createStatement();
            User user = ((User) request.getSession().getAttribute("emp"));
            String empid = user.getUserid();
            
            if (action.equals("Save")) {                    
                String sSubmitStatus = "No";
                saveAnsDetails(st, con,qlist, noOfQuestions, empid);
                int marks = calculateMarks(quizList, qlist,request);
                long timeDuarationSeconds = checkLogOutTimestamp(request);
                
                String sq2 = "DELETE FROM MST_RESULT where emp_code = '" + empid + "'";
                st.executeUpdate(sq2);
                String sql = "INSERT INTO mst_result(EMP_CODE,MARKS,TIME_DURATION,SUBMIT_STATUS) values ('" + empid + "','" + marks + "','" + timeDuarationSeconds + "','" + sSubmitStatus + "')";
                st.executeUpdate(sql);
                con.close();
                
                session.setAttribute("timeDuration", timeDuarationSeconds);
                list = new ArrayList<String>();
                list.add("success");
                con.close();
                String json = new Gson().toJson(list);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
//                request.getRequestDispatcher("jsps/exam.jsp").forward(request, response);

            } else if (action.equals("Submit")) {
                String sSubmitStatus = "Yes";
                saveAnsDetails(st, con, qlist, noOfQuestions, empid);
                int marks = calculateMarks(quizList, qlist,request);
                long timeDuarationSeconds = checkLogOutTimestamp(request);
                
                String sq2 = "DELETE FROM MST_RESULT where emp_code = '" + empid + "'";
                st.executeUpdate(sq2);
                String sql = "INSERT INTO mst_result(EMP_CODE,MARKS,TIME_DURATION,SUBMIT_STATUS) values ('" + empid + "','" + marks + "','" + timeDuarationSeconds + "','" + sSubmitStatus + "')";
                st.executeUpdate(sql);

                String sql1 = "Select * from mst_result order by marks desc";
                ResultSet rs = st.executeQuery(sql1);
                int heighest_marks = 0;
                if (rs.next()) {
                    heighest_marks = rs.getInt("MARKS");
                }
                con.close();
                
//                request.getRequestDispatcher("jsps/result.jsp").forward(request, response);
              
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }

    private int calculateMarks(ArrayList<QuizQuestion> quizList, List<questions> qlist,HttpServletRequest request) {
        
        int icorrect_ans_mark =Integer.parseInt((String) request.getSession().getAttribute("correct_ans_mark")) ;
        int iwrong_ans_mark =Integer.parseInt((String) request.getSession().getAttribute("wrong_ans_mark"));
        int marks = 0;
        for (int i = 0; i < quizList.size(); i++) {
            if(i<qlist.size())
            {    
                if (Integer.parseInt(qlist.get(i).getQuesAns()) == quizList.get(i).getCorrectOptionIndex()) {
                   // marks++;
                    marks = marks+icorrect_ans_mark;
                }
                else if(Integer.parseInt(qlist.get(i).getQuesAns()) == 0)
                {
                    marks = marks;
                }
                else
                {
                    marks = marks-iwrong_ans_mark;
                }
            }
        }
        return marks;
    }

    private long checkLogOutTimestamp(HttpServletRequest request) {
        long seconds = 0;
        String login_timestamp = (String) request.getSession().getAttribute("login_timestamp");
        Long quiz_time = ((Long) request.getSession().getAttribute("quiz_time"));
        long quiz_time_secs = Long.valueOf(quiz_time);
        try {
            Connection con = DatabaseConnectionFactory.createConnection();
            Statement st = con.createStatement();

            //HttpSession session=request.getSession();
            User user = ((User) request.getSession().getAttribute("emp"));
            String empid = user.getUserid();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String logout_timeStamp = format.format(new Date());

            Date startDate = format.parse(login_timestamp);
            Date endDate = format.parse(logout_timeStamp);
            if ((endDate.getTime() - startDate.getTime()) > 0) {
                seconds = (endDate.getTime() - startDate.getTime()) / 1000;
                if (quiz_time_secs < seconds) {
                    seconds = quiz_time_secs;
                }
            } else {
                seconds = 0;
            }

            String sql1 = "DELETE FROM LOGOUT_DETAILS where emp_code = '" + empid + "'";
            st.executeUpdate(sql1);

            String sql2 = "INSERT INTO LOGOUT_DETAILS(EMP_CODE, LOGIN_TIMESTAMP, LOGOUT_TIMESTAMP,TIME_DURATION) VALUES"
                    + " ('" + empid + "','" + login_timestamp + "','" + logout_timeStamp + "','" + seconds + "')";
            st.executeUpdate(sql2);
            st.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return seconds;
    }

    private void saveAnsDetails(Statement st, Connection con, List<questions> qlist,
            int noOfQuestions, String empid) {

        try {
            String sql = "DELETE FROM ans_details where emp_code = '" + empid + "'";
            st.executeUpdate(sql);
            String sq2 = "DELETE FROM MST_RESULT where emp_code = '" + empid + "'";
            st.executeUpdate(sq2);

            //int j;
            for (int i = 0; i < noOfQuestions; i++) {
                String sql1;
                sql1 = "INSERT INTO ANS_DETAILS(EMP_CODE,QUES_ID,OPTION_SELECTED) values ('" + empid + "','" + qlist.get(i).getQuesNo() + "','" + qlist.get(i).getQuesAns() + "')";
                st.executeUpdate(sql1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
