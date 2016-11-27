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
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
public class StudentDAOJDBC implements StudentDAO{

    @Override
    public void addStudToDB(StudentBean student) {Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            student.prepareString();

            String insertStudStmt = "INSERT INTO studentTBL(studentID, studentName, studentPassword, studentType, preferences) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement insertStudPrepStmt = conn.prepareStatement(insertStudStmt);
            insertStudPrepStmt.setInt(1, student.getID());
            insertStudPrepStmt.setString(2, student.getName());
            insertStudPrepStmt.setString(3, student.getPassword());
            insertStudPrepStmt.setString(4, student.getType());
            insertStudPrepStmt.setString(4, student.getPreferencesAsString());
            insertStudPrepStmt.executeQuery();
            insertStudPrepStmt.close();
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
    public void addStudsToDB(List<StudentBean> studs) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            String insertStudStmt = "INSERT INTO studentTBL(studentID, studentName, studentPassword, studentType, preferences) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement insertStudPrepStmt;
            for(StudentBean stud : studs){
                stud.prepareString();
                insertStudPrepStmt = conn.prepareStatement(insertStudStmt);
                insertStudPrepStmt.setInt(1, stud.getID());
                insertStudPrepStmt.setString(2, stud.getName());
                insertStudPrepStmt.setString(3, stud.getPassword());
                insertStudPrepStmt.setString(4, stud.getType());
                insertStudPrepStmt.setString(5, stud.getPreferencesAsString());
                insertStudPrepStmt.executeQuery();
                insertStudPrepStmt.close();
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
    public String testUser(StudentBean student) {
        String username = student.getName();
        String password = student.getPassword();
        if(username != null && username.length() > 0 && password != null && password.length() > 0)
        {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "userFunctions", "usrFnct");
                
                String stmtSQL = "SELECT studentid, studentName, STUDENTTYPE, preferences FROM STUDENTTBL WHERE studentName LIKE ? AND studentpassword LIKE ?";
                PreparedStatement stmt =conn.prepareStatement(stmtSQL);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rows = stmt.executeQuery();
                if(rows.getFetchSize() < 1)
                {
                    rows.close();
                    stmt.close();
                    return "login.xhtml";
                }
                while(rows.next()){
                    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    String type = rows.getString("STUDENTTYPE");
                    session.setAttribute("username", rows.getString("STUDENTNAME"));
                    session.setAttribute("type", type);
                    student.setID(rows.getInt("STUDENTID"));
                    student.setPreferencesAsString(rows.getString("PREFERENCES"));
                    student.getPreferences().clear();
                    student.setPassword(null);
                    student.loadPreferences();
                    rows.close();
                    stmt.close();
                    if(type.equals("admin"))
                        return "schoolsPreferences.xhtml";
                    else
                        return "editStudent.xhtml";
                }
            } catch (SQLException ex) {
                Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                if(conn != null) try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "login.xhtml";
    }

    @Override
    public void updateSchool(StudentBean student) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            String updateStudent = "UPDATE STUDENTTBL SET PREFERENCES = ? WHERE STUDENTID = ?";
            PreparedStatement stmt = conn.prepareStatement(updateStudent);
            stmt.setInt(2, student.getID());
            stmt.setString(1, student.getPreferencesAsString());
            stmt.executeQuery();
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
    public List<Map.Entry<String, String>> getResults() {
        Map<String, String> output = new TreeMap();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            conn.setAutoCommit(true);
            
            String updateStudent = "SELECT STUDENTNAME, SCHOOLNAME FROM RESULTSTBL";
            PreparedStatement stmt = conn.prepareStatement(updateStudent);
            ResultSet rows = stmt.executeQuery();
            while(rows.next())
            {
                output.put(rows.getString("STUDENTNAME"), rows.getString("SCHOOLNAME"));
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
        return new ArrayList<>(output.entrySet());
    }

    @Override
    public List<StudentBean> getStudents() {
        List<StudentBean> output = new ArrayList();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "userFunctions", "usrFnct");
            
            String getSStudentsStmt = "SELECT STUDENTNAME, PREFERENCES FROM STUDENTTBL WHERE STUDENTTYPE LIKE 'user'";
            PreparedStatement stmt = conn.prepareStatement(getSStudentsStmt);
            ResultSet results = stmt.executeQuery();
            while(results.next())
            {
                String studentName = results.getString("STUDENTNAME");
                String studentPref = results.getString("PREFERENCES");
                StudentBean newStudBean = new StudentBean();
                newStudBean.setName(studentName);
                newStudBean.setPreferencesAsString(studentPref);
                output.add(newStudBean);
            }
            stmt.close();
            results.close();
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

}
