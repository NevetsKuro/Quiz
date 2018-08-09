<%-- 
   Document   : header
   Created on : 17 Dec, 2016, 11:03:57 AM
   Author     : Manish Jangir
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="headTag.jsp" %>
<header>
    <div class="row">
        <img class="img-responsive center-block" src="resources/images/portal_hdr_main.bmp" alt="QUIZ"/>
    </div>
    <div class="row col-md-12 text-center">
        <h3 style="color: #4F8A10"> ${quiz_name}</h3>
    </div>
    <div class="row text-center">
        <div class="col-sm-4">
            <label class="form-group">Name: </label> ${emp.name}
        </div>
        <div class="col-sm-4">
            <label class="form-group">Employee ID: </label> ${emp.userid}
        </div>
        <div class="col-sm-4">
            <label class="form-group">Designation: </label> ${emp.designation}
        </div>
    </div>
</header>