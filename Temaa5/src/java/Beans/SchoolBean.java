/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Config.ConfigClass;
import DAO.Factories.AbstractFactory;
import DAO.ResultsDAO;
import DAO.SchoolDAO;
import DAO.StudentDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Vlad
 */
@ManagedBean( name = "schoolBean")
@SessionScoped
public class SchoolBean implements Serializable{
    
    private int schoolID;
    private String schoolName;
    private int maxApply;
    private String preferencesAsString;
    
    
    private List<StudentBean> preferences = new ArrayList();
    private StudentBean asignedTo;
    
    public void setAsignedTo(StudentBean newStudent)
    {
        this.asignedTo = newStudent;
    }
    
    public StudentBean getAsignedTo()
    {
        return this.asignedTo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public List<StudentBean> getPreferences() {
        return preferences;
    }

    public void setSchoolName(String name) {
        this.schoolName = name;
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
            newStud.setStudentID(id);
            newStud.setStudentName("S" + (i+1));
            newStud.setStudentPassword("asd");
            newStud.setStudentType("user");
            
            newSchool.setSchoolID(id);
            newSchool.setSchoolName("C" + (i+1));
            newSchool.setMaxApply(10);
            
            studs.add(newStud);
            schools.add(newSchool);
        }
        
        for(SchoolBean school : schools)
        {
            random.setSeed(System.currentTimeMillis());
            Collections.shuffle(studs, random);
            List<StudentBean> currentPref = school.getPreferences();
            for(StudentBean stud : studs) currentPref.add(stud);
            school.setPrefAsStr();
        }
        for(StudentBean stud : studs)
        {
            random.setSeed(System.currentTimeMillis());
            Collections.shuffle(schools, random);
            List<SchoolBean> currentPref = stud.getPreferences();
            for(SchoolBean school : schools) currentPref.add(school);
            stud.setPrefAsStr();
        }
        StudentBean.addStudsToDB(studs);
        SchoolBean.addSchoolsToDB(schools);
    }
    
    public List<SchoolBean> getSchools()
    {
        
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        return addStudToDB.getSchools();
    }
            

    public int getMaxApply() {
        return maxApply;
    }

    public void setMaxApply(int maxStuds) {
        this.maxApply = maxStuds;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int ID) {
        this.schoolID = ID;
    }
    
    public void addSchoolToDB()
    {
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        addStudToDB.addSchoolToDB(this);

    }
    public static void addSchoolsToDB(List<SchoolBean> schools)
    {
        
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
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
            temp.append(stud.getStudentName()).append(" ");
        }
        if(temp.length() > 0)this.preferencesAsString = temp.toString();
    }
    
    public void deleteSchool()
    {
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        addStudToDB.deleteSchool(this);
    }
    public void updateSchool(SchoolBean school)
    {
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        addStudToDB.updateSchool(school);
    }
    
    
    public void schoolChange()
    {
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
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
        
        SchoolDAO schoolDAO = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        StudentDAO studDAO = (StudentDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.STUDENT_DAO);
        List<SchoolBean> schools = schoolDAO.getSchools();
        List<StudentBean> students = studDAO.getStudents();
        Map<String, SchoolBean> tempSchools = new HashMap();
        Map<String, StudentBean> tempStuds = new HashMap();
        
        for(SchoolBean school : schools)
        {
            tempSchools.put(school.getSchoolName(), school);
        }
        for(StudentBean student : students)
        {
            tempStuds.put(student.getStudentName(), student);
        }
        
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
        
        ResultsDAO resultsDAO = (ResultsDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.RESULTS_DAO);
        resultsDAO.insertResultsInDB(students);

    }
    
    public boolean preferesStudentOverCurrent(StudentBean testMan)
    {
            for(StudentBean man : this.preferences)
            {
                    if(testMan.getStudentName().equals(man.getStudentName())) return true;
                    if(this.asignedTo.getStudentName().equals(man.getStudentName())) return false;
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
        SchoolDAO addStudToDB = (SchoolDAO) AbstractFactory.getInstance(ConfigClass.IMPLEMENTATION_USED).getDAO(ConfigClass.SCHOOL_DAO);
        return addStudToDB.getSchoolsNames();
    }
    
    public void setPrefAsStr()
    {
        if(this.preferences != null)
        {
            StringBuilder builder = new StringBuilder();
            int size = preferences.size();
            for(int i = 0;; ++i)
            {
                StudentBean temp = preferences.get(i);
                builder.append(temp.getStudentName());
                if(i == (size - 1)) break;
                builder.append(" ");
            }
            this.preferencesAsString = builder.toString();
        }
    }
}
