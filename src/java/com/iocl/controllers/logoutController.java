/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iocl.controllers;

import config.snConfigVars;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sessions.snAuthentications;

public class logoutController extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        session.invalidate();
        (new snAuthentications()).sessionCASInvalidate(request, response, getServletContext().getInitParameter("server-IP"), getServletContext().getInitParameter("ProjectName"));
        snConfigVars V = new snConfigVars();
        if (V.isCASenabled()) {
            if (true) {
                return;
            }
        }
    }
}
