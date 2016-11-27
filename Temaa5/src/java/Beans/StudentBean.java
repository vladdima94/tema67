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
            newSchool.setName(pref);
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
                if(preferences.get(i).getName().equals(student.getName())){
                        preferences.remove(i);
                }
        }
    }
    
    public List<Map.Entry<String, String>> getResults()
    {
        StudentDAO addStudToDB = (StudentDAO)AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        return addStudToDB.getResults();
    }
}
