/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Image;
import java.util.Date;


/**
 *
 * @author John F
 */
public class Pet extends Owner{
   private String name;
   private String species;
   private int age;
   private float weight;
   private Image picture;
   
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
