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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class SchoolDAOJDBC implements SchoolDAO{

    @Override
    public List<SchoolBean> getSchools() {
        Connection conn = null;
        List<SchoolBean> output = new ArrayList();
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            String stmtSQL = "SELECT * FROM SCHOOLSTBL ORDER BY SCHOOLNAME";
            PreparedStatement stmt =conn.prepareStatement(stmtSQL);
            stmt.execute(stmtSQL);
            ResultSet rows = stmt.getResultSet();
            while(rows.next())
            {
                String schoolName = rows.getString("SCHOOLNAME");
                int schoolID = rows.getInt("SCHOOLID");
                int maxapply = rows.getInt("MAXAPPLY");
                String schoolPref = rows.getString("PREFERENCES");
                SchoolBean temp = new SchoolBean();
                temp.setSchoolName(schoolName);
                temp.setSchoolID(schoolID);
                temp.setMaxApply(maxapply);
                temp.setPreferencesAsString(schoolPref);
                output.add(temp);
            }
            rows.close();
            stmt.close();
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
        return output;
    }

    @Override
    public void addSchoolToDB(SchoolBean school) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            school.prepareString();
            
            String insertSchoolStmt = "INSERT INTO SCHOOLSTBL(schoolID, schoolName, maxApply, preferences) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSchoolStmt);
            stmt.setInt(1, school.getSchoolID());
            stmt.setString(2, school.getSchoolName());
            stmt.setInt(3, school.getMaxApply());
            stmt.setString(4, school.getPreferencesAsString());
            stmt.executeQuery();
            stmt.close();
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

    @Override
    public void addSchoolsToDB(List<SchoolBean> schools) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            String insertSchoolStmt = "INSERT INTO schoolsTBL(schoolID, schoolName, maxApply, preferences) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt;
            for(SchoolBean school : schools){
                school.prepareString();
                stmt = conn.prepareStatement(insertSchoolStmt);
                stmt.setInt(1, school.getSchoolID());
                stmt.setString(2, school.getSchoolName());
                stmt.setInt(3, school.getMaxApply());
                stmt.setString(4, school.getPreferencesAsString());
                stmt.executeQuery();
                stmt.close();
            }
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

    @Override
    public void deleteSchool(SchoolBean school) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            String deleteSchoolStmt = "DELETE FROM SCHOOLSTBL WHERE ? = SCHOOLID";
            PreparedStatement stmt = conn.prepareStatement(deleteSchoolStmt);
            stmt.setInt(1, school.getSchoolID());
            stmt.executeQuery();
            stmt.close();
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

    @Override
    public void updateSchool(SchoolBean school)
    {
        school.prepareString();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            String deleteSchoolStmt = "UPDATE SCHOOLSTBL SET PREFERENCES = ? WHERE SCHOOLNAME LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(deleteSchoolStmt);
            stmt.setString(1, school.getPreferencesAsString());
            stmt.setString(2, school.getSchoolName());
            stmt.executeQuery();
            stmt.close();
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


    @Override
    public List<String> getSchoolsNames()
    {
        List<String> output = new ArrayList();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            
            String getSchoolsStmt = "SELECT SCHOOLNAME FROM SCHOOLSTBL";
            PreparedStatement stmt = conn.prepareStatement(getSchoolsStmt);
            ResultSet results = stmt.executeQuery();
            while(results.next())
            {
                output.add(results.getString("SCHOOLNAME"));
            }
            results.close();
            stmt.close();
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
        return output;
    }

    @Override
    public void schoolChange(SchoolBean school) {
        List<StudentBean> preferencesLocal = school.getPreferences();
        preferencesLocal.clear();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            school.prepareString();
            
            String getSchoolPrefs = "SELECT SCHOOLID, PREFERENCES FROM SCHOOLSTBL WHERE SCHOOLNAME LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(getSchoolPrefs);
            stmt.setString(1, school.getSchoolName());
            ResultSet rows = stmt.executeQuery();
            while(rows.next())
            {
                String [] pref = rows.getString("PREFERENCES").split(" ");
                for(String preference : pref)
                {
                    StudentBean newStud = new StudentBean();
                    newStud.setStudentName(preference);
                    preferencesLocal.add(newStud);
                }
            }
            rows.close();
            stmt.close();
            school.prepareString();
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
