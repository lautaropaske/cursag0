package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class LocalCourse extends Course {

    @OneToMany(mappedBy = "parent")
    private Collection<Unit> units;

    public LocalCourse(){
        this.units = new ArrayList<>();
    }

    public Collection<Unit> getUnits(){return units;}

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public void removeUnit(Unit unit){
        units.remove(unit);
    }
}
