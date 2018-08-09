/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iocl.controllers;

import com.google.gson.Gson;
import com.iocl.quiz.DatabaseConnectionFactory;
import globals.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wrtrg2
 */
public class ResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url");
        if(url.equalsIgnoreCase("upload")){
            request.getRequestDispatcher("jsps/UploadExcel.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("result")){
            request.getRequestDispatcher("jsps/resultsList.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("Finish")){
            request.getRequestDispatcher("jsps/result.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("Masters")){
            request.getRequestDispatcher("jsps/masters.jsp").forward(request, response);
        }else if(url.equalsIgnoreCase("empList")){
            request.getRequestDispatcher("jsps/empList.jsp").forward(request, response);
        }
    }
}
