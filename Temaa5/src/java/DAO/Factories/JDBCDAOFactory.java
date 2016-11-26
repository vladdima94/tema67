/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

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
    public DAO getDAO(String type) {
        if(classesLoaded == false || type == null) return null;
        switch(type)
        {
            case "student" :{
                return new StudentDAOJDBC();
            }
            case "school" :{
                return new SchoolDAOJDBC();
            }
            case "results" :{
                return new ResultsDAOJDBC();
            }
            default: return null;
        }
    }
    
}
