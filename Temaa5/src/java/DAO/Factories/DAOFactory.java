/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factories;

import DAO.DAO;

/**
 *
 * @author Vlad
 */
public interface DAOFactory {
    public DAO getDAO(int type);
}
