package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reservation reservation = new Reservation();
        Optional<User> optionalUser = userRepository3.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new Exception("Cannot make reservation");
        }
        User user = optionalUser.get();

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if(!optionalParkingLot.isPresent()) {
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot = optionalParkingLot.get();

        Spot reserveSpot = getSpot(numberOfWheels, parkingLot);

        reserveSpot.setOccupied(Boolean.TRUE);

        Reservation reservation = new Reservation(timeInHours);
        reservation.setSpot(reserveSpot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);
        reservation = reservationRepository3.save(reservation);

        reservation.setUser(null);
        reservation.setSpot(null);
        return reservation;
    }

    private static Spot getSpot(Integer numberOfWheels, ParkingLot parkingLot) throws Exception {
        SpotType spotType = SpotType.OTHERS;
        if(numberOfWheels == 2) spotType = SpotType.TWO_WHEELER;
        else if(numberOfWheels == 4) spotType = SpotType.FOUR_WHEELER;

        List<Spot> spotList = parkingLot.getSpotList();
        // get spot for given vehicle
        Spot reserveSpot = null;
        int minPrice = Integer.MAX_VALUE;
        for (Spot spot: spotList) {
            if(spotType.equals(spot.getSpotType()) && spot.getOccupied() == Boolean.FALSE) {
                if(spot.getPricePerHour() < minPrice) {
                    reserveSpot = spot;
                    minPrice = spot.getPricePerHour();
                }

            }
        }

        if(reserveSpot == null) {
            if(spotType.equals(SpotType.TWO_WHEELER)) {
                for (Spot spot: spotList) {
                    if(SpotType.FOUR_WHEELER.equals(spot.getSpotType()) && spot.getOccupied() == Boolean.FALSE) {
                        if(spot.getPricePerHour() < minPrice) {
                            reserveSpot = spot;
                            minPrice = spot.getPricePerHour();
                        }
                    }
                }
            }
            for (Spot spot: spotList) {
                if(SpotType.OTHERS.equals(spot.getSpotType()) && spot.getOccupied() == Boolean.FALSE) {
                    if(spot.getPricePerHour() < minPrice) {
                        reserveSpot = spot;
                        minPrice = spot.getPricePerHour();
                    }
                }
            }
        }

        if(reserveSpot == null) {
            throw new Exception("Cannot make reservation");
        }
        return reserveSpot;
    }
}
