/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;



/**
 *
 * @author John F
 */

public class Vacine extends Person{
    private String name;
    private float dosage;
    
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
