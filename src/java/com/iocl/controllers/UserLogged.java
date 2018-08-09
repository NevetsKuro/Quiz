/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import com.google.gson.Gson;
import globals.User;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogged extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if(action == "getDetails"){
            User user = (User)request.getSession().getAttribute("emp");
            if(user.getIsloggedin()){
                HashMap<String, String> userDetails = new HashMap<String,String>();
                userDetails.put("empid", user.getUserid());
                userDetails.put("empName", user.getName());
                userDetails.put("quizTime", (String)request.getSession().getAttribute("quiz_time"));
                String json = new Gson().toJson(userDetails);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
        }else if(action == "getAuth"){
            User user = (User)request.getSession().getAttribute("emp");
            if(user.getIsloggedin()){
                request.setAttribute("Valid",true);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
