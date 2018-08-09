<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page language="java" import="com.iocl.quiz.*,java.util.ArrayList" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="globals.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <%@include file="../UItemplate/header2.jsp" %>
    <%
        Long quiz_time = ((Long) request.getSession().getAttribute("quiz_time"));//in seconds
        String end_time = ((String) request.getSession().getAttribute("end_time"));
        String login_timestamp = TimeUtil.convertToJSDate(((String) request.getSession().getAttribute("login_timestamp")));
        Long timeDuration = ((Long) request.getSession().getAttribute("timeDuration"));
        Long time=1245552l; 
            time = (Long)quiz_time;
    %>
    <style type="text/css">
        .hide{
            display: none;
        }
    @keyframes lds-pacman-1 {
      0% {
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
      }
      50% {
        -webkit-transform: rotate(-45deg);
        transform: rotate(-45deg);
      }
      100% {
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
      }
    }
    @-webkit-keyframes lds-pacman-1 {
      0% {
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
      }
      50% {
        -webkit-transform: rotate(-45deg);
        transform: rotate(-45deg);
      }
      100% {
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
      }
    }
    @keyframes lds-pacman-2 {
      0% {
        -webkit-transform: rotate(180deg);
        transform: rotate(180deg);
      }
      50% {
        -webkit-transform: rotate(225deg);
        transform: rotate(225deg);
      }
      100% {
        -webkit-transform: rotate(180deg);
        transform: rotate(180deg);
      }
    }
    @-webkit-keyframes lds-pacman-2 {
      0% {
        -webkit-transform: rotate(180deg);
        transform: rotate(180deg);
      }
      50% {
        -webkit-transform: rotate(225deg);
        transform: rotate(225deg);
      }
      100% {
        -webkit-transform: rotate(180deg);
        transform: rotate(180deg);
      }
    }
    @keyframes lds-pacman-3 {
      0% {
        -webkit-transform: translate(190px, 0);
        transform: translate(190px, 0);
        opacity: 0;
      }
      20% {
        opacity: 1;
      }
      100% {
        -webkit-transform: translate(70px, 0);
        transform: translate(70px, 0);
        opacity: 1;
      }
    }
    @-webkit-keyframes lds-pacman-3 {
      0% {
        -webkit-transform: translate(190px, 0);
        transform: translate(190px, 0);
        opacity: 0;
      }
      20% {
        opacity: 1;
      }
      100% {
        -webkit-transform: translate(70px, 0);
        transform: translate(70px, 0);
        opacity: 1;
      }
    }
    .lds-pacman {
      position: relative;
    }
    .lds-pacman > div:nth-child(2) div {
      position: absolute;
      top: 40px;
      left: 40px;
      width: 120px;
      height: 60px;
      border-radius: 120px 120px 0 0;
      background: #337ab7;
      -webkit-animation: lds-pacman-1 1s linear infinite;
      animation: lds-pacman-1 1s linear infinite;
      -webkit-transform-origin: 60px 60px;
      transform-origin: 60px 60px;
    }
    .lds-pacman > div:nth-child(2) div:nth-child(2) {
      -webkit-animation: lds-pacman-2 1s linear infinite;
      animation: lds-pacman-2 1s linear infinite;
    }
    .lds-pacman > div:nth-child(1) div {
      position: absolute;
      top: 92px;
      left: -8px;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      background: #5bc0de;
      -webkit-animation: lds-pacman-3 1s linear infinite;
      animation: lds-pacman-3 1s linear infinite;
    }
    .lds-pacman > div:nth-child(1) div:nth-child(1) {
      -webkit-animation-delay: -0.67s;
      animation-delay: -0.67s;
    }
    .lds-pacman > div:nth-child(1) div:nth-child(2) {
      -webkit-animation-delay: -0.33s;
      animation-delay: -0.33s;
    }
    .lds-pacman > div:nth-child(1) div:nth-child(3) {
      -webkit-animation-delay: 0s;
      animation-delay: 0s;
    }
    .lds-pacman {
      width: 200px !important;
      height: 200px !important;
      -webkit-transform: translate(-100px, -100px) scale(1) translate(100px, 100px);
      transform: translate(-100px, -100px) scale(1) translate(100px, 100px);
    }
    </style>
    <body id="box" oncontextmenu="return false" style="width:99%">
        <div id="loader" class="lds-css ng-scope" style="position: absolute;left: 40%;top: 40%;">
            <div style="width: 100%; height: 100%;" class="lds-pacman" id="divv">
                <div><div></div><div></div><div></div></div><div><div></div><div></div></div>
            </div>
        </div>
        <div id="tqt" data-time="${quiz_time}"></div>
        <div id="mainBody" class="container hide" style="margin-top:40px">
            <div class="col-lg-8">
                    <div class="row">
                        <div class="col-lg-12">
                            <div id="clockIt" class="text-center" style="font-size:40px">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <table id="QuesTable" class="table table-striped table-bordered" style="width:100%;font-weight:bold;font-family: sans-serif;">
                                <thead>
                                    <tr>
                                        <td class="text-center">Questions</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ques_list_details}" var="quest" varStatus="outerloop">
                                        <tr>
                                            <td>
                                                <span data-name="${quest.questionNumber}" class="row">Q${outerloop.count}. ${quest.question} </span>
                                                <br/>
                                                <br/>
                                                <form style="margin-left: 20px">
                                                <c:forEach items="${quest.questionOptions}" var="questopts" varStatus="innerloop">
                                                    <div class="row">
                                                        <label>
                                                            <input type="radio" name="${quest.questionNumber}"  value="${innerloop.count}" >  ${questopts}
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="button" class="btn btn-primary" id="clearRadio" value="Clear"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" class="btn btn-primary" id="saveIt" name="action" value="Save"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" id="submitIt" class="btn btn-primary" name="action" value="Submit"/>
                        </div>
                    </div>
                </div>
                <div id="summary" class="col-lg-4" style="margin-top: 64px;border: 1px dotted;height: 390px;scroll-behavior: smooth;background-color: #f9f9f9"><!-- summary -->

                </div>
            </div>
        <script src="resources/js/examJS.js" type="text/javascript"></script>
    </body>
</html>