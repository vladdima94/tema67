/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import Beans.StudentBean;
import DAO.Factories.JPQLDAOFactory;
import Entity.SchoolEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Vlad
 */
public class SchoolDAOJPQL implements SchoolDAO{

    @Override
    public List<SchoolBean> getSchools() {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        try{
            List<SchoolEntity> results = em.createQuery("SELECT stbl FROM SchoolEntity stbl WHERE stbl.preferencesAsString IS NOT NULL").getResultList();
            List<SchoolBean> output = new LinkedList();
            for(SchoolEntity temp : results)
            {
                SchoolBean newSchool = new SchoolBean();
                newSchool.setSchoolID(temp.getSchoolID());
                newSchool.setSchoolName(temp.getSchoolName());
                newSchool.setPreferencesAsString(temp.getPreferencesAsString());
                output.add(newSchool);
            }
            return output;
        }finally{
            em.close();
        }
    }

    @Override
    public void addSchoolToDB(SchoolBean school) {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        SchoolEntity schoolE = new SchoolEntity();
        schoolE.setInfo(school);
        em.persist(schoolE);
        
        et.commit();
        em.close();
    }

    @Override
    public void addSchoolsToDB(List<SchoolBean> schools) {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        for(SchoolBean school : schools)
        {
            SchoolEntity schoolE = new SchoolEntity();
            schoolE.setInfo(school);
            em.persist(schoolE);
        }
        et.commit();
        em.close();
    }

    @Override
    public void deleteSchool(SchoolBean school)
    {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        SchoolEntity schoolE = new SchoolEntity();
        schoolE.setInfo(school);
        em.remove(schoolE);
        et.commit();
        em.close();
    }

    @Override
    public void updateSchool(SchoolBean school) {
        
        EntityManager em = JPQLDAOFactory.getEntityManager();
        List<SchoolEntity> results = em.createQuery("SELECT stbl FROM SchoolEntity stbl WHERE stbl.schoolName = :sclNAME")
                .setParameter("sclNAME", school.getSchoolName())
                .getResultList();
        if(results.size() < 1)return;
        SchoolEntity schoolE = results.get(0);
        EntityTransaction et = em.getTransaction();
        et.begin();
        schoolE.setInfo(school);
        et.commit();
        em.close();
    }

    @Override
    public void schoolChange(SchoolBean school) {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        List<String> results = em.createQuery("SELECT stbl.preferencesAsString FROM SchoolEntity stbl WHERE stbl.schoolName = :sclNAME")
                .setParameter("sclNAME", school.getSchoolName())
                .getResultList();
        if(results.size() > 0)
        {
            List<StudentBean> preferencesLocal = school.getPreferences();
            preferencesLocal.clear();
            String [] pref = results.get(0).split(" ");
            for(String preference : pref)
            {
                StudentBean newStud = new StudentBean();
                newStud.setStudentName(preference);
                preferencesLocal.add(newStud);
            }
        }
        em.close();
    }

    @Override
    public List<String> getSchoolsNames() {
        EntityManager em = JPQLDAOFactory.getEntityManager();
        try{
            return em.createQuery("SELECT stbl.schoolName FROM SchoolEntity stbl").getResultList();
        }finally{
            em.close();
        }
    }
    
}
