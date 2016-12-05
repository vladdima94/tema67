/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Beans.StudentBean;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vlad
 */
@Entity
@Table(name = "studentTBL")
public class StudentEntity implements Serializable{
    
    @Id
    @Column(name = "studentID")
    private int studentID;
    @Column(name = "studentName")
    private String studentName;
    @Column(name = "studentPassword")
    private String studentPassword;
    @Column(name = "studentType")
    private String studentType;
    @Column(name = "preferences")
    private String preferencesAsString;

    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public String getStudentType() {
        return studentType;
    }

    public String getPreferencesAsString() {
        return preferencesAsString;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public void setPreferencesAsString(String preferencesAsString) {
        this.preferencesAsString = preferencesAsString;
    }
    
    
    public void setInfo(StudentBean stud)
    {
        String prefsString = stud.getPreferencesAsString();
        String name = stud.getStudentName();
        String pass = stud.getStudentPassword();
        String type = stud.getStudentType();
        
        this.studentID = stud.getStudentID();
        if(prefsString != null) this.preferencesAsString = prefsString;
        if(name != null) this.studentName = name;
        if(pass != null) this.studentPassword = pass;
        if(type != null) this.studentType = type;
    }
}
