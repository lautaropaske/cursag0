package model;

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


    @ManyToMany(mappedBy = "enrolled")
    private Set<User> enrolledStudents = new HashSet<User>();


    public LocalCourse(){
        this.units = new ArrayList<>();
    }

    public LocalCourse(String name, String description, double price, double rating, User publisher) {
        super(name,description,price,rating,publisher);
        this.units = new ArrayList<>();
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
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

    public void setEnrolledStudents(Set<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void enrollStudents(User user){
        enrolledStudents.add(user);
    }

}
