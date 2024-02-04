package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment = new Payment();
        Optional<Reservation> optionalReservation = reservationRepository2.findById(reservationId);
        if(optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();

            // Check payment balance
            Spot spot = reservation.getSpot();
            int pricePerHours = spot.getPricePerHour();
            int timeInHours = reservation.getNumberOfHours();
            int paymentAmount = pricePerHours*timeInHours;
            if(amountSent < paymentAmount) {
                throw new Exception("Insufficient Amount");
            }

            // Check Payment Mode
            PaymentMode paymentMode = null;
            if(mode.equalsIgnoreCase("UPI")) {
                paymentMode = PaymentMode.UPI;
            }else if(mode.equalsIgnoreCase("CARD")) {
                paymentMode = PaymentMode.CARD;
            }else if(mode.equalsIgnoreCase("CASH")) {
                paymentMode = PaymentMode.CASH;
            }
            if(paymentMode == null) {
                throw new Exception("Payment mode not detected");
            }

            // Create payment
            payment.setPaymentMode(paymentMode);
            payment.setPaymentCompleted(Boolean.TRUE);
            payment.setReservation(reservation);
            payment = paymentRepository2.save(payment);
        }

        Payment newPayment = new Payment();
        newPayment.setPaymentMode(payment.getPaymentMode());
        newPayment.setPaymentCompleted(payment.getPaymentCompleted());
        newPayment.setId(payment.getId());
        return newPayment;
    }
}
