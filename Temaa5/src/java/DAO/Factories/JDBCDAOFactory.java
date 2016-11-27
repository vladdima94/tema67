/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

import Config.ConfigClass;
import DAO.DAO;
import DAO.ResultsDAOJDBC;
import DAO.SchoolDAOJDBC;
import DAO.StudentDAOJDBC;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class JDBCDAOFactory implements DAOFactory{

    
    private static boolean classesLoaded = false;
    static{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            classesLoaded = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public DAO getDAO(int type) {
        if(classesLoaded == false) return null;
        switch(type)
        {
            case ConfigClass.STUDENT_DAO :{
                return new StudentDAOJDBC();
            }
            case ConfigClass.SCHOOL_DAO :{
                return new SchoolDAOJDBC();
            }
            case ConfigClass.RESULTS_DAO :{
                return new ResultsDAOJDBC();
            }
            default: return null;
        }
    }
    
}
