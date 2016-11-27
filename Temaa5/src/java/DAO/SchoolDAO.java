/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.SchoolBean;
import java.util.List;

/**
 *
 * @author Vlad
 */
public interface SchoolDAO extends DAO{
    
    public List<SchoolBean> getSchools();
    public void addSchoolToDB(SchoolBean school);
    public void addSchoolsToDB(List<SchoolBean> schools);
    public void deleteSchool(SchoolBean school);
    
    /**
     * Update student position in School preferences. This method should only saves string from SchoolBean in DB, the swap must be done before
     * this method is called
     */
    public void updateSchool(SchoolBean school);
    public void schoolChange(SchoolBean school);
    
    public List<String> getSchoolsNames();
}
