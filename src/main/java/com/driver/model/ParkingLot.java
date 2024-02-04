package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private List<Spot> spotList = new ArrayList<>();

    public ParkingLot() {

    }

    public ParkingLot(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }
}
