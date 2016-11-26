/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.Factories.AbstractFactory;
import DAO.StudentDAOJDBC;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
@ManagedBean( name = "studentBean")
@SessionScoped
public class StudentBean implements Serializable{
    private String name;
    private String password;
    private String type;
    private int ID;
    private List<SchoolBean> preferences = new ArrayList();
    private String preferencesAsString;
    private SchoolBean asignedTo;
    
    public void setAsignedTo(SchoolBean newSchool)
    {
        this.asignedTo = newSchool;
    }
    
    public SchoolBean getAsignedTo()
    {
        return this.asignedTo;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return ID;
    }

    public List<SchoolBean> getPreferences()
    {
        return preferences;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPreferences(List<SchoolBean> preferences) {
        this.preferences = preferences;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void addStudToDB()
    {
        StudentDAOJDBC addStudToDB = (StudentDAOJDBC)AbstractFactory.getInstance("JDBC").getDAO("student");
        addStudToDB.addStudToDB(this);
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            conn.setAutoCommit(true);
//            this.prepareString();
//
//            String insertStudStmt = "INSERT INTO studentTBL(studentID, studentName, studentPassword, studentType, preferences) VALUES(?, ?, ?, ?, ?)";
//            PreparedStatement insertStudPrepStmt = conn.prepareStatement(insertStudStmt);
//            insertStudPrepStmt.setInt(1, this.ID);
//            insertStudPrepStmt.setString(2, this.name);
//            insertStudPrepStmt.setString(3, this.password);
//            insertStudPrepStmt.setString(4, this.type);
//            insertStudPrepStmt.setString(4, this.preferencesAsString);
//            insertStudPrepStmt.executeQuery();
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally
//        {
//            if(conn != null) try {
//                conn.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
    
    public static void addStudsToDB(List<StudentBean> studs)
    {
        StudentDAOJDBC addStudToDB = (StudentDAOJDBC)AbstractFactory.getInstance("JDBC").getDAO("student");
        addStudToDB.addStudsToDB(studs);
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            conn.setAutoCommit(true);
//            
//            String insertStudStmt = "INSERT INTO studentTBL(studentID, studentName, studentPassword, studentType, preferences) VALUES(?, ?, ?, ?, ?)";
//            PreparedStatement insertStudPrepStmt;
//            for(StudentBean stud : studs){
//                stud.prepareString();
//                insertStudPrepStmt = conn.prepareStatement(insertStudStmt);
//                insertStudPrepStmt.setInt(1, stud.getID());
//                insertStudPrepStmt.setString(2, stud.getName());
//                insertStudPrepStmt.setString(3, stud.getPassword());
//                insertStudPrepStmt.setString(4, stud.getType());
//                insertStudPrepStmt.setString(5, stud.getPreferencesAsString());
//                insertStudPrepStmt.executeQuery();
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally
//        {
//            if(conn != null) try {
//                conn.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
    
    
    public void prepareString()
    {
        StringBuilder temp = new StringBuilder();
        for(SchoolBean school : this.preferences)
        {
            temp.append(school.getName()).append(" ");
        }
        this.preferencesAsString = temp.toString();
    }

    public String getPreferencesAsString() {
        return preferencesAsString;
    }

    public void setPreferencesAsString(String preferencesAsString) {
        this.preferencesAsString = preferencesAsString;
    }
    
    public String getPrefSchool()
    {
        return "asd";
    }
    
    public String testUser()
    {
        
        StudentDAOJDBC addStudToDB = (StudentDAOJDBC)AbstractFactory.getInstance("JDBC").getDAO("student");
        return addStudToDB.testUser(this);
//        if(this.name != null && this.name.length() > 0 && this.password != null && this.password.length() > 0)
//        {
//            Connection conn = null;
//            try {
//                Class.forName("oracle.jdbc.driver.OracleDriver");
//                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                        "userFunctions", "usrFnct");
//                
//                String stmtSQL = "SELECT studentid, studentName, STUDENTTYPE, preferences FROM STUDENTTBL WHERE studentName LIKE ? AND studentpassword LIKE ?";
//                PreparedStatement stmt =conn.prepareStatement(stmtSQL);
//                stmt.setString(1, this.name);
//                stmt.setString(2, this.password);
//                ResultSet rows = stmt.executeQuery();
//                if(rows.getFetchSize() < 1) return "login.xhtml";
//                while(rows.next()){
//                    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//                    String type = rows.getString("STUDENTTYPE");
//                    session.setAttribute("username", rows.getString("STUDENTNAME"));
//                    session.setAttribute("type", type);
//                    this.ID = rows.getInt("STUDENTID");
//                    this.preferencesAsString = rows.getString("PREFERENCES");
//                    this.preferences.clear();
//                    this.password = null;
//                    this.loadPreferences();
//                    if(type.equals("admin"))
//                        return "schoolsPreferences.xhtml";
//                    else
//                        return "editStudent.xhtml";
//                }
//            } catch (ClassNotFoundException | SQLException ex) {
//                Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
//            } finally
//            {
//                if(conn != null) try {
//                    conn.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return "login.xhtml";
    }

    
    public void loadPreferences()
    {
        if(this.preferencesAsString == null) return;
        String [] prefs = this.preferencesAsString.split(" ");
        for(String pref: prefs)
        {
            SchoolBean newSchool = new SchoolBean();
            newSchool.setName(pref);
            this.preferences.add(newSchool);
        }
    }

    public void updateSchool(SchoolBean school)
    {
        
        StudentDAOJDBC addStudToDB = (StudentDAOJDBC)AbstractFactory.getInstance("JDBC").getDAO("student");
        addStudToDB.updateSchool(this);
        
//        if(school != null)
//        {
//            for(int i = 0 ; i < this.preferences.size();++i)
//            {
//                if(this.preferences.get(i).getName().equals(school.getName()))
//                    this.preferences.remove(i);
//            }
//            this.prepareString();
//        }
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            conn.setAutoCommit(true);
//            
//            String updateStudent = "UPDATE STUDENTTBL SET PREFERENCES = ? WHERE STUDENTID = ?";
//            PreparedStatement stmt = conn.prepareStatement(updateStudent);
//            stmt.setInt(2, this.ID);
//            stmt.setString(1, this.preferencesAsString);
//            stmt.executeQuery();
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally
//        {
//            if(conn != null) try {
//                conn.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }  
    }
    
    
    public void moveUp(SchoolBean school, int poz)
    {
        if(poz == 0)return;
        Collections.swap(this.preferences, poz, poz-1);
        this.prepareString();
        updateSchool(this.preferences.get(poz-1));
    }
    
    public void moveDown(SchoolBean school, int poz)
    {
        if(poz == this.preferences.size()-1)return;
        Collections.swap(this.preferences, poz, poz+1);
        this.prepareString();
        updateSchool(this.preferences.get(poz+1));
    }
    
    public void removeStudent(SchoolBean student)
    {
        for(int i = 0 ; i < preferences.size(); ++i)
        {
                if(preferences.get(i).getName().equals(student.getName())){
                        preferences.remove(i);
                }
        }
    }
    
    public List<Map.Entry<String, String>> getResults()
    {
        
        StudentDAOJDBC addStudToDB = (StudentDAOJDBC)AbstractFactory.getInstance("JDBC").getDAO("student");
        return addStudToDB.getResults();
        
//        Map<String, String> output = new TreeMap();
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            conn.setAutoCommit(true);
//            
//            String updateStudent = "SELECT STUDENTNAME, SCHOOLNAME FROM RESULTSTBL";
//            PreparedStatement stmt = conn.prepareStatement(updateStudent);
//            ResultSet rows = stmt.executeQuery();
//            while(rows.next())
//            {
//                output.put(rows.getString("STUDENTNAME"), rows.getString("SCHOOLNAME"));
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally
//        {
//            if(conn != null) try {
//                conn.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(SchoolBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }  
//        return new ArrayList<>(output.entrySet());
    }
}
