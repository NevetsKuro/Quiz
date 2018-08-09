package globals;

import com.iocl.quiz.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class initializeInstruction {

    public static ArrayList getQuizInstructions() {
        Connection con = null;
        ResultSet res = null;
        ArrayList liInstructions = new ArrayList();
        modelInstruction eachObj = null;
        Statement st = null;
        try {
            con = DatabaseConnectionFactory.createConnection();
            st = con.createStatement();
            String query = " SELECT INS_ID,INS_DESC FROM MST_INSTRUCTIONU";

            res = st.executeQuery(query);
            while (res.next()) {
                eachObj = new modelInstruction();
                eachObj.setInsID(res.getString(1));
                eachObj.setInsDesc(res.getString(2));
                liInstructions.add(eachObj);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(LabOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error : While closing resources in getIndustryWiseLABSummary" + e.getMessage());
            }
        }

        return liInstructions;
    }
}