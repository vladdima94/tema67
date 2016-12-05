/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

import Config.ConfigClass;
import DAO.DAO;
import DAO.ResultsDAOJPQL;
import DAO.SchoolDAOJPQL;
import DAO.StudentDAOJPQL;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vlad
 */
public class JPQLDAOFactory implements DAOFactory{
    
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager()
    {
        if(factory == null)
        {
//            Map<String, String> props = new HashMap();
//            props.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//            props.put("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
//            props.put("hibernate.connection.url", "jdbc:oracle:thin:@localhost:1521:XE");
//            props.put("hibernate.show_sql", "true");
//            props.put("hibernate.connection.username", "userFunction");
//            props.put("hibernate.connection.password", "usrFnct");
//            props.put("hibernate.max_fetch_depth", "3");
//            props.put("hibernate.classloading.use_current_tccl_as_parent", "false");
            factory = Persistence.createEntityManagerFactory("Temaa5PU");
        }
        return factory.createEntityManager();
    }
    
    @Override
    public DAO getDAO(int type) {
        switch(type)
        {
            case ConfigClass.STUDENT_DAO :{
                return new StudentDAOJPQL();
            }
            case ConfigClass.SCHOOL_DAO :{
                return new SchoolDAOJPQL();
            }
            case ConfigClass.RESULTS_DAO :{
                return new ResultsDAOJPQL();
            }
            default: return null;
        }
    }
    
}
