<%-- 
    Document   : resultsList
    Created on : 1 Aug, 2018, 2:56:23 PM
    Author     : wrtrg2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../UItemplate/header2.jsp" %>
    <style>
        
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
    .hide{
        display: none;
    }
    .loaderImg{
        z-index: 12;
        position: absolute;
        left: 50%;
        top: 50%;
    }
    </style>
    <body>
<!--        <div id="loader" class="lds-css ng-scope" style="position: absolute;left: 40%;top: 40%;">
            <div style="width: 100%; height: 100%; display: none;" class="lds-pacman" id="divv"></div>
        </div>-->
        <div id="loader">
            <img class="loaderImg" src="resources/images/loader.gif">
        </div>
        <div class="empTable row">
            <div class="col-lg-offset-1 col-lg-10" style="margin: 80px;margin-left: 150px;">
                <table id="EmpListDatable" class="table table-responsive table-bordered">
                    <thead>
                        <tr>
                            <th>Sr.No</th>
                            <th>Code</th>
                            <th>Name</th>
                            <th>Location Code</th>
                            <th>Designation</th>
                            <th>Company Code</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <script src="resources/js/empListJS.js" type="text/javascript"></script>
    </body>
</html>
