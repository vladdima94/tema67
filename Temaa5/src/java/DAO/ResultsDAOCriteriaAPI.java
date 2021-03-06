/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.StudentBean;
import DAO.Factories.CriteriaAPIDAOFactory;
import Entity.ResultsEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Vlad
 */
public class ResultsDAOCriteriaAPI implements ResultsDAO{

    @Override
    public void insertResultsInDB(List<StudentBean> results) {
        EntityManager em = CriteriaAPIDAOFactory.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        for(StudentBean stud : results)
        {
            ResultsEntity result = new ResultsEntity();
            result.setSchoolName(stud.getAsignedTo().getSchoolName());
            result.setStudentName(stud.getStudentName());
            em.persist(result);
        }
        et.commit();
        em.close();
    }
    
}
