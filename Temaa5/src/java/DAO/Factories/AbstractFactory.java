/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

/**
 *
 * @author Vlad
 */
public abstract class AbstractFactory {
    
    //bagam si putin de singleton sa nu ne batem joc de memorie :))
    private static DAOFactory instance;
    
    public static DAOFactory getInstance(String type)
    {
        if(type == null) return null;
        switch(type)
        {
            case "JDBC":
            {
                if(instance instanceof JDBCDAOFactory)return instance;      // 
                instance = new JDBCDAOFactory();                            //  asa trebuie pus si la cazul tau bogdan
                return instance;                                            //
            }
            case "JPA+JP-QL":
            {
                
            }
            case "JPA+CriteriaAPI":
            {
                
            }
            default: return null;
        }
    }
}
