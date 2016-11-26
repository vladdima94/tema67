/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.StudentBean;
import java.util.List;

/**
 *
 * @author Vlad
 */
public interface ResultsDAO extends DAO{
    
    /**
     * Insert results in DB.
     */
    public void insertResultsInDB(List<StudentBean> results);
}
