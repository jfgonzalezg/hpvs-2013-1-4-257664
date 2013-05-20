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
import javax.persistence.InheritanceType;



/**
 *
 * @author John F
 */
@Entity
@DiscriminatorValue("Owner")
public class Owner extends Person{
    
    public Owner(){
        super();
    }
    
    @OneToMany(mappedBy = "Owner")
    private List<Pet> pet = new ArrayList<>();

}
