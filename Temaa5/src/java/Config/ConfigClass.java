/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;


/**
 *
 * @author Vlad
 */
public final class ConfigClass {
    public static final int IMPLEMENTATION_JDBC = 1;
    public static final int IMPLEMENTATION_JPA_JP_QL = 2;
    public static final int IMPLEMENTATION_JPA_CriteriaAPI = 3;
    
    public static final int STUDENT_DAO = 4;
    public static final int SCHOOL_DAO = 5;
    public static final int RESULTS_DAO = 6;
    
    
    
    
    /**
     * Use this implementation of persistence layer. Change it from here.
     */
    public static final int IMPLEMENTATION_USED = IMPLEMENTATION_JPA_CriteriaAPI;
}