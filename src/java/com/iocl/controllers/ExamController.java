package com.iocl.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iocl.quiz.DatabaseConnectionFactory;
import com.iocl.quiz.QuizQuestion;
import com.iocl.quiz.TimeUtil;
import globals.User;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;

@WebServlet("/exam")
public class ExamController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO Auto-generated method stub
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con=DatabaseConnectionFactory.createConnection();
        try{
            
            Statement st=con.createStatement();

            HttpSession session=request.getSession();
            String mFlag = (String)request.getSession().getAttribute("mflag");
            if(mFlag == "ON"){
                request.getRequestDispatcher("jsps/error503.jsp").forward(request, response);
            }
            User user = ((User)request.getSession().getAttribute("emp"));
            String empid = user.getUserid();

            checkLoginTimestamp(st, empid, request); // punching time stamp
            
            String query = "SELECT * FROM mst_result where emp_code = '"+empid+"'";
            ResultSet rset = st.executeQuery(query);
            
            String submitStatus="";
            if(rset.next())
            {
                submitStatus=rset.getString("submit_status");
            }
            
            if(submitStatus.equals("Yes"))
            {
                request.getRequestDispatcher("jsps/alreadyTaken.jsp").forward(request,response);
            }
            rset.close();
            
            String end_time1 = ((String) request.getSession().getAttribute("end_time"));
            
            if(TimeUtil.getDateDiff(end_time1, TimeUnit.SECONDS) > 0){
                request.getRequestDispatcher("jsps/timeOver.jsp").forward(request, response);
            }else{
                Boolean lock = check_ques_set(empid,st);//check if question set exist in database return true
                String sql4 = "";
                 if(lock){
                    String exist = "";
                    exist = (String)request.getSession().getAttribute("randListSql");
                    if(exist==""||exist==null){
                        sql4 = getQuesNoList(empid,st,request);
                    }else{
                        sql4 = exist;
                    }    
                }
                else{
                    //generate a random number list
                    sql4 = generateRandomQuestionList(request,empid);
                    String sql = "Update login_details set QUES_LOCK=1 where emp_code="+empid;
                    st.executeUpdate(sql);
                }

                ResultSet rs_qlist_details = st.executeQuery(sql4);
                QuizQuestion question;
                ArrayList<QuizQuestion> questions = new ArrayList<QuizQuestion>();
                ArrayList<String> options = new ArrayList<String>();
                while(rs_qlist_details.next()){

                    question =  new QuizQuestion();
                    questions.add(question);
                    question.setQuestionNumber(rs_qlist_details.getInt("QUSE_ID"));
                    question.setQuestion(rs_qlist_details.getString("QUES_DESC"));
                    question.setCorrectOptionIndex(rs_qlist_details.getInt("CORRECT_OP"));
                    options = new ArrayList<String>();
                    options.add(rs_qlist_details.getString("OPTION1"));
                    options.add(rs_qlist_details.getString("OPTION2"));
                    options.add(rs_qlist_details.getString("OPTION3"));
                    options.add(rs_qlist_details.getString("OPTION4"));
                    question.setQuestionOptions(options);
                }

                Collections.sort(questions, COMPARATOR);
    //            for (QuizQuestion ques : questions){
    //                System.out.println("After sorting on price: " + ques.getQuestionNumber());
    //            }

                session.setAttribute("ques_list_details", questions); //list of all question with details
    //            Gson gson = new Gson().toJson(questions, response.getWriter());
    //            String quiztime = (String)request.getSession().getAttribute("quest");
    //            new Gson().toJson(quiztime,response.getWriter());
                con.close();
                request.getRequestDispatcher("jsps/exam.jsp").forward(request,response);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (con != null) {
                try { con.close(); } catch (Exception e) {  } 
            }
        }
    }	
    
    private Comparator<QuizQuestion> COMPARATOR = new Comparator<QuizQuestion>()
    {
        public int compare(QuizQuestion q1, QuizQuestion q2)
        {
            return q1.getQuestionNumber()- q2.getQuestionNumber();
        }
    };
    
    private  String generateRandomQuestionList(HttpServletRequest request,String empid)throws ServletException, IOException,SQLException{
            
        int randomInt=0;
        Long quiz_No_Of_Questions = (Long)request.getSession().getAttribute("quiz_no_of_questions");
        long NoOfQuizQuestions = Long.valueOf(quiz_No_Of_Questions);
        Integer total_no_of_questions = ((Integer)request.getSession().getAttribute("total_no_questions"));
        int TotalNoOfQuestions = Integer.valueOf(total_no_of_questions);
        ArrayList<Integer> randList=new ArrayList<Integer>();
        while(1==1)
        {
            Random rand = new Random();
            randomInt =(int) (Math.random()*TotalNoOfQuestions);
            if(randList.size()==NoOfQuizQuestions)
            {
                break;
            }
            if(randomInt>0 && randomInt<TotalNoOfQuestions)
            {
                randList=checkDistinctNumberInList(randList,randomInt);
            }
        }
        String sql4 = "Select * from question_masteru where quse_id in (";
        for (Integer integer : randList) {
            sql4=sql4.concat(integer+",");
        }
        sql4 = sql4.substring(0, sql4.length() - 1);
        sql4+=")";
        if(randList.size()>0){
        request.getSession().setAttribute("randListSql", sql4);
        }
        String a=String.valueOf(NoOfQuizQuestions);
        int ques_list = Integer.parseInt(a);
        saveAnsDetails(randList,ques_list,empid);
        return sql4;
    }
    
    private ArrayList<Integer> checkDistinctNumberInList( ArrayList<Integer> list,int number)
    {
        int flag=0;
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)==number)
            {
                flag=1;
                break;
            }
        }
        if(flag==0)
        {
            list.add(number);
        }
        
        return list;
    }
    
    private void checkLoginTimestamp(Statement stmt, String empid, HttpServletRequest request){
        boolean flag = false;
        try{
            String query = "SELECT * FROM LOGIN_DETAILS where emp_code = '"+empid+"'"; 
            ResultSet rset = stmt.executeQuery(query);
            if(rset.next()){ //if login stamp exists
                flag = true;
                request.getSession().setAttribute("login_timestamp", rset.getString("LOGIN_TIMESTAMP"));
            }
            rset.close();
            if(!flag){ // if the login stamp doesnt exists
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String timeStamp = format.format(new Date());
                String sql = "INSERT INTO LOGIN_DETAILS(EMP_CODE, LOGIN_TIMESTAMP) VALUES"
                        + " ('"+empid+"','"+timeStamp+"')";
                stmt.executeUpdate(sql);
                request.getSession().setAttribute("login_timestamp", timeStamp);
            }
          
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private Boolean check_ques_set(String empid, Statement st) throws SQLException,IOException{
        String sql5 = "Select QUES_LOCK from login_details where emp_code="+empid;
        ResultSet rs_check = st.executeQuery(sql5);//question lock
        int check_flag = 0;
        while (rs_check.next()) {            
            check_flag = rs_check.getInt(1);
        }
        if(check_flag == 1){
            return true;
        }else{
            return false;
        }
    }

    private void saveAnsDetails( ArrayList<Integer> radioList,int noOfQuestions, String empid) throws SQLException,IOException{

        Connection con=DatabaseConnectionFactory.createConnection();
        Statement st=con.createStatement();
        String sql = "DELETE FROM ans_details where emp_code = '" + empid + "'";
        st.executeUpdate(sql);
        String sq2 = "DELETE FROM MST_RESULT where emp_code = '" + empid + "'";
        st.executeUpdate(sq2);

        System.out.println(sql);
        System.out.println(sq2);
        //int j;
        for (int i = 0; i < noOfQuestions; i++) {
            String sql1="";
            if(i<radioList.size()){
                sql1 = "INSERT INTO ANS_DETAILS(EMP_CODE,QUES_ID,OPTION_SELECTED) values ('" + empid + "','" + radioList.get(i) + "',0)";
            }
            st.executeUpdate(sql1);
        }
    }

    private String getQuesNoList(String empid,Statement st,HttpServletRequest request)throws SQLException{
        String sql = "Select * from ans_details where emp_code="+empid;
        ResultSet rs = st.executeQuery(sql);
        String sql2 = "Select * from question_masteru where quse_id in (";
        while (rs.next()) {
            sql2=sql2.concat(rs.getInt(2)+",");
        }
        sql2 = sql2.substring(0, sql2.length() - 1);
        sql2+=")";
        request.getSession().setAttribute("randListSql", sql2);
        return sql2;
    }
}