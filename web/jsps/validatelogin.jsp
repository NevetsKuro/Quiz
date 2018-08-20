<%@page import="java.text.SimpleDateFormat"%>
<%@page import="globals.initializeInstruction"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--@ page session="true" --%>
<%--@ page import="Tools.authentication"--%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.*, com.iocl.quiz.DatabaseConnectionFactory" %>
<%@ page import="globals.DBService,globals.Constants" %>
<%@ page import="com.iocl.quiz.TimeUtil"%>
<%@ page import="globals.User" %>
<%@ page import="connect.*"%>
<%@ page import="config.*" import="sessions.*" %><head>
    <meta http-equiv="PRAGMA" content="NO-CACHE" />
</head>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    response.setDateHeader("Expires", -1);

    String userid = "0";
    String pwd = "", fpwd = null;
    String username = "XXX";
    String region = "XXX";
    String loc = "XXX";
    String so_office = "XXX";
    String mail_id = "XXX";
    String function = "0";
    String reg_flag = "N", so_flag = "N", loc_flag = "N", emp_category = "1";
    int role, role_rep = 4, err = 0;
    role = 0;
    Connection con = null, conLP = null;
    Statement stmt = null;
    Statement stmt2 = null;
    ResultSet rs = null, rs1 = null, rs2 = null;
    String redirectURL = null;

    long authId = 0;
    snConfigVars V = new snConfigVars();

    if (V.isCASenabled()) {
        pwd = null;

        userid = "" + snAuthentications.getAuthenticationId_Numeric(request);
        try {
            authId = Long.parseLong(userid);
        } catch (Exception ee) {
            authId = Long.parseLong(userid);
        }
    } else {
        authId = Long.parseLong(userid);
        pwd = request.getParameter("passwd");
    }
    boolean userOK = false, pwdOK = false;
    boolean redirect2Login = false;
    System.out.print("user123" + userid);

    //Connection con_AD = null;
    String result = "error";
    String resultDetails = "error", user_name = "";//details
     String errorCode = "0";
    String res = "Wrong UserID/Password";
    String smess = "";
    try {

        if (authId > 0) {
            String fname;
            String lname;
            String name;
            String desg;
            String asd;

            globals.User user = null;
            if (userid.length() > 0) {
                try {
                    while (userid.length() < 8) {
                        userid = "0" + userid;
                    }
                    user = new globals.User();
                    globals.DBService dbservice = new globals.DBService();
                    user.setIsloggedin(true);
                    user.dbCon = dbservice.getConnection();
                    if (dbservice.isconnectionvalid == false || user.dbCon == null) {
                        smess = "There is an error connecting to Application Database .Please inform IS Dept" + dbservice.sErrorMsg;
                        user = null;
                        dbservice = null;
%>

<div align="center">
    <img src="../images/dialog-error.png" width="31" height="31" alt="Open Form" />  <h5>Although you have valid Central Login Credentials , But access is denied...</h5>
    Reason:<br/>
    <%=smess%>
</div>

<%
        return;
    }
    user.setPfnumber(userid);
    if (!user.initialiseUser()) {
        smess = "You are not a Authorised User to access Quiz System.Your user ID was: " + userid + " .<br/><b> Please inform respective Dept</b>";
        user = null;
        dbservice = null;
%>
<div align="center">
    <img src="../images/dialog-error.png" width="31" height="31" alt="Open Form" />   <h5>Although you have valid Central Login Credentials , But access is denied...</h5>
    Reason:<br/>
    <%=smess%>
</div>
<%
        return;
    }

    String userAgent = request.getHeader("user-agent");
    if (userAgent.indexOf("MSIE") > -1) {
        String site_link = "";
        PreparedStatement ps;
        ps = user.dbCon.prepareStatement("Select PARAM, P_VALUE from CONTROL_TABLEU where PARAM='Site_Link'");
        rs = ps.executeQuery();
        if (rs.next()) {
            site_link = rs.getString(2);
        }
        user.dbCon.close();


%>
<div align="left">
    <h1 style="color:red;">This is not compatible with Internet Explorer.<br/> Kindly copy the below link and open in Google Chrome or Mozilla!</h1>
    <h2><%=site_link%></h2>
</div>
<%                        return;
                        // user.dbCon.close();
                        // RequestDispatcher rd = request.getRequestDispatcher("compatibleIssueIE.jsp");
                        //rd.forward(request, response);
                    }

                    String sql = "select emp_code as emp_code,emp_name as emp_name,EMP_DESIG,ROLE "
                            + "from emp_master"
                            + " where "
                            + " lpad(emp_code,8,0)=lpad('" + user.getPfnumber() + "',8,0)";

                    String sql2 = "select PARAM, P_VALUE "
                            + "from CONTROL_TABLEU"
                            + " where "
                            + " PARAM in ('s_datetime','e_datetime')";

                    String sql3 = "select * "
                            + "from MST_RESULT"
                            + " where "
                            + " lpad(emp_code,8,0)=lpad('" + user.getPfnumber() + "',8,0)";

                    String sql4 = "select * "
                            + "from MST_RESULT"
                            + " order by marks desc ";

                    String sql5 = "SELECT * "
                            + "FROM LOGIN_DETAILS "
                            + "where "
                            + "lpad(emp_code,8,0)=lpad('" + user.getPfnumber() + "',8,0)";

                    String sql6 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'quiz_time'";

                    String sql7 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'quiz_no_questions'";

                    String sql8 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'quiz_name'";

                    String sql9 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'total_no_questions'";

                    String sql10 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'random_flag'";
                    
                    String sql11 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'correct_ans_mark'";
                    
                    String sql12 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'wrong_ans_mark'";

                    String sql13 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'result_flag'";

                    String sql14 = "SELECT P_VALUE "
                            + "FROM CONTROL_TABLEU "
                            + "where PARAM = 'Maintence_flag'";


                    PreparedStatement ps;
                    boolean isValidUser = false;
                    boolean hasPermission = false;
                    try {

                        ps = user.dbCon.prepareStatement(sql);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            session = request.getSession();
                            user.setName(rs.getString("EMP_NAME"));
                            user.setUserid(rs.getString("EMP_CODE"));
                            user.setDesignation(rs.getString("EMP_DESIG"));
                            session.setAttribute("emp", user);
                            session.setAttribute("ROLE", rs.getString("ROLE"));
//                            String res_flag = (String) request.getAttribute("flag_res");
//                            if (res_flag != null && res_flag.equalsIgnoreCase("ON")) {
                                // RequestDispatcher rd=request.getRequestDispatcher("quizres.jsp");
                                // rd.forward(request, response);
//                            } else if (res_flag != null && res_flag.equalsIgnoreCase("OFF")) {
                                // RequestDispatcher rd=request.getRequestDispatcher("new.jsp");
                                // rd.forward(request, response);
//                            }
                            isValidUser = true;
                        }

                        ps = user.dbCon.prepareStatement(sql2);
                        rs = ps.executeQuery();
                        String time[] = new String[2];
                        while (rs.next()) {
                            if (rs.getString("PARAM").equalsIgnoreCase("s_datetime")) {
                                time[0] = rs.getString("P_VALUE");
                            } else {
                                time[1] = rs.getString("P_VALUE");
                            }
                        }

                        ps = user.dbCon.prepareStatement(sql5);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            String timeStamp = TimeUtil.convertToJSDate(rs.getString("LOGIN_TIMESTAMP"));
                            if (TimeUtil.getTimeDiff(timeStamp, time[1], TimeUnit.SECONDS) > 0) {
                                hasPermission = true;
                            }
                        }

                        Long qtime = 0l;

                        ps = user.dbCon.prepareStatement(sql6);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            qtime = Long.parseLong(rs.getString("P_VALUE")) * 60;
                        }
                        Long quizNoOfQuestions = 0l;
                        ps = user.dbCon.prepareStatement(sql7);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            quizNoOfQuestions = Long.parseLong(rs.getString("P_VALUE"));
                        }
                        String strQuizName = "";
                        ps = user.dbCon.prepareStatement(sql8);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            strQuizName = rs.getString("P_VALUE");
                        }

                        Integer total_no_of_questions = 0;
                        ps = user.dbCon.prepareStatement(sql9);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            total_no_of_questions = rs.getInt("P_VALUE");
                        }

                        String randomFlag = "";
                        ps = user.dbCon.prepareStatement(sql10);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            randomFlag = rs.getString("P_VALUE");
                        }
                        
                        String resultFlag = "";
                        ps = user.dbCon.prepareStatement(sql13);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            resultFlag = rs.getString("P_VALUE");
                        }

                        String correctAnsMark = "";
                        ps = user.dbCon.prepareStatement(sql11);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            correctAnsMark = rs.getString("P_VALUE");

                        }
                        
                        String wrongAnsMark = "";
                        ps = user.dbCon.prepareStatement(sql12);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            wrongAnsMark = rs.getString("P_VALUE");

                        }

                        String m_flag = "";
                        ps = user.dbCon.prepareStatement(sql14);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            m_flag = rs.getString("P_VALUE");
                        }

                        
                        session.setAttribute("start_time", time[0]);
                        session.setAttribute("end_time", time[1]);
                        session.setAttribute("quiz_time", qtime);
                        session.setAttribute("quiz_no_of_questions", quizNoOfQuestions);
                        session.setAttribute("quiz_name", strQuizName);
                        session.setAttribute("total_no_questions", total_no_of_questions);
                        session.setAttribute("random_flag", randomFlag);
                        session.setAttribute("result_flag", resultFlag);
                        session.setAttribute("correct_ans_mark", correctAnsMark);
                        session.setAttribute("wrong_ans_mark", wrongAnsMark);
                        session.setAttribute("now_date", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
                        session.setAttribute("mflag", m_flag);
                        long yetTime = 0;
                        
 //                       yetTime = TimeUtil.getTimeDiff( String.valueOf(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date())),time[0], TimeUnit.SECONDS);
                        yetTime = TimeUtil.getDateDiff(time[0], TimeUnit.SECONDS);

                        if(yetTime<0){
                            session.setAttribute("time_Remaining", Math.abs(yetTime)); //Time Remaining For the test to start
                        }

                        if(m_flag.equals("ON")&&session.getAttribute("ROLE").toString().equals("USER")){
                            RequestDispatcher rd = request.getRequestDispatcher("error503.jsp");
                            rd.forward(request, response);
                        }else if(m_flag.equals("OFF")||session.getAttribute("ROLE").toString().equals("ADMIN")){
                        if (isValidUser) {

                            if (TimeUtil.getDateDiff(time[1], TimeUnit.SECONDS) > 0 && !hasPermission) {
                                ps = user.dbCon.prepareStatement(sql3);
                                rs = ps.executeQuery();
                                String status1 = "";
                                if (rs.next()) {
                                    status1 = rs.getString("SUBMIT_STATUS");
                                    user.dbCon.close();
                                } else {
                                    status1 = "";
                                    user.dbCon.close();
                                }

                                if (status1.equals("Yes")) {
                                    RequestDispatcher rd = request.getRequestDispatcher("alreadyTaken.jsp");
                                    rd.forward(request, response);
                                } else if (status1.equals("No")) //in this case default it will be No as status can be either Yes or No 
                                {
                                    RequestDispatcher rd = request.getRequestDispatcher("takenNotSubmitted.jsp");
                                    rd.forward(request, response);
                                } else {
                                    RequestDispatcher rd = request.getRequestDispatcher("timeOver.jsp");
                                    rd.forward(request, response);
                                }

                            } else {
                                ps = user.dbCon.prepareStatement(sql3);
                                rs = ps.executeQuery();
                                String end_time1 = ((String) request.getSession().getAttribute("end_time"));
                                if (rs.next()) {
                                    String status = rs.getString("SUBMIT_STATUS");
                                    user.dbCon.close();

                                    if (TimeUtil.getDateDiff(end_time1, TimeUnit.SECONDS) > 0) {
                                        if (status.equals("Yes")) {
                                            RequestDispatcher rd = request.getRequestDispatcher("alreadyTaken.jsp");
                                            rd.forward(request, response);
                                        } else if (status.equals("No")) {
                                            RequestDispatcher rd = request.getRequestDispatcher("takenNotSubmitted.jsp");
                                            rd.forward(request, response);
                                        } else {
                                            RequestDispatcher rd = request.getRequestDispatcher("timeOver.jsp");
                                            rd.forward(request, response);
                                        }
                                    } else {
                                        if (status.equals("Yes")) {
                                            RequestDispatcher rd = request.getRequestDispatcher("alreadyTaken.jsp");
                                            rd.forward(request, response);
                                        } else {
                                            System.out.println("instruction");
                                            request.setAttribute("quizInstructions", initializeInstruction.getQuizInstructions());
                                            RequestDispatcher rd = request.getRequestDispatcher("instructions.jsp");
                                            rd.forward(request, response);
                                        }
                                    }
                                } else {
                                    user.dbCon.close();
                                    if (TimeUtil.getDateDiff(end_time1, TimeUnit.SECONDS) > 0) {
                                        RequestDispatcher rd = request.getRequestDispatcher("timeOver.jsp");
                                        rd.forward(request, response);

                                    } else {
                                        System.out.println("instruction");
                                        request.setAttribute("quizInstructions", initializeInstruction.getQuizInstructions());
                                        System.out.println("instruction set");
                                        RequestDispatcher rd = request.getRequestDispatcher("instructions.jsp");
                                        rd.forward(request, response);
                                    }
                                }
                                rs.close();
                            }
                        }
                        }   
                        rs.close();
                        user.dbCon.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.dbCon.close();
                } catch (Exception e) {
                    user.dbCon.close();
                    userid = "0L";
                }
            } else {
                response.sendRedirect("validatelogin.jsp");
            }
        } else {
            response.sendRedirect("validatelogin.jsp");
        }
    } catch (Exception e) {
        userid = "0L";
    }
%>

Your Employee ID is <%=authId%>
Your Login ID Was <%=userid%>

<%

    String end_time = ((String) request.getSession().getAttribute("end_time"));
    if (TimeUtil.getDateDiff(end_time, TimeUnit.SECONDS) > 0) {
        //RequestDispatcher rd = request.getRequestDispatcher("quizres.jsp");
        //rd.forward(request, response);
    }
%>

<%
    if (redirectURL != null) {
        //response.sendRedirect(redirectURL);
    }
%>
