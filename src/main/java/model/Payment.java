package model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    private User consumer;
    @ManyToOne
    private Course course;

    public Payment() {
    }

    public Payment(Double amount, Date date, User consumer, Course course) {
        this.amount = amount;
        this.date = date;
        this.consumer = consumer;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public User getConsumer() {
        return consumer;
    }

    public Course getCourse() {
        return course;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
