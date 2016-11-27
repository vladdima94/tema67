/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import Beans.StudentBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class ResultsDAOJDBC implements ResultsDAO{

    @Override
    public void insertResultsInDB(List<StudentBean> results) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            String getSchoolsStmt = "INSERT INTO RESULTSTBL (STUDENTNAME, SCHOOLNAME) VALUES (?, ?)";
            PreparedStatement stmt;
            for(StudentBean stud : results)
            {
                stmt = conn.prepareStatement(getSchoolsStmt);
                stmt.setString(1, stud.getName());
                stmt.setString(2, stud.getAsignedTo().getName());
                stmt.execute();
                stmt.close();
            }
            conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            if(conn != null) try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
