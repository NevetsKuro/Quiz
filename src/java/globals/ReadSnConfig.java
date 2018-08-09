package globals;





import config.*;
import java.io.*;

/**
 *
 * @author Administrator
 */
public class ReadSnConfig extends snConfigFile
{
    String dbpath = "";
    String dbdriver = "";
    String loginuser = "";
    String loginpassword = "";
    String configfilename = "";
    String uploadfilepath="";
    String startmonth="";
    public void readParameters() {
        configfilename = this.getConfigFileName();
        try {
            FileInputStream fstream = new FileInputStream(configfilename);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {            
                if (strLine.startsWith("JDBCurl"))
                {
                    dbpath=strLine.replaceFirst("JDBCurl=", "").trim();
                }
                if (strLine.startsWith("Login"))
                {
                    loginuser=strLine.replaceFirst("Login=", "").trim();
                }
                if (strLine.startsWith("Pass"))
                {
                    loginpassword=strLine.replaceFirst("Pass=", "").trim();
                }
                if (strLine.startsWith("driver"))
                {
                    dbdriver=strLine.replaceFirst("driver=", "").trim();
                }
                if (strLine.startsWith("Fileuploadpath"))
                {
                    uploadfilepath=strLine.replaceFirst("Fileuploadpath=", "").trim();
                }
                 if (strLine.startsWith("Startmonth"))
                {
                    startmonth=strLine.replaceFirst("Startmonth=", "").trim();
                }

            }
            //Close the input stream
            in.close();
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

