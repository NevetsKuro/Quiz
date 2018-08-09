/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iocl.quiz;



import java.sql.Connection;
import config.snConfigVars; 



public class DatabaseConnectionFactory {	
    public static Connection createConnection()
    {
        snConfigVars V = new snConfigVars();
        globals.User user = new globals.User();
        globals.DBService dbservice = new globals.DBService();
        user.setIsloggedin(true);
        user.dbCon = dbservice.getConnection();
        return user.dbCon;
    }
}

