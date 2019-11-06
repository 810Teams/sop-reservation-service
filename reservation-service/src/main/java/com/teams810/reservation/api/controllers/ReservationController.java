package com.teams810.reservation.api.controllers;

import com.teams810.reservation.api.entities.Reservation;
import com.teams810.reservation.api.entities.TimePeriod;
import com.teams810.reservation.api.exceptions.InvalidStatusFlowException;
import com.teams810.reservation.api.repositories.ReservationRepository;
import com.teams810.reservation.api.token.AccountToken;
import com.teams810.reservation.api.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private ReservationRepository repository;

    @RequestMapping(value = "/")
    public String index() {
        // Operation: Shows com.teams810.reservation.api.service welcome text.
        // Returns: Welcome text as a string

        return "Reservation Service Index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Reservation> newReservation(
            @RequestBody Reservation reservation
    ) {
        // Operation: Creates a reservation from POST request.
        // Returns: Reservation created

        repository.save(reservation);

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getAllReservation() {
        // Operation: Get all reservations
        // Returns: All reservations
        List<Reservation> reservationList = new ArrayList<Reservation>();

        for (Reservation r : repository.findAll()) {
            reservationList.add(r);
        }

        return new ResponseEntity<List<Reservation>>(reservationList, HttpStatus.OK);
    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reservation> getReservation(
            @PathVariable Long id
    ) {
        // Operation: Get a reservation by id.
        // Returns: A certain reservation

        return new ResponseEntity<Reservation>(repository.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/reservation", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> userReservation(
            @PathVariable String userId
    ) {
        // Operation: Get all reservations of a certain user.
        // Returns: Reservation list of a cart

        List<Reservation> reservationList = new ArrayList<Reservation>();

        for (Reservation r : repository.findAll()) {
            if (r.getUserId().equals(userId)) {
                reservationList.add(r);
            }
        }

        return new ResponseEntity<List<Reservation>>(reservationList, HttpStatus.OK);
    }

    // TODO: Implements shop reservation list

    @RequestMapping(value = "/reservation/{id}/cancel", method = RequestMethod.POST)
    public ResponseEntity<Reservation> cancelReservation(
            @PathVariable Long id
    ) {
        // Operation: Cancel a reservation, changes reservation status.
        // Returns: Cancelled reservation

        Reservation reservation = repository.findById(id);

        try {
            reservation.cancelByUser();
            repository.save(reservation);
        } catch (InvalidStatusFlowException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            return null;
        }

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }
}
