<%-- 
    Document   : compatibleIssueIE
    Created on : 4 Sep, 2017, 2:18:12 PM
    Author     : 00507469
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String userAgent = request.getHeader("user-agent");
    if (userAgent.indexOf("MSIE") <= -1) 
    {
        RequestDispatcher rd = request.getRequestDispatcher("validatelogin.jsp");
        rd.forward(request, response);
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>compatible Issue</title>
    </head>
    <body>
        <h1 style="color:red;">This is not compatible with Internet Explorer.<br/> Kindly copy the link and open in Google Chrome or Mozilla!</h1>
    </body>
</html>
