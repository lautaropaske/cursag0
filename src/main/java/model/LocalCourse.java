package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class LocalCourse extends Course {

    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Unit> units;

    public LocalCourse(){
        this.units = new ArrayList<>();
    }

    public LocalCourse(String name, String description, double price, User publisher) {
        super(name,description,price,publisher);
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
