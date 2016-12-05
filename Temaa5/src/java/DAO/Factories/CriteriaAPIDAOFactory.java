/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

import Config.ConfigClass;
import DAO.DAO;
import DAO.ResultsDAOCriteriaAPI;
import DAO.ResultsDAOJPQL;
import DAO.SchoolDAOCriteriaAPI;
import DAO.SchoolDAOJPQL;
import DAO.StudentDAOCriteriaAPI;
import DAO.StudentDAOJPQL;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vlad
 */
public class CriteriaAPIDAOFactory implements DAOFactory{
    
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager()
    {
        if(factory == null)
        {
            factory = Persistence.createEntityManagerFactory("Temaa5PU");
        }
        return factory.createEntityManager();
    }
    
    
    @Override
    public DAO getDAO(int type) {
        switch(type)
        {
            case ConfigClass.STUDENT_DAO :{
                return new StudentDAOCriteriaAPI();
            }
            case ConfigClass.SCHOOL_DAO :{
                return new SchoolDAOCriteriaAPI();
            }
            case ConfigClass.RESULTS_DAO :{
                return new ResultsDAOCriteriaAPI();
            }
            default: return null;
        }
    }
    
}
