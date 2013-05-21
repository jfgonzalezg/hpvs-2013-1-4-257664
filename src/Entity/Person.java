/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

/**
 *
 * @author John F
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Person implements Serializable {
    private String name;
    private String lastname;
    private String SSN;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    private String birthplace;
    
    @Id
    @Column(name="PersonId")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    public Person(){
        super();
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname=lastname;
    }
    public String getSSN(){
        return SSN;
    }
    public void setSSN(String SSN){
        this.SSN=SSN;
    }
    public Date getBirthday(){
        return birthday;
    }
    public void setBirthday(Date birthday){
        this.birthday=birthday;
    }
    public String getBirthplace(){
        return birthplace;
    }
    public void setBirthplace(String birthplace){
        this.birthplace=birthplace;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @Override
    public String toString(){
        return name + " " + lastname;
    }

    
}
