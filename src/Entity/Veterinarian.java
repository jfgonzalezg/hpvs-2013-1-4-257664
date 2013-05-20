/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author John F
 */
@Entity
@DiscriminatorValue("Veterinarian")
public class Veterinarian extends Person{
    
    private String focusofpractice;
    private String specialities;
    private int salary;
    private String graduatedat;
    
    @OneToMany(mappedBy = "Veterinarian")
    private List<Appointment> appointment = new ArrayList<>();    
    
    public Veterinarian(){
        super();
    }
    public String getFocusOfPractice(){
        return focusofpractice;
    }
    public void setFocusOfPractice(String focusofpractice){
        this.focusofpractice=focusofpractice;
    }
    public String getSpecialities(){
        return specialities;
    }
    public void setSpecialities(String specialities){
        this.specialities=specialities;
    }
    public int getSalary(){
        return salary;
    }
    public void setSalary(int salary){
        this.salary=salary;
    }
    public String getGraduatedAt(){
        return graduatedat;
    }
    public void setGraduatedAt(String graduatedat){
        this.graduatedat=graduatedat;
    }
}
