/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



/**
 *
 * @author John F
 */
@Entity
public class Vacine implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private float dosage;
    
    @ManyToOne
    private MedicalRecord medicalrecord;
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public float getDosage(){
        return dosage;
    }
    public void setDosage(float dosage){
        this.dosage=dosage;
    }
}
