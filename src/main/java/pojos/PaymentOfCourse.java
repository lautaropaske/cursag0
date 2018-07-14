package pojos;

import model.Payment;
import model.User;

import java.util.Date;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class PaymentOfCourse {
    private int id;
    private Double amount;
    private Date date;
    private User consumer;

    public PaymentOfCourse() {
    }

    public PaymentOfCourse(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.date = payment.getDate();
        this.consumer = payment.getConsumer();
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
}
