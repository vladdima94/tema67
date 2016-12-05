/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.StudentBean;
import DAO.Factories.JPQLDAOFactory;
import Entity.ResultsEntity;
import Entity.StudentEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
public class StudentDAOCriteriaAPI implements StudentDAO{

    @Override
    public List<StudentBean> getStudents() {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> cq = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> c = cq.from(StudentEntity.class);
        cq.select(c).where(cb.like(c.get("studentType"), "user")).distinct(true);
        TypedQuery<StudentEntity> q = em.createQuery(cq);
        List<StudentEntity> results = q.getResultList();
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
        em.close();
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
    public void addStudsToDB(List<StudentBean> studs) {
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> cq = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> students = cq.from(StudentEntity.class);
        cq.select(students).where(cb.and(cb.like(students.get("studentType"), "admin"),
                                         cb.like(students.get("studentName"), student.getStudentName()),
                                         cb.like(students.get("studentPassword"), student.getStudentPassword())));
        TypedQuery<StudentEntity> q = em.createQuery(cq);
        List<StudentEntity> admTest = q.getResultList();

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
        
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(StudentEntity.class);
        students = cq.from(StudentEntity.class);
        cq.select(students).where(cb.and(cb.like(students.get("studentType"), "user"),
                                         cb.like(students.get("studentName"), student.getStudentName())),
                                         cb.like(students.get("studentPassword"), student.getStudentPassword()));
        q = em.createQuery(cq);
        List<StudentEntity> usrTest = q.getResultList();
        
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate cu = cb.createCriteriaUpdate(StudentEntity.class);
        Root<StudentEntity> e = cu.from(StudentEntity.class);
        cu.set("preferencesAsString", student.getPreferencesAsString());
        cu.where(cb.like(e.get("studentName"), student.getStudentName()));
        
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery(cu).executeUpdate();
        et.commit();
        em.close();
    }

    @Override
    public List<Map.Entry<String, String>> getResults() {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<ResultsEntity> c = cq.from(ResultsEntity.class);
        cq.multiselect(c.get("studentName"),
                      c.get("schoolName")).distinct(true);
        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> results = q.getResultList();
        Map<String,String> output = new HashMap();
        for(Tuple temp : results)
        {
            output.put((String)temp.get(0), (String)temp.get(1));
        }
        em.close();
        return new ArrayList<>(output.entrySet());
    }
    
}
