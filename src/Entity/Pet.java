/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Image;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


/**
 *
 * @author John F
 */
@Entity
public class Pet implements Serializable{
    
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private String name;
   private String species;
   private int age;
   private float weight;
   //private Image picture;
   
   @ManyToOne()
   private Owner owner;
   @OneToOne(mappedBy = "Pet")
   private MedicalRecord medicalRecord;
   
   public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getSpecies(){
        return species;
    }
    public void setLastname(String species){
        this.species=species;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }
    public float getWeight(){
        return weight;
    }
    public void setWeight(float weight){
        this.weight=weight;
    }
  
}
