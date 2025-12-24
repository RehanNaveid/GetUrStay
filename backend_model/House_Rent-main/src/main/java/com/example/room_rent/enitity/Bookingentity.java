package com.example.room_rent.enitity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="booking")
public class Bookingentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Booking_id")
    private int bookingid;
    @Column(name = "start_date")
    private LocalDate startdate;
    @Column(name = "end_date")
    private LocalDate enddate;
    private String status;
    private LocalDate booking_date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();  // Set the current time when the entity is created
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="User_id")
    @JsonBackReference
    private Userentity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Room_id")
    @JsonBackReference
    private Roomentity room;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(LocalDate booking_date) {
        this.booking_date = booking_date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Roomentity getRoom() {
        return room;
    }

    public void setRoom(Roomentity room) {
        this.room = room;
    }

    public Userentity getUser() {
        return user;
    }

    public void setUser(Userentity user) {
        this.user = user;
    }
}

