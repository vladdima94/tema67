/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Beans.SchoolBean;
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
@Table(name = "schoolsTbl")
public class SchoolEntity implements Serializable{
    
    @Id
    @Column(name = "schoolID")
    private int schoolID;
    @Column(name = "schoolName")
    private String schoolName;
    @Column(name = "maxApply")
    private int maxApply;
    @Column(name = "preferences")
    private String preferencesAsString;

    
    public int getSchoolID() {
        return schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public int getMaxApply() {
        return maxApply;
    }

    public String getPreferencesAsString() {
        return preferencesAsString;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setMaxApply(int maxApply) {
        this.maxApply = maxApply;
    }

    public void setPreferencesAsString(String preferencesAsString) {
        this.preferencesAsString = preferencesAsString;
    }
    
    public void setInfo(SchoolBean school)
    {
        String prefsString = school.getPreferencesAsString();
        String name = school.getSchoolName();
        int newID = school.getSchoolID();
        int newMaxApply = school.getMaxApply();
        
        if(newID != 0)this.schoolID = newID;
        if(newMaxApply != 0)this.maxApply = newMaxApply;
        if(prefsString != null && prefsString.length() > 0) this.preferencesAsString = prefsString;
        if(name != null && name.length() > 0) this.schoolName = name ;
    }
}
