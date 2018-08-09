package globals;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import globals.SendMail;

/**
 *
 * @author Adminsitrator
 */
public class User {
    // Login Details

    private String pfnumber;
    private String pwd;
    // User Details
    private String userid;
    private String designation;
    private String email;
    private String location;
    private String department;
    private String mail;
    private String return_no;
    private String name;
    private String maxmisyr;
    private String headerstatus;
    private String uploaded_file;
    private String property_year;
    private String current_yymm;
    private String msgerror = "";
    public static Date AppDate = null;
    private String fileview;
    globals.DBService lib = new globals.DBService();
    // Validation Details
    String sessionid;
    private boolean isloggedin;
    private boolean isvaliduser;
    private List authorisation;//--------contains access specifier and roles of the user may have multiple elements
    public Connection dbCon;
    public String sErrorMsg;
    private String[] sr_no = new String[24];

    public User() {
        isloggedin = false;
        isvaliduser = false;

    }

    public boolean initialiseUser() {
        String ssql;
        PreparedStatement ps;

        sErrorMsg = "";
        ResultSet rs = null;


        if (isloggedin) {
            // Fetch user details from DB

            ssql = "select emp_code as emp_code,emp_name as emp_name,EMP_DESIG "
                    + "from emp_master"
                    + " where "
                    + " lpad(emp_code,8,0)=lpad('" + getPfnumber() + "',8,0)";

            System.out.println("Initiliaze User::;" + ssql);

            try {
                ps = dbCon.prepareStatement(ssql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    instiantiateMe(rs);
                    rs.close();
                    this.isvaliduser = true;
                } else {
                    this.isvaliduser = false;
                    rs.close();
                }
                //this.isvaliduser = fetchUserAuthorisationProfile(this.userid);
            } catch (Exception e) {
                e.printStackTrace();
                sErrorMsg = e.getMessage();
            }
        }

        return isvaliduser;
    }

    public boolean fetchUserAuthorisationProfile(String id) {
        bnAuthorisation bnauth;
        authorisation = new ArrayList<bnAuthorisation>();
        String ssql;
        String hier = "", temp = "", loclist = "";
        ResultSet rs;
        PreparedStatement ps;
        this.isvaliduser = false;
        ssql = "select * from hindi_mst_user_roles where lpad(user_id,8,0)=lpad('" + getPfnumber() + "',8,0)";
        try {
            ps = dbCon.prepareStatement(ssql);

            rs = ps.executeQuery();
            while (rs.next()) {
                this.isvaliduser = true;
                bnauth = new bnAuthorisation();

                if (rs.getString("access_specifier") == null) {
                    bnauth.setAccess_specifier("");
                } else {
                    bnauth.setAccess_specifier(rs.getString("access_specifier"));
                }
                bnauth.setRole(rs.getString("role"));
                authorisation.add(bnauth);
            }
            ps.close();
            rs.close();
            //  Iterator<bnAuthorisation> i = authorisation.iterator();
            //  while (i.hasNext()) {
            //      bnauth = i.next();

        } catch (Exception e) {
            e.printStackTrace();
            sErrorMsg = e.getMessage();
            return this.isvaliduser;
        }
        return this.isvaliduser;
    }

