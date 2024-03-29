package com.teams810.reservation.api.entities;

import com.teams810.reservation.api.exceptions.InvalidStatusFlowException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String userId;
    @ElementCollection
    @CollectionTable(name="listOfItemData")
    private List<ItemData> items;
    private TimePeriod timePeriod;
    private ReservationStatus status;
    private String message;

    public Reservation() {
        // Constructor: Default constructor
        this.status = ReservationStatus.WAITING;
        this.message = "";
    }

    public Reservation(String userId, List<ItemData> items, TimePeriod timePeriod) {
        this(userId, items, timePeriod, "");
    }

    public Reservation(String userId, List<ItemData> items, TimePeriod timePeriod, String message) {
        this.userId = userId;
        this.items = items;
        this.timePeriod = timePeriod;
        this.message = message;
        this.status = ReservationStatus.WAITING;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void checkSuccess() throws InvalidStatusFlowException {
        // Instance Method: Shop & user checks reservation as success
        if (this.status != ReservationStatus.WAITING) {
            throw new InvalidStatusFlowException("Invalid status flow.");
        }
        this.status = ReservationStatus.SUCCESS;
    }

    public void checkMissed() throws InvalidStatusFlowException {
        // Instance Method: Shop checks reservation as missed
        if (this.status != ReservationStatus.WAITING) {
            throw new InvalidStatusFlowException("Invalid status flow.");
        } else if (LocalDateTime.now().compareTo(this.timePeriod.getEndDateTime()) <= 0) {
            throw new InvalidStatusFlowException("Unable to check as missed before the reserved time.");
        }
        this.status = ReservationStatus.MISSED;
    }

    public void cancelByUser() throws InvalidStatusFlowException {
        // Instance Method: User cancels reservation
        if (this.status != ReservationStatus.WAITING) {
            throw new InvalidStatusFlowException("Invalid status flow.");
        } else if (LocalDateTime.now().compareTo(this.timePeriod.getStartDateTime()) >= 0) {
            throw new InvalidStatusFlowException("Unable to cancel after the reserved time.");
        }
        this.status = ReservationStatus.CANCELLED_BY_USER;
    }

    public void cancelByShop() throws InvalidStatusFlowException {
        // Instance Method: Shop cancels reservation
        if (this.status != ReservationStatus.WAITING) {
            throw new InvalidStatusFlowException("Invalid status flow.");
        }
        this.status = ReservationStatus.CANCELLED_BY_SHOP;
    }
}
