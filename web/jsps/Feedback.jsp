<%-- 
    Document   : showExcel
    Created on : 10 Sep, 2018, 12:51:57 PM
    Author     : wrtrg2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../UItemplate/header2.jsp" %>
<style>
    .inputS {
        border: 0;
        width: 1px;
        height: 1px;
        overflow: hidden;
        position: absolute !important;
        clip: rect(1px 1px 1px 1px);
        clip: rect(1px, 1px, 1px, 1px);
        opacity: 0;
    }

    .labelS {
        position: relative;
        float: right;
        color: #C8C8C8;
    }

    .labelS:before {
        margin: 5px;
        content: "\f005";
        font-family: FontAwesome;
        display: inline-block;
        font-size: 1.5em;
        color: #ccc;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
    }

    input:checked ~ .labelS:before {
        color: #FFC107;
    }

    .labelS:hover ~ .labelS:before {
        color: #ffdb70;
    }

    .labelS:hover:before {
        color: #FFC107;
    }
    section{
        margin: 20px 200px;
        border: 3px double grey;
        padding: 40px;
        border-radius: 10px;
        background: white;
    }
    .labelS1{
        margin-right: 60%!important;
    }
    .h2Class{
        text-align: center;
        margin: 25px;
        font-family: cursive;
        font-size: 39px;
    }
</style>
    <body style="background: snow;">

        <div class="container">
            <h2 class="h2Class">Feedback Form</h2>
            <section>
                <form id="feedbackForm" class="pure-form">
                    <div class="col-12">
                        <input id="fname" name="fname" class="form-control pure-input-1-2" type="text" placeholder="Enter a name.." maxlength="20" autofocus>
                    </div>
                    <div class="col-12">
                        <label>Rating:</label>
                        <input id="frating" name="frating" class="form-control inputS" type="text">
                            <input type="checkbox" id="st1" class="inputS" value="1"/>
                            <label for="st1" class="labelS labelS1"></label>
                            <input type="checkbox" id="st2" class="inputS" value="2"/>
                            <label for="st2" class="labelS"></label>
                            <input type="checkbox" id="st3" class="inputS" value="3"/>
                            <label for="st3" class="labelS"></label>
                            <input type="checkbox" id="st4" class="inputS" value="4"/>
                            <label for="st4" class="labelS"></label>
                            <input type="checkbox" id="st5" class="inputS" value="5"/>
                            <label for="st5" class="labelS"></label>
                    </div>
                    <div class="col-12">
                        <textarea id="fcomment" name="fcomment" class="pure-input-1" placeholder="Enter Comment.." maxlength="255"></textarea>
                    </div>
                    <br>
                    <div class="col-12">
                        <button id="subFeedback" class="button button-raised button-pill button-primary">Submit</button>
                    </div>
                </form>
            </section>
        </div>
        <script src="resources/js/feedback.js"></script>
    </body>
</html>
