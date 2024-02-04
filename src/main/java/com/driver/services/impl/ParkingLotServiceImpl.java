package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name, address);
        parkingLot = parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        SpotType spotType = SpotType.OTHERS;
        if(numberOfWheels == 2) {
            spotType = SpotType.TWO_WHEELER;
        }else if(numberOfWheels == 4) {
            spotType = SpotType.FOUR_WHEELER;
        }
        Spot spot = new Spot(spotType, pricePerHour, Boolean.FALSE);

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
        if (optionalParkingLot.isPresent()) {
            ParkingLot parkingLot = optionalParkingLot.get();
            spot.setParkingLot(parkingLot);
            spot = spotRepository1.save(spot);
        }
        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = new Spot();
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
        if(optionalParkingLot.isPresent()) {
            ParkingLot parkingLot = optionalParkingLot.get();
            Optional<Spot> optionalSpot = spotRepository1.findByIdAndParkingLot(spotId, parkingLot);
            if(optionalSpot.isPresent()) {
                spot = optionalSpot.get();
                spot.setPricePerHour(pricePerHour);
                spot = spotRepository1.save(spot);
            }
        }
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
