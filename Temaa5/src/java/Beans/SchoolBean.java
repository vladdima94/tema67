/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.Factories.AbstractFactory;
import DAO.ResultsDAO;
import DAO.SchoolDAO;
import DAO.SchoolDAOJDBC;
import DAO.StudentDAO;
import DAO.StudentDAOJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Vlad
 */
@ManagedBean( name = "schoolBean")
@SessionScoped
public class SchoolBean {
    private int ID;
    private String name;
    private int maxStuds;
    private List<StudentBean> preferences = new ArrayList();
    private String preferencesAsString;
    private StudentBean asignedTo;
    
    public void setAsignedTo(StudentBean newStudent)
    {
        this.asignedTo = newStudent;
    }
    
    public StudentBean getAsignedTo()
    {
        return this.asignedTo;
    }

    public String getName() {
        return name;
    }

    public List<StudentBean> getPreferences() {
        return preferences;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferences(List<StudentBean> preferences) {
        this.preferences = preferences;
    }
    
    public void generateSchools()
    {
        List<SchoolBean> schools = new ArrayList();
        List<StudentBean> studs = new ArrayList();
        Random random = new Random();
        for(int i = 0; i < 10; ++i)
        {
            StudentBean newStud = new StudentBean();
            SchoolBean newSchool = new SchoolBean();
            
            int id = i+1;
            newStud.setID(id);
            newStud.setName("S" + (i+1));
            newStud.setPassword("asd");
            newStud.setType("user");
            
            newSchool.setID(id);
            newSchool.setName("C" + (i+1));
            newSchool.setMaxStuds(10);
            
            studs.add(newStud);
            schools.add(newSchool);
        }
        
        for(SchoolBean school : schools)
        {
            random.setSeed(System.currentTimeMillis());
            Collections.shuffle(studs, random);
            List<StudentBean> currentPref = school.getPreferences();
            for(StudentBean stud : studs) currentPref.add(stud);
        }
        for(StudentBean stud : studs)
        {
            random.setSeed(System.currentTimeMillis());
            Collections.shuffle(schools, random);
            List<SchoolBean> currentPref = stud.getPreferences();
            for(SchoolBean school : schools) currentPref.add(school);
        }
        StudentBean.addStudsToDB(studs);
        SchoolBean.addSchoolsToDB(schools);
    }
    
    public List<SchoolBean> getSchools()
    {
        
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        return addStudToDB.getSchools();
//        Connection conn = null;
//        List<SchoolBean> output = new ArrayList();
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            String stmtSQL = "SELECT * FROM SCHOOLSTBL ORDER BY SCHOOLNAME";
//            PreparedStatement stmt =conn.prepareStatement(stmtSQL);
//            stmt.execute(stmtSQL);
//            ResultSet rows = stmt.getResultSet();
//            while(rows.next())
//            {
//                String schoolName = rows.getString("SCHOOLNAME");
//                int schoolID = rows.getInt("SCHOOLID");
//                int maxapply = rows.getInt("MAXAPPLY");
//                String schoolPref = rows.getString("PREFERENCES");
//                SchoolBean temp = new SchoolBean();
//                temp.setName(schoolName);
//                temp.setID(schoolID);
//                temp.setMaxStuds(maxapply);
//                temp.setPreferencesAsString(schoolPref);
//                output.add(temp);
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
//        return output;
    }
            

    public int getMaxStuds() {
        return maxStuds;
    }

    public void setMaxStuds(int maxStuds) {
        this.maxStuds = maxStuds;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void addSchoolToDB()
    {
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        addStudToDB.addSchoolToDB(this);
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            conn.setAutoCommit(true);
//            
//            this.prepareString();
//            
//            String insertSchoolStmt = "INSERT INTO SCHOOLSTBL(schoolID, schoolName, maxApply, preferences) VALUES(?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(insertSchoolStmt);
//            stmt.setInt(1, this.ID);
//            stmt.setString(2, this.name);
//            stmt.setInt(3, this.maxStuds);
//            stmt.setString(4, this.preferencesAsString);
//            stmt.executeQuery();
//            
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
    public static void addSchoolsToDB(List<SchoolBean> schools)
    {
        
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        addStudToDB.addSchoolsToDB(schools);
    }

    public String getPreferencesAsString() {
        return preferencesAsString;
    }

    public void setPreferencesAsString(String preferencesAsString) {
        this.preferencesAsString = preferencesAsString;
    }
    
    
    public void prepareString()
    {
        StringBuilder temp = new StringBuilder();
        for(StudentBean stud : this.preferences)
        {
            temp.append(stud.getName()).append(" ");
        }
        if(temp.length() > 0)this.preferencesAsString = temp.toString();
    }
    
    public void deleteSchool()
    {
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        addStudToDB.deleteSchool(this);
    }
    public void updateSchool(SchoolBean school)
    {
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        addStudToDB.updateSchool(school);
    }
    
    
    public void schoolChange()
    {
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        addStudToDB.schoolChange(this);
    }
    
    public void moveUp(int poz)
    {
        if(poz == 0)return;
        Collections.swap(this.preferences, poz, poz-1);
        this.prepareString();
        updateSchool(this);
    }
    
    public void moveDown(int poz)
    {
        if(poz == this.preferences.size()-1)return;
        Collections.swap(this.preferences, poz, poz+1);
        this.prepareString();
        updateSchool(this);
    }
    
    
    public void resolveProblem()
    {
        
        SchoolDAO schoolDAO = (SchoolDAO) AbstractFactory.getInstance("JDBC").getDAO("school");
        StudentDAO studDAO = (StudentDAO) AbstractFactory.getInstance("JDBC").getDAO("student");
        List<SchoolBean> schools = schoolDAO.getSchools();
        List<StudentBean> students = studDAO.getStudents();
        Map<String, SchoolBean> tempSchools = new HashMap();
        Map<String, StudentBean> tempStuds = new HashMap();
        
        for(SchoolBean school : schools)
        {
            tempSchools.put(school.getName(), school);
        }
        for(StudentBean student : students)
        {
            tempStuds.put(student.getName(), student);
        }
        
//        Connection conn = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            
//            String getSchoolsStmt = "SELECT SCHOOLNAME, PREFERENCES FROM SCHOOLSTBL";
//            PreparedStatement stmt = conn.prepareStatement(getSchoolsStmt);
//            ResultSet results = stmt.executeQuery();
//            while(results.next())
//            {
//                String schoolName = results.getString("SCHOOLNAME");
//                String schoolPref = results.getString("PREFERENCES");
//                SchoolBean newSchoolBean = new SchoolBean();
//                newSchoolBean.setName(schoolName);
//                newSchoolBean.setPreferencesAsString(schoolPref);
//                schools.add(newSchoolBean);
//            }
//            
//            String getSStudentsStmt = "SELECT STUDENTNAME, PREFERENCES FROM STUDENTTBL WHERE STUDENTTYPE LIKE 'user'";
//            stmt = conn.prepareStatement(getSStudentsStmt);
//            results = stmt.executeQuery();
//            while(results.next())
//            {
//                String studentName = results.getString("STUDENTNAME");
//                String studentPref = results.getString("PREFERENCES");
//                StudentBean newStudBean = new StudentBean();
//                newStudBean.setName(studentName);
//                newStudBean.setPreferencesAsString(studentPref);
//                students.add(newStudBean);
//                tempStuds.put(studentName, newStudBean);
//            }
//            
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
        
        for(StudentBean stud : students)
        {
            String [] prefs = stud.getPreferencesAsString().split(" ");
            List<SchoolBean> preferences = stud.getPreferences();
            for(String pref : prefs)
            {
                preferences.add(tempSchools.get(pref));
            }
        }
        for(SchoolBean school : schools)
        {
            String [] prefs = school.getPreferencesAsString().split(" ");
            List<StudentBean> preferences = school.getPreferences();
            for(String pref : prefs)
            {
                preferences.add(tempStuds.get(pref));
            }
        }
        
        StudentBean testStudent;
        SchoolBean testSchool;
        while( (testStudent = getFreeStudent(students)) != null)
        {
                testSchool = testStudent.getPreferences().get(0);
                if(testSchool.getAsignedTo() == null)
                {
                    testSchool.setAsignedTo(testStudent);
                    testStudent.setAsignedTo(testSchool);
                }
                else
                {
                        if(testSchool.preferesStudentOverCurrent(testStudent))
                        {
                                testSchool.getAsignedTo().removeStudent(testSchool);
                                testSchool.getAsignedTo().setAsignedTo(null);
                                testSchool.setAsignedTo(testStudent);
                                testStudent.setAsignedTo(testSchool);
                        }
                        else
                        {
                                testStudent.getPreferences().remove(0);
                        }
                }
        }
        
        ResultsDAO resultsDAO = (ResultsDAO) AbstractFactory.getInstance("JDBC").getDAO("results");
        resultsDAO.insertResultsInDB(students);
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
//                    "userFunctions", "usrFnct");
//            String getSchoolsStmt = "INSERT INTO RESULTSTBL (STUDENTNAME, SCHOOLNAME) VALUES (?, ?)";
//            PreparedStatement stmt;
//            for(StudentBean stud : students)
//            {
//                System.out.printf("%s - %s\r\n", stud.getName(), stud.getAsignedTo().getName());
//                stmt = conn.prepareStatement(getSchoolsStmt);
//                stmt.setString(1, stud.getName());
//                stmt.setString(2, stud.getAsignedTo().getName());
//                stmt.execute();
//            }
//            conn.commit();
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
    
    public boolean preferesStudentOverCurrent(StudentBean testMan)
    {
            for(StudentBean man : this.preferences)
            {
                    if(testMan.getName().equals(man.getName())) return true;
                    if(this.asignedTo.getName().equals(man.getName())) return false;
            }
            return true;
    }
    
    public StudentBean getFreeStudent(List<StudentBean> men)
    {
        for(StudentBean man : men)
        {
                if(man.getAsignedTo() == null && man.getPreferences().size() > 0) return man;
        }
        return null;
    }
    
    
    public List<String> getSchoolsNames()
    {
        SchoolDAOJDBC addStudToDB = (SchoolDAOJDBC) AbstractFactory.getInstance("JDBC").getDAO("school");
        return addStudToDB.getSchoolsNames();
    }
    
    
}
