<%@ page language="java" import="com.iocl.quiz.*, java.util.ArrayList" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <%@include file="../UItemplate/header2.jsp" %>
    <body>
        <div style="position:absolute;left:500px;top:70px">
            <h3 align="center">Quiz Result</h3>
            <table border=1>
                <tr>
                    <td class="head">
                        You have successfully saved your answers and logged out!!
                    </td>
                </tr>
            </table>         
            <%session.invalidate();%>
        </div>
        <%--<jsp:include page="../UItemplate/footer.jsp"/>--%>
    </body>
</html>