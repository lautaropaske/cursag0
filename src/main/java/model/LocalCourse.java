package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class LocalCourse extends Course {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Unit> units;

    public LocalCourse(){
        this.units = new ArrayList<>();
    }

    public LocalCourse(String name, String description, double price, double rating, User publisher) {
        super(name,description,price,rating,publisher);
        this.units = new ArrayList<>();
    }


    public List<Unit> getUnits(){return units;}

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public void removeUnit(Unit unit){
        units.remove(unit);
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

}
