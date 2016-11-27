/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import Beans.StudentBean;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vlad
 */
public interface StudentDAO extends DAO{
    
    public List<StudentBean> getStudents();
    
    public void addStudToDB(StudentBean student);
    public void addStudsToDB(List<StudentBean> studs);
    
    /**
     * Tests student(username,password) is valid (return "schoolsPreferences.xhtml" if valid and type = admin
     * or "editStudent.xhtml" else. Returns "login.xhtml" if user is invalid.
     * 
    */
    public String testUser(StudentBean student);
    
    
    /**
     * Update school position in Student preferences. This method only saves string from StudentBean in DB, the swap must be done before
     * this method is called
     */
    public void updateSchool(StudentBean student);
    
    /**
     * Get results from DB of student allocation problem
     */
    public List<Map.Entry<String, String>> getResults();
}
