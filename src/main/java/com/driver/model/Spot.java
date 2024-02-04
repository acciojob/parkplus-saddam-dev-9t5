package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private SpotType spotType;

    private int pricePerHour;

    private Boolean occupied;

    @JoinColumn
    @ManyToOne
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    public Spot() {

    }

    public Spot(SpotType spotType, int pricePerHour, Boolean occupied) {
        this.spotType = spotType;
        this.pricePerHour = pricePerHour;
        this.occupied = occupied;
    }

    public int getId() {
        return id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
