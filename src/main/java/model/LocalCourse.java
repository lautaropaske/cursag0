package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LocalCourse extends Course {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private Collection<Unit> units;

    public LocalCourse(){
        this.units = new ArrayList<>();
    }

    public LocalCourse(String name, String description, double price, User publisher) {
        super(name,description,price,publisher);
        this.units = new ArrayList<>();
    }


    public Collection<Unit> getUnits(){return units;}

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public void removeUnit(Unit unit){
        units.remove(unit);
    }

    public void setUnits(Collection<Unit> units) {
        this.units = units;
    }

}
