/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import Beans.StudentBean;
import DAO.Factories.JPQLDAOFactory;
import Entity.ResultsEntity;
import Entity.SchoolEntity;
import Entity.StudentEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
public class StudentDAOJPQL implements StudentDAO{

    @Override
    public List<StudentBean> getStudents() {
        List<StudentEntity> results = JPQLDAOFactory.getEntityManager().createQuery("SELECT stbl FROM StudentEntity stbl WHERE stbl.studentType = 'user'").getResultList();
        List<StudentBean> output = new LinkedList();
        for(StudentEntity temp : results)
        {
            StudentBean newStud = new StudentBean();
            newStud.setStudentID(temp.getStudentID());
            newStud.setStudentName(temp.getStudentName());
            newStud.setStudentPassword(temp.getStudentPassword());
            newStud.setPreferencesAsString(temp.getPreferencesAsString());
            newStud.setStudentType(temp.getStudentType());
            newStud.loadPreferences();
            output.add(newStud);
        }
        return output;
    }

    @Override
    public void addStudToDB(StudentBean student) {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        StudentEntity stud = new StudentEntity();
        stud.setInfo(student);
        em.persist(stud);
        
        et.commit();
        em.close();
    }

    @Override
    public void addStudsToDB(List<StudentBean> studs) 
    {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        for(StudentBean student : studs)
        {
            StudentEntity stud = new StudentEntity();
            stud.setInfo(student);
            em.persist(stud);
        }
        et.commit();
        em.close();
    }

    @Override
    public String testUser(StudentBean student) {
        
        EntityManager em = JPQLDAOFactory.getEntityManager();
        List<StudentEntity> admTest = em.createQuery("SELECT stbl FROM StudentEntity stbl WHERE stbl.studentType LIKE 'admin' AND stbl.studentName LIKE :usr AND stbl.studentPassword LIKE :pass")
                                                .setParameter("usr", student.getStudentName())
                                                .setParameter("pass", student.getStudentPassword()).getResultList();
        
        if(admTest.size() > 0)
        {
            em.close();
            StudentEntity currentStudent = admTest.get(0);
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("username", currentStudent.getStudentName());
            session.setAttribute("type", currentStudent.getStudentType());
            student.setStudentID(currentStudent.getStudentID());
            student.setPreferencesAsString(currentStudent.getPreferencesAsString());
            student.getPreferences().clear();
            student.setStudentPassword(null);
            student.loadPreferences();
            return "schoolsPreferences.xhtml";
        }
        
        List<StudentEntity> usrTest = em.createQuery("SELECT stbl FROM StudentEntity stbl WHERE stbl.studentType LIKE 'user' AND stbl.studentName LIKE :usr AND stbl.studentPassword LIKE :pass")
                                                .setParameter("usr", student.getStudentName())
                                                .setParameter("pass", student.getStudentPassword()).getResultList();
        
        if(usrTest.size() > 0)
        {
            em.close();
            StudentEntity currentStudent = usrTest.get(0);
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("username", currentStudent.getStudentName());
            session.setAttribute("type", currentStudent.getStudentType());
            student.setStudentID(currentStudent.getStudentID());
            student.setPreferencesAsString(currentStudent.getPreferencesAsString());
            student.getPreferences().clear();
            student.setStudentPassword(null);
            student.loadPreferences();
            return "editStudent.xhtml";
        }
    
        em.close();
        return "login.xhtml";
    }

    @Override
    public void updateSchool(StudentBean student) {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        StudentEntity stud = em.find(StudentEntity.class, student.getStudentID());
        if(stud != null)
        {
            stud.setInfo(student);
            et.commit();
        }
        em.close();
    }

    @Override
    public List<Map.Entry<String, String>> getResults() {
        List<ResultsEntity> results = JPQLDAOFactory.getEntityManager().createQuery("SELECT stbl FROM ResultsEntity stbl").getResultList();
        Map<String,String> output = new HashMap();
        for(ResultsEntity temp : results)
        {
            output.put(temp.getStudentName(), temp.getSchoolName());
        }
        return new ArrayList<>(output.entrySet());
    }
    
}
