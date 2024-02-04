package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int numberOfHours;

    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private Spot spot;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Payment payment;

    public Reservation() {

    }

    public Reservation(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public Spot getSpot() {
        return spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


}
