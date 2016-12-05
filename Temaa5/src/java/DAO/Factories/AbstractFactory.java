/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

import Config.ConfigClass;

/**
 *
 * @author Vlad
 */
public abstract class AbstractFactory {
    
    //bagam si putin de singleton sa nu ne batem joc de memorie :))
    private static DAOFactory instance;
    
    public static DAOFactory getInstance(int type)
    {
        switch(type)
        {
            case ConfigClass.IMPLEMENTATION_JDBC:
            {
                if(instance instanceof JDBCDAOFactory)return instance;      // 
                instance = new JDBCDAOFactory();                            //  asa trebuie pus si la cazul tau bogdan
                return instance;                                            //
            }
            case ConfigClass.IMPLEMENTATION_JPA_JP_QL:
            {
                if(instance instanceof JPQLDAOFactory)return instance;      // 
                instance = new JPQLDAOFactory();                            //  asa trebuie pus si la cazul tau bogdan
                return instance;   
            }
            case ConfigClass.IMPLEMENTATION_JPA_CriteriaAPI:
            {
                if(instance instanceof JDBCDAOFactory)return instance;      // 
                instance = new CriteriaAPIDAOFactory();                            //  asa trebuie pus si la cazul tau bogdan
                return instance;   
            }
            default: return null;
        }
    }
}