    public boolean hasRole(String role) {
        //For Menu
        boolean res = false;
        List lath;
        bnAuthorisation bnauth;

        Iterator<bnAuthorisation> e = this.authorisation.iterator();
        if (role.length() > 0) {
            while (e.hasNext()) {
                bnauth = e.next();
                if (bnauth.getRole().equalsIgnoreCase(role)) {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }

    public boolean hasRole(String role, String access_specifier) {
        //Operator or L1 or L2
        boolean res = false;
        List lath;
        bnAuthorisation bnauth;

        Iterator<bnAuthorisation> e = this.authorisation.iterator();
        if (role.length() > 0) {
            while (e.hasNext()) {
                bnauth = e.next();
                if ((bnauth.getRole()).equalsIgnoreCase(role) && bnauth.getAccess_specifier().equalsIgnoreCase(access_specifier)) {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }

    public List isOPR() {
        //Operator
        String res = "CC";
        List lath=new ArrayList();
        bnAuthorisation bnauth;

        Iterator<bnAuthorisation> e = this.authorisation.iterator();

        while (e.hasNext()) {
            bnauth = e.next();
            if ((bnauth.getRole()).equalsIgnoreCase("OPR")) {
               
                lath.add(bnauth.getAccess_specifier());
                
            }
        }

        return lath;
    }

    public String getRoleList(String role) {
        //Operator
        String res = "CC";
        List lath;
        bnAuthorisation bnauth;

        Iterator<bnAuthorisation> e = this.authorisation.iterator();

        if (role.length() > 0) {
            while (e.hasNext()) {
                bnauth = e.next();
                if ((bnauth.getRole()).equalsIgnoreCase(role)) {
                    res = bnauth.getAccess_specifier();
                    break;
                }
            }
        }

        return res;
    }

    public static String today(int days) {
        // usage today();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        cal.add(Calendar.DATE, days);
        String today = dateFormat.format(cal.getTime());
        System.out.println("today" + today);
        return today;//+System.currentTimeMillis() ;
    }

    public static String currentyymm(int months) {
        // usage today();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");

        cal.add(Calendar.MONTH, -1);
        String today = dateFormat.format(cal.getTime());
        System.out.println("current yymm" + today);
        return today;//+System.currentTimeMillis() ;
    }

    public void sendEmail(int report_no, int status) {
        // usage today();
        String ssql = "";
        Statement stmt;
        sErrorMsg = "";
        ResultSet rs = null;
        int a = 0;

        Connection con = null;
        //String mailsubject = "Email from Hindi MIS Report Portal";
        String mailsubject = "हिन्दी एमआईएस रिपोर्ट पोर्टल से ईमेल | ";
        String mailtext = "";
        if (status == 0) {

            //mailtext = "Report submitted for your department has been rejectd. Please Login at Hindi MIS Portal link (Link @NRO Intranet Page)  and Resubmit the report";
            //mailtext = "आपके विभाग के लिए प्रस्तुत रिपोर्ट को खारिज कर दिया गया है | कृपया हिन्दी एमआईएस पोर्टल लिंक (Link @NRO Intranet Page) पर लॉगिन करें और रिपोर्ट पुनः सबमिट करें | ";

            ssql = "insert into maildb (report_id,mailrecipient,subject,mailtext,mailsender)"
                    + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role='OPR'"
                    + " and d.identifier='status00'";
            ssql = ssql + " union all ";

            //mailtext = "Report submitted for your department has been rejectd. This is for your information only";
            //mailtext = "आपके विभाग के लिए प्रस्तुत रिपोर्ट को खारिज कर दिया गया है | यह केवल आपकी जानकारी के लिए है | ";

            ssql = ssql + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role='L1'"
                    + " and d.identifier='status01'";


        }

        if (status == 1) {

            //mailtext = "Report submitted for your department and is pending for your approval . This is for your information only";
            //mailtext = "आपके विभाग के लिए रिपोर्ट प्रस्तुत की गई है और अनुमोदन के लिए लंबित है | यह केवल आपकी जानकारी के लिए है | ";

            ssql = "insert into maildb (report_id,mailrecipient,subject,mailtext,mailsender)"
                    + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role='OPR'"
                    + " and d.identifier='status10'";

            ssql = ssql + " union all ";

            //mailtext = "Report is pending for your approval .Please Login at Hindi MIS Portal link (Link @NRO Intranet Page) ";
            //mailtext = "रिपोर्ट अपने अनुमोदन के लिए लंबित है | कृपया हिन्दी एमआईएस पोर्टल लिंक पर लॉगिन करें (Link @NRO Intranet Page)| ";

            ssql = ssql + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role='L1'"
                    + " and d.identifier='status11'";
        }

        if (status == 2) {

            //mailtext = "Report has been Partially Approved.It is pending for final approval. This is for information only";
            //mailtext = "रिपोर्ट आंशिक रूप से स्वीकृत की गई है | यह अंतिम अनुमोदन के लिए लंबित है | यह केवल आपकी जानकारी के लिए है |";

            ssql = "insert into maildb (report_id,mailrecipient,subject,mailtext,mailsender)"
                    + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role in('OPR','L1')"
                    + " and d.identifier='status20'";

            ssql = ssql + " union all ";

            //mailtext = "Report has been Partially Approved.It is pending for your final approval. Please Login at Hindi MIS Portal link (Link @NRO Intranet Page) ";
            //mailtext = "रिपोर्ट आंशिक रूप से स्वीकृत की गई है | यह आपके अंतिम अनुमोदन के लिए लंबित है | कृपया हिन्दी एमआईएस पोर्टल लिंक पर लॉगिन करें (Link @NRO Intranet Page) |";

            ssql = ssql + " select '" + report_no + "',email_id,mailsubject,mailtext,'hindimailer@indianoil.in'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b, hindi_trn_report_header c, maildb_data d"
                    + " where c.report_no=" + report_no
                    + " and a.user_id=b.user_id"
                    + " and c.cost_Centre_code=b.access_specifier"
                    + " and b.role='L2'"
                    + " and d.identifier='status21'";
        }


        try {
            globals.DBService lib = new globals.DBService();
            con = lib.getConnection();
            stmt = dbCon.createStatement();
            System.out.println("ssql ? " + ssql);

            stmt.executeUpdate(ssql);
        } catch (Exception e) {
            e.printStackTrace();
            sErrorMsg = e.getMessage();
        }



    }

    public int sendreminderEmail(int report_yymm,User role) {
        // usage today();
        String ssql = "";
        Statement stmt;
        sErrorMsg = "";
        ResultSet rs = null;
        int a = 0;



        Connection con = null;
        try {

            con = lib.getConnection();
            
            String mailsubject = "REMINDER ::Email From Hindi MIS Portal";
            String mailtext = "";
            mailtext = "Report for your Department/Location is still pending completion . Please Login at Hindi MIS Portal link (Link @NRO Intranet Page) ";
             

            /*
            String mailsubject = "अनुस्मारक ::हिन्दी एमआईएस पोर्टल से ईमेल |";
            String mailtext = "";
            mailtext = "आपके विभाग / स्थान के लिए रिपोर्ट अभी भी पूरा होने के लिए लंबित है | हिन्दी एमआईएस पोर्टल लिंक (Link @NRO Intranet Page) पर लॉगिन करें | ";
             */

           ssql = "insert into maildb (report_id,mailrecipient,subject,mailtext,mailsender,created_by)"
                    + "( select '00',email_id,mailsubject,mailtext,'hindimailer@indianoil.in','"+role.getUserid()+"'"
                    + " from hindi_mst_user a, hindi_mst_user_roles b,maildb_data c"
                    + " where a.user_id=b.user_id"
                    + " and b.role in('OPR','L1') and b.access_specifier not in"
                    + "( select cost_centre_code from hindi_trn_report_header where report_status<>0 and report_yyyymm=" + report_yymm + ")"
                    + " and c.identifier='reminder'";
             if(role.hasRole("SO"))
                ssql =ssql  + " and b.access_specifier in (select Cost_Centre_code from hindi_mst_Cost_centre where state_cord='"+role.getRoleList("SO")+"')";
                ssql=ssql+")";
          

   /*    //  ssql =ssql+" union all ";
            ssql =ssql+" select '00',email_id,'"+mailsubject+"','"+mailtext+"','hindimailer@indianoil.in'"
            +" from hindi_mst_user a, hindi_mst_user_roles b"
            +" where a.user_id=b.user_id"
            +" and b.role in('OPR','L1') and b.access_specifier not in" +
            "( select cost_centre_code from hindi_trn_report_header where report_yyyymm="+report_yymm+"))";
*/
            System.out.println("ssql ? " + ssql);

            stmt = con.createStatement();
            a = stmt.executeUpdate(ssql);
        } catch (Exception e) {
            e.printStackTrace();
            sErrorMsg = e.getMessage();
        }

        return a;

    }

    public int generateReport(int report_yymm, String meetingdate, String meetingdesc) {
        // usage today();
        globals.DBService lib = new globals.DBService();
        Connection con = lib.getConnection();
        CallableStatement st;

        String ssql = "";
        // call SPR_GENERATE_REGION_REPORT (?,'NR',sysdate,'THIS IS MY DESC','00082551,OUT_index,out_ERR);--

        ssql = "{call SPR_GENERATE_REGION_REPORT(?,?,?,?,?,?,?)}";

        Statement stmt;
        sErrorMsg = "";
        ResultSet rs = null;
        int ret = 0;

        try {
            st = con.prepareCall(ssql);
            st.setLong(1, report_yymm);
            st.setString(2, getRoleList("REG"));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date d = df.parse(meetingdate);
            java.sql.Date ds = new java.sql.Date(d.getTime());
            st.setDate(3, ds);
            st.setString(4, meetingdesc);
            st.setString(5, getUserid());
             st.registerOutParameter(6, Types.INTEGER);
            st.registerOutParameter(7, Types.VARCHAR);

            st.executeQuery();
            ret = st.getInt(6); // Error Code 0-Failure, positive - means InspectionID
            this.msgerror = st.getString(7); //Error Message String
            if (ret > 0) {
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.msgerror = e.getMessage();

        }

        return ret;

    }

    public int generateReportNew(int report_yymm, String meetingdate, String meetingdesc,String cc) {
        // usage today();
        globals.DBService lib = new globals.DBService();
        Connection con = lib.getConnection();
        CallableStatement st;

        String ssql = "";
        // call SPR_GENERATE_REGION_REPORT (?,'NR',sysdate,'THIS IS MY DESC','00082551,OUT_index,out_ERR);--

        ssql = "{call SPR_GENERATE_REGION_REPORT_NEW(?,?,?,?,?,?,?,?)}";

        Statement stmt;
        sErrorMsg = "";
        ResultSet rs = null;
        int ret = 0;

        try {
            st = con.prepareCall(ssql);
            st.setLong(1, report_yymm);
            st.setString(2, getRoleList("REG"));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date d = df.parse(meetingdate);
            java.sql.Date ds = new java.sql.Date(d.getTime());
            st.setDate(3, ds);
            st.setString(4, meetingdesc);
            st.setString(5, getUserid());
            st.setString(6, cc);
            st.registerOutParameter(7, Types.INTEGER);
            st.registerOutParameter(8, Types.VARCHAR);

            st.executeQuery();
            String checkquery ="{call SPR_GENERATE_REGION_REPORT_NEW(" + report_yymm + ","+getRoleList("REG")
                    +","+ds+","+meetingdesc+","+getUserid()+","+cc+")";
             System.out.println(checkquery);
            ret = st.getInt(7); // Error Code 0-Failure, positive - means InspectionID
            this.msgerror = st.getString(8); //Error Message String
            if (ret > 0) {
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.msgerror = e.getMessage();

        }

        return ret;

    }

    public void instiantiateMe(ResultSet rs) {
        try {
            if (rs.getString("emp_code") != null) {
                this.userid = rs.getString(1);
            } else {
                this.userid = "";
            }
            if (rs.getString("emp_name") != null) {
                this.name = new String(rs.getString(2).getBytes("ISO8859-1"), "UTF-8");
            } else {
                this.name = "Name";
            }
            if (rs.getString("designation") != null) {
                this.designation = rs.getString("designation");
            } else {
                this.designation = "Desig";
            }
            if (rs.getString("email") != null) {
                this.location = rs.getString("email");
            } else {
                this.location = "x@y.com";
            }

            System.out.println("instiantiated User::;" + this.userid);
        } catch (Exception e) {

            sErrorMsg = e.getMessage();
        }
    }

    public void MaxMISYear() {
        int i = 0;
        String ssql2;
        String multiple="(";
        PreparedStatement ps2;
        ResultSet rs = null;
        List cc=isOPR();
        for(Iterator e = cc.iterator(); e.hasNext();)
        {
           

            multiple=multiple+"'"+(String)e.next()+"',";
            System.out.println("mut"+multiple);
        }

        multiple=multiple+"'0')";
        ssql2 = "select max(report_yyyymm) from hindi_trn_report_header where "
                + "cost_centre_code in "+multiple;
        System.out.println("MAX MIS " + ssql2);


        try {
            ps2 = dbCon.prepareStatement(ssql2);
            rs = ps2.executeQuery();


            if (rs.next()) {

                if (rs.getString(1) != null) {
                    this.maxmisyr = rs.getString(1);
                } else {
                    this.maxmisyr = lib.startmonth;

                }
            }

            rs.close();
        } catch (Exception e) {

            sErrorMsg = e.getMessage();
            System.out.println("Error::" + sErrorMsg);
        }

    }

    //Initialise Getter/Setter methods for Properties
    public void setPfnumber(String pf) {
        this.pfnumber = pf;
    }

    public String getPfnumber() {
        return pfnumber;
    }

    public void setUploaded_file(String s) {
        this.uploaded_file = s;
    }

    public String getUploaded_file() {
        return uploaded_file;
    }

    public void setFileview(String s) {
        this.fileview = s;
    }

    public String getFileview() {
        return fileview;
    }

    public void setMaxmisyr(String s) {
        this.maxmisyr = s;
    }

    public String getMaxmisyr() {
        return maxmisyr;
    }

    public void setCurrent_yymm(String s) {
        this.current_yymm = s;
    }

    public String getCurrent_yymm() {
        return current_yymm;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public String getHeaderstatus() {
        return headerstatus;
    }

    public void setHeaderstatus(String s) {
        this.headerstatus = s;
    }

    public String getProperty_year() {
        return property_year;
    }

    public void setProperty_year(String s) {
        this.property_year = s;
    }

    public boolean getIsloggedin() {
        return isloggedin;
    }

    public void setIsloggedin(boolean state) {
        this.isloggedin = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String s) {
        this.mail = s;
    }
    //Getter Properties ONLY

    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String s) {
        this.location = s;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String s) {
        this.department = s;
    }

    public String getReturn_no() {
        return return_no;
    }

    public void setReturn_no(String s) {
        this.return_no = s;
    }

    public String getMsgerror() {
        return msgerror;
    }

    public void setMsgerror(String s) {
        this.msgerror = s;
    }

    public boolean getIsvaliduser() {
        return isvaliduser;
    }

    public void setUserid(String l) {
        this.userid = l;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setSr_no(String[] c) {
        this.sr_no = c;
    }

    public String[] getSr_no() {
        return sr_no;
    }

    public List getAuthorisation() {
        return this.authorisation;
    }
}
