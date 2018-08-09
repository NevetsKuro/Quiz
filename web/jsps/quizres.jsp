
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="com.iocl.quiz.*, java.util.ArrayList" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
  <%@page import="globals.User" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Quiz Result</title>

<link href="css/demo.css" rel="stylesheet" type="text/css" />
<!--[if lt IE 9]>
<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- required for this demo -->
<link rel="stylesheet" href="css/sticky-navigation.css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<style type="text/css">
.clockStyle {
      background-color:#000;
      border:#999 2px inset;
      padding:6px;
      color:#0FF;
      font-family:"Arial Black", Gadget, sans-serif;
      font-size:16px;
      font-weight:bold;
      letter-spacing: 2px;
      text-decoration-color: red;
      display:inline;
    }
</style>
<script type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
</script>
</head>
<body onload="noBack();"
    onpageshow="if (event.persisted) noBack();" onunload="" bgcolor="lightblue">
 <%
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Expires","0");
    response.setDateHeader("Expires",-1);
 
    String flag_res = null;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    User user = ((User)request.getSession().getAttribute("emp"));
    String strQuizName=(String)request.getSession().getAttribute("quiz_name");
    
    try{
        if(user == null){
            con=DatabaseConnectionFactory.createConnection();
            st = con.createStatement();
            String sql = "select P_VALUE "
                + "from CONTROL_TABLE"
                + " where "
                + " PARAM = 'result_flag'";

            rs = st.executeQuery(sql);
            if(rs.next()){
                flag_res = rs.getString("P_VALUE");
            }
            rs.close();
            con.close();
        RequestDispatcher rd = request.getRequestDispatcher("validatelogin.jsp");
        request.setAttribute("flag_res", flag_res);
        rd.forward(request, response);
        }
        
    }catch(SQLException e){
        con.close();;
        rs.close();
        e.printStackTrace();
    }
    
    String empid = null, empName = null, desig = null;
    if(user != null){
        empid = user.getUserid();
        empName = user.getName();
        desig = user.getDesignation();
    }
    
    
    Integer no_questions = 0;
    Integer marks = 0;
    Integer top_marks = 0;
    String strTimeDuration="";
    int timeSeconds=0;
    int iSecs=0,iMins=0;
    try{
        con=DatabaseConnectionFactory.createConnection();
        st = con.createStatement();
        String sql1 = "select max(marks) "
            + "from MST_RESULT";

        String sql2 = "select marks,TIME_DURATION "
            + "from MST_RESULT"
            + " where emp_code = '"+empid+"'";

        String sql3 = "select count(*) "
            + "from QUESTION_MASTERU"
            + " where update_flag='A'";
        
        rs = st.executeQuery(sql1);
        if(rs.next()){
            top_marks = rs.getInt(1);
        }
        
        rs = st.executeQuery(sql2);
        if(rs.next()){
            marks = rs.getInt(1);
            timeSeconds=rs.getInt(2);
        }
        
        iMins=(timeSeconds/60);
        iSecs=(timeSeconds%60);
        if(iMins>0)
        {
            if(iMins>1)
                strTimeDuration=iMins+ " Minutes ";
            else
                strTimeDuration=" 1 Minute ";
        }
        if(iSecs>0)
        {
            if(iSecs>1)
                strTimeDuration += iSecs + " Seconds";
            else
                strTimeDuration += "1 Second";
        }
        
        rs = st.executeQuery(sql3);
        if(rs.next()){
            no_questions = rs.getInt(1);
        }
        
        con.close();;
        rs.close();

    }catch(SQLException e){
        con.close();;
        rs.close();
        e.printStackTrace();
    }
    
%>

    <table width="80%" align="center">
        <tr align="center">
        <td colspan="3">
      <img align="middle" src="css/images/portal_hdr_main.bmp" alt="QUIZ"/> </td></tr>
	  
	  

    <tr align="center"><td colspan="3">
<h3 style="color: #4F8A10"> <%=strQuizName%></h3></td>
 
         <td colspan="2">
          
       
        </td> 
    </tr>
	
    <tr align="center">
	<td width="34%">
NAME: <b><%=empName%></b>
  </td>
<td width="33%"> 
EMP_ID: <b><%=empid%>   
</td>
<td width="33%"> 
Designation: <b><%=desig%></b>
</td>

</table>
<br>
<h3 align="center">Quiz Result</h3>
<table border="2" align="center">
        
              
                <tr>
            <td class="head">
               Total No. of Questions :
            </td>
            <td>
                <span id="lblNquestions"><%=no_questions %></span></td>
        </tr>
        
        <tr>
            <td class="head">
                Your Marks :
            </td>
            <td>
                <span id="lblNcans"><%=marks%></span></td>
        </tr>
        <tr>
            <td class="head">
                Your Time Taken :
            </td>
            <td>
                <span id="lblNcans"><%=strTimeDuration%></span></td>
        </tr>
        <tr>
        	<td class="head">
        		Highest Marks(Till Now) :
        	</td>
        	<td>
                    <span id="lblNcans"><%=top_marks%></span>
            </td>	
        </tr>
    </table>
<%session.invalidate(); %>
<%--<jsp:include page="../UItemplate/footer.jsp"/>--%>

</body>
</html>