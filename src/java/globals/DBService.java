package globals;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.sql.*;
import java.util.*;

/**
 * @author Administrator
 */
public class DBService {

    private Connection con;
    public boolean isconnected;
    public boolean isconnectionvalid;    
    private String DBurl;
    private String DBuser;
    private String DBpass;
    private ReadSnConfig snconf;

   
    
    public String sErrorMsg;
    public String uploadfilepath="";
    public String uploadinspectionpath="";
    public String startmonth="";

    
    public DBService() {
        isconnected = false;
        isconnectionvalid = false;
        con = null;
        snconf=new ReadSnConfig();
        snconf.readParameters();
        uploadfilepath=snconf.uploadfilepath;
        startmonth=snconf.startmonth;
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(snconf.dbdriver);
            isconnectionvalid = true;
        } catch (Exception e) {
            e.printStackTrace();
            sErrorMsg = e.getMessage();
        }
    }

    private boolean openConnection() {
        if (isconnectionvalid == true && isconnected == false) {
            try {
                
               // con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.2:1521:xe", "prop", "prop");
                            Class.forName(snconf.dbdriver).newInstance();
                            Properties props = new Properties();
                            props.setProperty("user", snconf.loginuser);
                            props.setProperty("password", snconf.loginpassword);
                            props.setProperty("oracle.jdbc.convertNcharLiterals", "true");
                            props.setProperty("oracle.jdbc.defaultNChar", "true");
                            con = DriverManager.getConnection(snconf.dbpath, props);


                            



               // con = DriverManager.getConnection(snconf.dbpath, snconf.loginuser, snconf.loginpassword);
                isconnected = true;
            } catch (Exception e) {
                sErrorMsg = e.getMessage();
                isconnected = false;
            }
        }
        return isconnected;
    }

    public void closeConnection() {
        try {
            con.close();
            isconnected = false;
        } catch (Exception e) {
            isconnected = false;
            sErrorMsg = e.getMessage();
        }
    }

    ////////////////////////* Setter/Getter Methods*////////////////////////////
    public Connection getConnection() {
        if (isconnected) {
            return con;
        } else {
            boolean flag = openConnection();
            if (flag == true) {
                return con;
            } else {
                return null;
            }
        }
    }
    //Date Setter
   
}
