/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Config.ConfigClass;
import DAO.Factories.AbstractFactory;
import DAO.StudentDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vlad
 */
@ManagedBean( name = "studentBean")
@SessionScoped
public class StudentBean implements Serializable{
    
    private int studentID;
    private String studentName;
    private String studentPassword;
    private String studentType;
    private String preferencesAsString;
    
    private List<SchoolBean> preferences = new ArrayList();
    private SchoolBean asignedTo;
    
    
    public void setAsignedTo(SchoolBean newSchool)
    {
        this.asignedTo = newSchool;
    }
    
    public SchoolBean getAsignedTo()
    {
        return this.asignedTo;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public int getStudentID() {
        return studentID;
    }

    public List<SchoolBean> getPreferences()
    {
        return preferences;
    }

    public void setStudentName(String name) {
        this.studentName = name;
    }

    public void setStudentPassword(String password) {
        this.studentPassword = password;
    }

    public void setStudentID(int ID) {
        this.studentID = ID;
    }

    public void setPreferences(List<SchoolBean> preferences) {
        this.preferences = preferences;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String type) {
        this.studentType = type;
    }
    
    public void addStudToDB()
    {
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        addStudToDB.addStudToDB(this);
    }
    
    public static void addStudsToDB(List<StudentBean> studs)
    {
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        addStudToDB.addStudsToDB(studs);
    }
    
    
    public void prepareString()
    {
        StringBuilder temp = new StringBuilder();
        for(SchoolBean school : this.preferences)
        {
            temp.append(school.getSchoolName()).append(" ");
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
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        return addStudToDB.testUser(this);
    }

    
    public void loadPreferences()
    {
        if(this.preferencesAsString == null) return;
        String [] prefs = this.preferencesAsString.split(" ");
        for(String pref: prefs)
        {
            SchoolBean newSchool = new SchoolBean();
            newSchool.setSchoolName(pref);
            this.preferences.add(newSchool);
        }
    }

    public void updateSchool(SchoolBean school)
    {
        
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        addStudToDB.updateSchool(this);
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
                if(preferences.get(i).getSchoolName().equals(student.getSchoolName())){
                        preferences.remove(i);
                }
        }
    }
    
    public List<Map.Entry<String, String>> getResults()
    {
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        return addStudToDB.getResults();
    }
    
    
    public void setPrefAsStr()
    {
        if(this.preferences != null)
        {
            StringBuilder builder = new StringBuilder();
            int size = preferences.size();
            for(int i = 0;; ++i)
            {
                SchoolBean temp = preferences.get(i);
                builder.append(temp.getSchoolName());
                if(i == (size - 1)) break;
                builder.append(" ");
            }
            this.preferencesAsString = builder.toString();
        }
    }
}
