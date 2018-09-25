<%-- 
    Document   : Header2
    Created on : 30 Jul, 2018, 1:04:41 PM
    Author     : wrtrg2
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../UItemplate/footer.jsp" %>
<!DOCTYPE html>
<header class="animated fadeInDown">
    <div class="row" style="background-color: #014B8C;padding: 10px;box-shadow: 0px 2px 15px 0px black;color: #dddddd!important">
<!--        <div class="col-lg-2" style="background: aliceblue;border-radius: 29px;width: 19%;margin: 13px;text-align: center;font-style: italic;color: #014b8c">
            <h1><strong>Quiz</strong></h1>
        </div>-->
        <div class="col-lg-2" style="background: aliceblue;border-radius: 71px;width: 19%;margin: 13px;">
            <img src="resources/images/iocl.gif" alt="Indian Oil"/>
        </div>

        <div class="col-sm-7 col-xs-4 text-center">
            <h3 style="padding-top: 12px;font-size: 48px;font-family: cursive;"> ${quiz_name} </h3>
        </div>

        <div class="col-lg-2" style="margin-top: 10px;">
            <div class="col-sm-10">
                <label class="form-group">Name: </label> ${emp.name}
            </div>
            <div class="col-sm-2" style="font-size: 30px;" title="logout">
                <span id="logout" style="cursor: pointer"><i class="fa fa-sign-out"></i></span>
            </div>
            <div class="col-sm-12">
                <label class="form-group">Employee ID: </label> ${emp.userid}
            </div>
            <div class="col-sm-12">
                <label class="form-group">Designation: </label> ${emp.designation}
            </div>
        </div>
    </div>
            <script type="text/javascript">
                $(document).on('click','#logout',function() {
                    if($('#saveIt').length){
                        $('#saveIt').trigger('click');
                        console.log("trigger running");
                    }
                    setTimeout(window.open('logoutController','_self'),2000);
                    console.log("trigger runned");
                });
            </script>
</header>
