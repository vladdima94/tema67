/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import Beans.StudentBean;
import DAO.Factories.CriteriaAPIDAOFactory;
import Entity.SchoolEntity;
import Entity.StudentEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Vlad
 */
public class SchoolDAOCriteriaAPI implements SchoolDAO{

    @Override
    public List<SchoolBean> getSchools() {
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
        try{
            List<SchoolBean> output = new LinkedList();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SchoolEntity> query = cb.createQuery(SchoolEntity.class);
            Root<SchoolEntity> p = query.from(SchoolEntity.class);
            query.select(p).where(cb.isNotNull(p.get("preferencesAsString")));
            return output;
        }finally{
            em.close();
        }
    }

    @Override
    public void addSchoolToDB(SchoolBean school) {
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
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
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
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
    public void deleteSchool(SchoolBean school) {
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
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
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SchoolEntity> cu = cb.createCriteriaUpdate(SchoolEntity.class);
        Root<SchoolEntity> c = cu.from(SchoolEntity.class);
        cu.set("preferencesAsString", school.getPreferencesAsString()).where(cb.like(c.get("schoolName"), school.getSchoolName()));

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createQuery(cu).executeUpdate();
        et.commit();
        em.close();
    }

    @Override
    public void schoolChange(SchoolBean school) {
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<SchoolEntity> c = cq.from(SchoolEntity.class);
        cq.select(c.get("preferencesAsString"))
                .where(cb.like(c.get("schoolName"), school.getSchoolName())).distinct(true);
        TypedQuery<String> q = em.createQuery(cq);
        List<String> results = q.getResultList();
        
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
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<SchoolEntity> c = cq.from(SchoolEntity.class);
        cq.select(c.get("schoolName")).distinct(true);
        TypedQuery<String> q = em.createQuery(cq);
        return q.getResultList();
    }
    
}
